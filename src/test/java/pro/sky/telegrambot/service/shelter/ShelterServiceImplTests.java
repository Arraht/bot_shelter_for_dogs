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
import pro.sky.telegrambot.exception.NotFoundShelterByIdException;
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

    @Test
    public void shouldBeReturnNotFoundShelterByIdException() {
        Mockito.when(shelterRepository.findById(-1l)).thenReturn(Optional.ofNullable(null));

        Assertions.assertThrows(NotFoundShelterByIdException.class, ()->shelterService.findById(-1l));
    }

}
