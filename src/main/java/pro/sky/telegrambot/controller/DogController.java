package pro.sky.telegrambot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pro.sky.telegrambot.entity.Dog;
import pro.sky.telegrambot.entity.Pet;
import pro.sky.telegrambot.exception.NotFoundDogByIdException;
import pro.sky.telegrambot.exception.NotNullIdException;
import pro.sky.telegrambot.interfaces.shelter.PetService;

@Controller
@RequestMapping("/dog")
public class DogController {

    private final PetService dogService;

    public DogController(PetService dogService) {
        this.dogService = dogService;
    }

    @Operation(summary = "Добавление собаки в базу данных",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "возвращает созданную в базе данных собаку",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Dog.class))
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "возвращает исключение если заполнен id в структуре запроса",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = NotNullIdException.class))
                            )
                    )
            },
            tags = "Dog"
    )
    @PostMapping
    public ResponseEntity<Pet> addDog(@Parameter(description = "структура собака", required = true)
                                                  @RequestBody Dog dog) {
        return ResponseEntity.ok(dogService.add(dog));
    }

    @Operation(summary = "Поиск собаки в базе данных",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "возвращает найденную в базе данных собаку",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Dog.class))
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "возвращает исключение если собака по id в структуре запроса не найдена в базе данных",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = NotFoundDogByIdException.class))
                            )
                    )
            },
            tags = "Dog"
    )
    @GetMapping
    public ResponseEntity<Pet> findDog(@Parameter(description = "структура собака", required = true)
                                                   @RequestBody Dog dog) {
        return ResponseEntity.ok(dogService.find(dog));
    }

    @Operation(summary = "Редактирование собакb в базе данных",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "возвращает отредактированную в базе данных собаку",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Dog.class))
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "возвращает исключение если собака по id в структуре запроса не найдена в базе данных",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = NotFoundDogByIdException.class))
                            )
                    )
            },
            tags = "Dog"
    )
    @PutMapping
    public ResponseEntity<Pet> editDog( @Parameter(description = "структура собака", required = true)
                                                    @RequestBody Dog dog
    ) {
        return ResponseEntity.ok(dogService.edit(dog));
    }
    @Operation(summary = "Удаление волонтера в базе данных",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "возвращает удаленную в базе данных собаку",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Dog.class))
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "возвращает исключение если собака по id в структуре запроса не найдена в базе данных",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = NotFoundDogByIdException.class))
                            )
                    )
            },
            tags = "Dog"
    )
    @DeleteMapping
    public ResponseEntity<Pet> deleteDog(@Parameter(description = "структура собака", required = true)
                                                     @RequestBody Dog dog) {
        return ResponseEntity.ok(dogService.remove(dog));
    }
}

