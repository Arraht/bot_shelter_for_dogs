package pro.sky.telegrambot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.telegrambot.entity.Shelter;
import pro.sky.telegrambot.exception.NotFoundShelterByIdException;
import pro.sky.telegrambot.exception.NotNullIdException;
import pro.sky.telegrambot.interfaces.shelter.ShelterService;

@RestController
@RequestMapping("/shelter")
public class ShelterController {
    private final ShelterService shelterService;

    public ShelterController(ShelterService shelterService) {
        this.shelterService = shelterService;
    }

    @Operation(summary = "Добавление приюта в базу данных",
        responses = {
            @ApiResponse(
                    responseCode = "200",
                    description = "возвращает созданный в базе данных приют",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = Shelter.class))
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
            tags = "Shelter"
    )
    @PostMapping
    public ResponseEntity<Shelter> addShelter(@Parameter(description = "структура приют", required = true)
                                                  @RequestBody Shelter shelter) {
        return ResponseEntity.ok(shelterService.add(shelter));
    }

    @Operation(summary = "Поиск приюта в базе данных",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "возвращает найденный в базе данных приют",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Shelter.class))
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "возвращает исключение если приют по id в структуре запроса не найден в базе данных",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = NotFoundShelterByIdException.class))
                            )
                    )
            },
            tags = "Shelter"
    )
    @GetMapping("/{id}")
    public ResponseEntity<Shelter> findShelter(@Parameter(description = "id приют", required = true)
                                                   @PathVariable Long id) {
        return ResponseEntity.ok(shelterService.findById(id));
    }

    @Operation(summary = "Редактирование приюта в базе данных",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "возвращает отредактированный в базе данных приют",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Shelter.class))
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "возвращает исключение если приют по id в структуре запроса не найден в базе данных",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = NotFoundShelterByIdException.class))
                            )
                    )
            },
            tags = "Shelter"
    )
    @PutMapping
    public ResponseEntity<Shelter> editShelter(@Parameter(description = "структура приют", required = true)
                                                   @RequestBody Shelter shelter) {
        return ResponseEntity.ok(shelterService.edit(shelter));
    }
    @Operation(summary = "Удаление приюта в базе данных",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "возвращает удаленный в базе данных приют",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Shelter.class))
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "возвращает исключение если приют по id в структуре запроса не найден в базе данных",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = NotFoundShelterByIdException.class))
                            )
                    )
            },
            tags = "Shelter"
    )
    @DeleteMapping
    public ResponseEntity<Shelter> deleteShelter(@Parameter(description = "структура приют", required = true)
                                                     @RequestBody Shelter shelter) {
        return ResponseEntity.ok(shelterService.remove(shelter));
    }
}
