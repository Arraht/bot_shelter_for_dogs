package pro.sky.telegrambot.service.shelter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import pro.sky.telegrambot.entity.Shelter;
import pro.sky.telegrambot.exception.NotFoundShelterByIdException;
import pro.sky.telegrambot.exception.NotNullIdException;
import pro.sky.telegrambot.interfaces.shelter.ShelterService;
import pro.sky.telegrambot.repository.shelter.ShelterRepository;

import java.util.Optional;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ShelterServiceImplTests {
    @Autowired
    private ShelterService shelterService ;
    @MockBean
    private ShelterRepository shelterRepository;

    private final Shelter TEST_SHELTER = new Shelter();
    private final Shelter TEST_SHELTER_WITH_NULL_ID = new Shelter();

    private Shelter EXPEPTED_SHELTER = new Shelter();

    @BeforeEach
    void init_shelter(){
        TEST_SHELTER.setId(1l);
        TEST_SHELTER.setAddress("TEST_address");
        TEST_SHELTER.setName("TEST_name");
        TEST_SHELTER.setWorkSchedule("TES_schedule");
        TEST_SHELTER.setSecurityContact("+7-9***");
        TEST_SHELTER.setGeneralRecommendationsOnSafety("TEST_recommendation");
    }

    private void assertsObject(Shelter expected, Shelter actual) {

        Assertions.assertEquals(expected.getId(),actual.getId());
        Assertions.assertEquals(expected.getAddress(),actual.getAddress());
        Assertions.assertEquals(expected.getName(),actual.getName());
        Assertions.assertEquals(expected.getWorkSchedule(),actual.getWorkSchedule());
        Assertions.assertEquals(expected.getSecurityContact(),actual.getSecurityContact());
        Assertions.assertEquals(expected.getGeneralRecommendationsOnSafety(),actual.getGeneralRecommendationsOnSafety());

        Assertions.assertEquals(expected.toString(), actual.toString());
        Assertions.assertEquals(expected.hashCode(), actual.hashCode());
        Assertions.assertTrue(expected.equals(actual));

    }

    /**
     * проверяет возрвщается ли ошибка если не найден приют в БД
     * @Note для тестирования используется Mockito
     */
    @Test
    public void shouldBeReturnNotFoundShelterByIdException() {
        Mockito.when(shelterRepository.findById(-1l)).thenReturn(Optional.ofNullable(null));

        Assertions.assertThrows(NotFoundShelterByIdException.class, ()->shelterService.findById(-1l));
    }

    /**
     * проверяет совпадает ли идентификатор по id у приютов при поиске в методе
     * @Note для тестирования используется Mockito
     */
    @Test
    public void shouldBeАFindByIdShelter() {
        Mockito.when(shelterRepository.findById(TEST_SHELTER.getId())).thenReturn(Optional.ofNullable(TEST_SHELTER));
        EXPEPTED_SHELTER = shelterService.findById(TEST_SHELTER.getId());
        assertsObject(EXPEPTED_SHELTER, TEST_SHELTER);
    }

    /**
     * добавляет приют (без id)
     * @Note для тестирования используется Mockito
     */
    @Test
    public void shouldBeAddShelter() {
        Mockito.when(shelterRepository.save(TEST_SHELTER_WITH_NULL_ID)).thenReturn(TEST_SHELTER);
        EXPEPTED_SHELTER = shelterService.add(TEST_SHELTER_WITH_NULL_ID);
        assertsObject(EXPEPTED_SHELTER, TEST_SHELTER);
    }

    /**
     * попытка добавить приют у которого уже присвоен id
     */
    @Test
    public void addShelterShouldBeReturnNotNullIdException() {
        Assertions.assertThrows(NotNullIdException.class, ()-> shelterService.add(TEST_SHELTER));
    }

    /**
     * поиск приюта - по id
     * @Note для тестирования используется Mockito
     */
    @Test
    public void shouldBeFindShelter() {
        Mockito.when(shelterRepository.findById(TEST_SHELTER.getId())).thenReturn(Optional.of(TEST_SHELTER));
        EXPEPTED_SHELTER = shelterService.find(TEST_SHELTER);
        assertsObject(EXPEPTED_SHELTER, TEST_SHELTER);
    }

    /**
     * поиск приюта - по id
     * @Note для тестирования используется Mockito
     */
    @Test
    public void tryFindShelterShouldBeReturnNotFoundShelterByIdException() {
        Mockito.when(shelterRepository.findById(TEST_SHELTER.getId())).thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundShelterByIdException.class, ()-> shelterService.find(TEST_SHELTER));
    }

    /**
     * поиск приюта - по id и редактирвоание данных на примере изменения имени
     * @Note для тестирования используется Mockito
     */
    @Test
    public void shouldBeEditShelter() {
        EXPEPTED_SHELTER.setName("TEST NAME MUST CHANGE");
        TEST_SHELTER.setName("NAME WAS CHANGED");

        Mockito.when(shelterRepository.findById(TEST_SHELTER.getId())).thenReturn(Optional.of(TEST_SHELTER));
        Mockito.when(shelterRepository.save(TEST_SHELTER)).thenReturn(TEST_SHELTER);

        EXPEPTED_SHELTER = shelterService.edit(TEST_SHELTER);

        assertsObject(EXPEPTED_SHELTER, TEST_SHELTER);
    }

    /**
     * удаление приюта - по id
     * @Note для тестирования используется Mockito
     */
    @Test
    public void shouldBeRemoveShelter() {
        TEST_SHELTER.setId(1L);
        Mockito.when(shelterRepository.findById(TEST_SHELTER.getId())).thenReturn(Optional.of(TEST_SHELTER));
        EXPEPTED_SHELTER = shelterService.remove(TEST_SHELTER);
        assertsObject(EXPEPTED_SHELTER, TEST_SHELTER);
    }

}
