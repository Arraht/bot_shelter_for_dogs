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
import pro.sky.telegrambot.entity.Volunteer;
import pro.sky.telegrambot.exception.NotFoundVolunteerByIdException;
import pro.sky.telegrambot.exception.NotNullIdException;
import pro.sky.telegrambot.interfaces.volunteer.VolunteerService;

@RestController
@RequestMapping("/volunteer")
public class VolunteerController {
    private final VolunteerService volunteerService;

    public VolunteerController(VolunteerService volunteerService) {
        this.volunteerService = volunteerService;
    }

    @Operation(summary = "Добавление волонтера в базу данных",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "возвращает созданного в базе данных волонтера",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Volunteer.class))
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
            tags = "Volunteer"
    )
    @PostMapping
    public ResponseEntity<Volunteer> addVolunteer(@Parameter(description = "структура волонтер", required = true)
                                                @RequestBody Volunteer volunteer) {
        return ResponseEntity.ok(volunteerService.add(volunteer));
    }

    @Operation(summary = "Поиск волонтера в базе данных",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "возвращает найденного в базе данных волонтер",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Volunteer.class))
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "возвращает исключение если волонтер по id в структуре запроса не найден в базе данных",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = NotFoundVolunteerByIdException.class))
                            )
                    )
            },
            tags = "Volunteer"
    )
    @GetMapping
    public ResponseEntity<Volunteer> findVolunteer(@Parameter(description = "структура волонтер", required = true)
                                               @RequestBody Volunteer volunteer) {
        return ResponseEntity.ok(volunteerService.find(volunteer));
    }

    @Operation(summary = "Редактирование волонтера в базе данных",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "возвращает отредактированного в базе данных волонтер",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Volunteer.class))
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "возвращает исключение если волонтер по id в структуре запроса не найден в базе данных",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = NotFoundVolunteerByIdException.class))
                            )
                    )
            },
            tags = "Volunteer"
    )
    @PutMapping
    public ResponseEntity<Volunteer> editVolunteer( @Parameter(description = "структура приют", required = true)
                                                    @RequestBody Volunteer volunteer
    ) {
        return ResponseEntity.ok(volunteerService.edit(volunteer));
    }
    @Operation(summary = "Удаление волонтера в базе данных",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "возвращает удаленного в базе данных волонтера",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Volunteer.class))
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "возвращает исключение если волонтер по id в структуре запроса не найден в базе данных",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = NotFoundVolunteerByIdException.class))
                            )
                    )
            },
            tags = "Volunteer"
    )
    @DeleteMapping
    public ResponseEntity<Volunteer> deleteVolunteer(@Parameter(description = "структура приют", required = true)
                                                 @RequestBody Volunteer volunteer) {
        return ResponseEntity.ok(volunteerService.remove(volunteer));
    }
}
