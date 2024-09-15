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
import pro.sky.telegrambot.entity.*;
import pro.sky.telegrambot.exception.NotFoundDogByIdException;
import pro.sky.telegrambot.exception.NotNullIdException;
import pro.sky.telegrambot.interfaces.shelter.PetService;
import pro.sky.telegrambot.repository.shelter.PetRepository;

import java.util.Optional;

@SpringBootTest
@ExtendWith({MockitoExtension.class})
public class DogServiceImplTests {
    @Autowired
    private PetService petService;

    @MockBean
    private PetRepository petRepository;


    private Pet TEST_DOG = new Dog();
    private Pet EXPECTED_DOG = new Dog();

    @BeforeEach
    void init(){
        TEST_DOG.setId(1l);
        TEST_DOG.setSex(false);
        TEST_DOG.setAge(null);
        TEST_DOG.setAlias("name_dog");
        TEST_DOG.setDiet("diet");
        TEST_DOG.setDescription("description");
        TEST_DOG.setWeight(72d);
    }

    private void assertsObject(Pet expected, Pet actual) {
        Assertions.assertEquals(expected.getId(),actual.getId());
        Assertions.assertEquals(expected.getSex(),actual.getSex());
        Assertions.assertEquals(expected.getAge(),actual.getAge());
        Assertions.assertEquals(expected.getAlias(),actual.getAlias());
        Assertions.assertEquals(expected.getDiet(),actual.getDiet());
        Assertions.assertEquals(expected.getDescription(),actual.getDescription());
        Assertions.assertEquals(expected.getWeight(),actual.getWeight());

        Assertions.assertEquals(expected.toString(), actual.toString());
        Assertions.assertEquals(expected.hashCode(), actual.hashCode());
        Assertions.assertTrue(expected.equals(actual));
    }


    @Test
    public void shouldBeReturnNotFoundPetByIdException() {
        Mockito.when(petRepository.findById(-1l)).thenReturn(null);

        Assertions.assertThrows(NotFoundDogByIdException.class, ()-> petService.find(TEST_DOG));
    }

    @Test
    public void shouldBeFind() {
        Mockito.when(petRepository.findById(TEST_DOG.getId())).thenReturn(Optional.of(TEST_DOG));
        EXPECTED_DOG = petService.find(TEST_DOG);
        assertsObject(EXPECTED_DOG, TEST_DOG);
    }

    @Test
    public void shouldBeEdit() {
        Mockito.when(petRepository.findById(TEST_DOG.getId())).thenReturn(Optional.of(TEST_DOG));
        Mockito.when(petRepository.save(TEST_DOG)).thenReturn(TEST_DOG);

        TEST_DOG.setWeight(45.569d);
        TEST_DOG.setSex(true);

        EXPECTED_DOG = petService.edit(TEST_DOG);
        assertsObject(EXPECTED_DOG, TEST_DOG);
    }

    @Test
    public void shouldBeRemove() {
        Mockito.when(petRepository.findById(TEST_DOG.getId())).thenReturn(Optional.of(TEST_DOG));


        EXPECTED_DOG = petService.remove(TEST_DOG);
        assertsObject(EXPECTED_DOG, TEST_DOG);
    }

    @Test
    public void shouldBeAdd() {
        Mockito.when(petRepository.findById(TEST_DOG.getId())).thenReturn(Optional.of(TEST_DOG));
        Mockito.when(petRepository.save(TEST_DOG)).thenReturn(TEST_DOG);

        TEST_DOG.setId(null);
        EXPECTED_DOG.setId(null);

        EXPECTED_DOG = petService.add(TEST_DOG);
        assertsObject(EXPECTED_DOG, TEST_DOG);
    }

    @Test
    public void shouldBeThrownExceptionAdd() {
        Mockito.when(petRepository.findById(TEST_DOG.getId())).thenReturn(Optional.of(TEST_DOG));

        Assertions.assertThrows(NotNullIdException.class, () -> petService.add(TEST_DOG));
    }

    @Test
    public void shouldBeFindById() {
        Mockito.when(petRepository.findById(TEST_DOG.getId())).thenReturn(Optional.of(TEST_DOG));

        EXPECTED_DOG = petService.findById(TEST_DOG.getId());
        assertsObject(EXPECTED_DOG, TEST_DOG);

    }

    @Test
    public void shouldBThrown() {
        Mockito.when(petRepository.findById(TEST_DOG.getId())).thenReturn(Optional.of(null));

        Assertions.assertThrows(NotFoundDogByIdException.class, () -> petService.findById(TEST_DOG.getId()));
    }

    

}
