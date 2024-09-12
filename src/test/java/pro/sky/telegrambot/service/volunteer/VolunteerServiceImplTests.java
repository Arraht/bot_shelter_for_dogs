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
import pro.sky.telegrambot.entity.Volunteer;
import pro.sky.telegrambot.exception.NotFoundVolunteerByIdException;
import pro.sky.telegrambot.exception.NotNullIdException;
import pro.sky.telegrambot.interfaces.volunteer.VolunteerService;
import pro.sky.telegrambot.repository.volunteer.VolunteerRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class VolunteerServiceImplTests {
    @Autowired
    private VolunteerService volunteerService ;
    @MockBean
    private VolunteerRepository volunteerRepository;

    private final Volunteer TEST_VOLUNTEER = new Volunteer();
    private final Volunteer TEST_VOLUNTEER_WITH_NULL_ID = new Volunteer();

    private Volunteer EXPECTED_VOLUNTEER = new Volunteer();

    @BeforeEach
    void init_volunteer(){
        TEST_VOLUNTEER.setId(1l);
        TEST_VOLUNTEER.setName("TEST_name");
        TEST_VOLUNTEER.setNickName("TEST_NICKNAME");
        TEST_VOLUNTEER.setWorkingFirstTime(LocalDateTime.now());
        TEST_VOLUNTEER.setWorkingLastTime(LocalDateTime.now());
    }

    private void assertsObject(Volunteer expected, Volunteer actual) {
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
        Mockito.when(volunteerRepository.findById(TEST_VOLUNTEER.getId())).thenReturn(Optional.ofNullable(TEST_VOLUNTEER));
        EXPECTED_VOLUNTEER = volunteerService.findById(TEST_VOLUNTEER.getId());
        assertsObject(EXPECTED_VOLUNTEER, TEST_VOLUNTEER);
    }

    /**
     * добавляет волонтера(без id)
     * @Note для тестирования используется Mockito
     */
    @Test
    public void shouldBeAddVolunteer() {
        Mockito.when(volunteerRepository.save(TEST_VOLUNTEER_WITH_NULL_ID)).thenReturn(TEST_VOLUNTEER);
        EXPECTED_VOLUNTEER = volunteerService.add(TEST_VOLUNTEER_WITH_NULL_ID);
        assertsObject(EXPECTED_VOLUNTEER, TEST_VOLUNTEER);
    }

    /**
     * попытка добавить волонтера у которого уже присвоен id
     */
    @Test
    public void addVolunteerShouldBeReturnNotNullIdException() {
        Assertions.assertThrows(NotNullIdException.class, ()-> volunteerService.add(TEST_VOLUNTEER));
    }

    /**
     * поиск волонтера - по id
     * @Note для тестирования используется Mockito
     */
    @Test
    public void shouldBeFindVolunteer() {
        Mockito.when(volunteerRepository.findById(TEST_VOLUNTEER.getId())).thenReturn(Optional.of(TEST_VOLUNTEER));
        EXPECTED_VOLUNTEER = volunteerService.find(TEST_VOLUNTEER);

        assertsObject(EXPECTED_VOLUNTEER, TEST_VOLUNTEER);
    }

    /**
     * поиск волонтера - по id
     * @Note для тестирования используется Mockito
     */
    @Test
    public void tryFindVolunteerShouldBeReturnNotFoundVolunteerByIdException() {
        Mockito.when(volunteerRepository.findById(TEST_VOLUNTEER.getId())).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundVolunteerByIdException.class, ()-> volunteerService.find(TEST_VOLUNTEER));
    }

    /**
     * поиск волонтера - по id и редактирвоание данных на примере изменения имени
     * @Note для тестирования используется Mockito
     */
    @Test
    public void shouldBeEditVolunteer() {
        EXPECTED_VOLUNTEER.setName("TEST NAME MUST CHANGE");
        TEST_VOLUNTEER.setName("NAME WAS CHANGED");

        Mockito.when(volunteerRepository.findById(TEST_VOLUNTEER.getId())).thenReturn(Optional.of(TEST_VOLUNTEER));
        Mockito.when(volunteerRepository.save(TEST_VOLUNTEER)).thenReturn(TEST_VOLUNTEER);

        EXPECTED_VOLUNTEER = volunteerService.edit(TEST_VOLUNTEER);

        assertsObject(EXPECTED_VOLUNTEER, TEST_VOLUNTEER);
    }

    /**
     * удаление волонтера - по id
     * @Note для тестирования используется Mockito
     */
    @Test
    public void shouldBeRemoveVolunteer() {
        TEST_VOLUNTEER.setId(1L);
        Mockito.when(volunteerRepository.findById(TEST_VOLUNTEER.getId())).thenReturn(Optional.of(TEST_VOLUNTEER));
        EXPECTED_VOLUNTEER = volunteerService.remove(TEST_VOLUNTEER);

        assertsObject(EXPECTED_VOLUNTEER, TEST_VOLUNTEER);
    }

}
