package pro.sky.telegrambot.service.volunteer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import pro.sky.telegrambot.entity.Volunteers;
import pro.sky.telegrambot.exception.NotFoundVolunteerByIdException;
import pro.sky.telegrambot.exception.NotNullIdException;
import pro.sky.telegrambot.interfaces.volunteer.VolunteerService;
import pro.sky.telegrambot.repository.volunteer.VolunteerRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class VolunteersServiceImplTests {
    @Autowired
    private VolunteerService volunteerService ;
    @MockBean
    private VolunteerRepository volunteerRepository;

    private final Volunteers TEST_Volunteers = new Volunteers();
    private final Volunteers TEST_Volunteers_WITH_NULL_ID = new Volunteers();

    private Volunteers EXPECTED_Volunteers = new Volunteers();

    @BeforeEach
    void init_volunteer(){
        TEST_Volunteers.setId(1l);
        TEST_Volunteers.setName("TEST_name");
        TEST_Volunteers.setNickName("TEST_NICKNAME");
        TEST_Volunteers.setWorkingFirstTime(LocalDateTime.now());
        TEST_Volunteers.setWorkingLastTime(LocalDateTime.now());
    }

    private void assertsObject(Volunteers expected, Volunteers actual) {
        Assertions.assertEquals(expected.getId(),actual.getId());
        Assertions.assertEquals(expected.getName(),actual.getName());
        Assertions.assertEquals(expected.getNickName(),actual.getNickName());
        Assertions.assertEquals(expected.getWorkingFirstTime(),actual.getWorkingFirstTime());
        Assertions.assertEquals(expected.getWorkingLastTime(),actual.getWorkingLastTime());

        Assertions.assertEquals(expected.toString(), actual.toString());
        Assertions.assertEquals(expected.hashCode(), actual.hashCode());
        Assertions.assertTrue(expected.equals(actual));
    }

    /**
     * проверяет возрвщается ли ошибка если не найден волонтер в БД
     * @Note для тестирования используется Mockito
     */
    @Test
    public void shouldBeReturnNotFoundVolunteerByIdException() {
        Mockito.when(volunteerRepository.findById(-1l)).thenReturn(Optional.ofNullable(null));
        Assertions.assertThrows(NotFoundVolunteerByIdException.class, ()->volunteerService.findById(-1l));
    }

    /**
     * проверяет совпадает ли идентификатор по id у волонтеров при поиске в методе
     * @Note для тестирования используется Mockito
     */
    @Test
    public void shouldBeАFindByIdVolunteer() {
        Mockito.when(volunteerRepository.findById(TEST_Volunteers.getId())).thenReturn(Optional.ofNullable(TEST_Volunteers));
        EXPECTED_Volunteers = volunteerService.findById(TEST_Volunteers.getId());
        assertsObject(EXPECTED_Volunteers, TEST_Volunteers);
    }

    /**
     * добавляет волонтера(без id)
     * @Note для тестирования используется Mockito
     */
    @Test
    public void shouldBeAddVolunteer() {
        Mockito.when(volunteerRepository.save(TEST_Volunteers_WITH_NULL_ID)).thenReturn(TEST_Volunteers);
        EXPECTED_Volunteers = volunteerService.add(TEST_Volunteers_WITH_NULL_ID);
        assertsObject(EXPECTED_Volunteers, TEST_Volunteers);
    }

    /**
     * попытка добавить волонтера у которого уже присвоен id
     */
    @Test
    public void addVolunteerShouldBeReturnNotNullIdException() {
        Assertions.assertThrows(NotNullIdException.class, ()-> volunteerService.add(TEST_Volunteers));
    }

    /**
     * поиск волонтера - по id
     * @Note для тестирования используется Mockito
     */
    @Test
    public void shouldBeFindVolunteer() {
        Mockito.when(volunteerRepository.findById(TEST_Volunteers.getId())).thenReturn(Optional.of(TEST_Volunteers));
        EXPECTED_Volunteers = volunteerService.find(TEST_Volunteers);

        assertsObject(EXPECTED_Volunteers, TEST_Volunteers);
    }

    /**
     * поиск волонтера - по id
     * @Note для тестирования используется Mockito
     */
    @Test
    public void tryFindVolunteerShouldBeReturnNotFoundVolunteerByIdException() {
        Mockito.when(volunteerRepository.findById(TEST_Volunteers.getId())).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundVolunteerByIdException.class, ()-> volunteerService.find(TEST_Volunteers));
    }

    /**
     * поиск волонтера - по id и редактирвоание данных на примере изменения имени
     * @Note для тестирования используется Mockito
     */
    @Test
    public void shouldBeEditVolunteer() {
        EXPECTED_Volunteers.setName("TEST NAME MUST CHANGE");
        TEST_Volunteers.setName("NAME WAS CHANGED");

        Mockito.when(volunteerRepository.findById(TEST_Volunteers.getId())).thenReturn(Optional.of(TEST_Volunteers));
        Mockito.when(volunteerRepository.save(TEST_Volunteers)).thenReturn(TEST_Volunteers);

        EXPECTED_Volunteers = volunteerService.edit(TEST_Volunteers);

        assertsObject(EXPECTED_Volunteers, TEST_Volunteers);
    }

    /**
     * удаление волонтера - по id
     * @Note для тестирования используется Mockito
     */
    @Test
    public void shouldBeRemoveVolunteer() {
        TEST_Volunteers.setId(1L);
        Mockito.when(volunteerRepository.findById(TEST_Volunteers.getId())).thenReturn(Optional.of(TEST_Volunteers));
        EXPECTED_Volunteers = volunteerService.remove(TEST_Volunteers);

        assertsObject(EXPECTED_Volunteers, TEST_Volunteers);
    }

}
