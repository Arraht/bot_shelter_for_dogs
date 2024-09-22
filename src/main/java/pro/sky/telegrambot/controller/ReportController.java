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
import pro.sky.telegrambot.entity.Report;
import pro.sky.telegrambot.entity.Volunteers;
import pro.sky.telegrambot.exception.NotFoundReportByIdException;
import pro.sky.telegrambot.exception.NotNullIdException;
import pro.sky.telegrambot.interfaces.shelter.ReportService;

import java.util.Collection;
import java.util.Collections;

/**
 * <p>сервис ReportController для обработки отчетов по домашним животным</p>
 *
 * @Author manxix69
 */
@Controller
@RequestMapping("/report")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @Operation(summary = "Добавление отчета в базу данных",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "возвращает созданную в базе данных отчет",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Report.class))
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
            tags = "Report"
    )
    @PostMapping
    public ResponseEntity<Report> addReport(@Parameter(description = "структура отчет", required = true)
                                            @RequestBody Report report) {
        return ResponseEntity.ok(reportService.add(report));
    }

    @Operation(summary = "Поиск отчета в базе данных",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "возвращает найденную в базе данных отчет",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Report.class))
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "возвращает исключение если отчет по id в структуре запроса не найдена в базе данных",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = NotFoundReportByIdException.class))
                            )
                    )
            },
            tags = "Report"
    )
    @GetMapping("/{id}")
    public ResponseEntity<Report> findReport(@Parameter(description = "id отчет", required = true)
                                                 @PathVariable Long id) {
        return ResponseEntity.ok(reportService.findById(id));
    }

    @Operation(summary = "Редактирование отчета в базе данных",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "возвращает отредактированный в базе данных отчет",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Report.class))
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "возвращает исключение если отчет по id в структуре запроса не найден в базе данных",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = NotFoundReportByIdException.class))
                            )
                    )
            },
            tags = "Report"
    )
    @PutMapping
    public ResponseEntity<Report> editReport( @Parameter(description = "структура отчет", required = true)
                                              @RequestBody Report report
    ) {
        return ResponseEntity.ok(reportService.edit(report));
    }

    @Operation(summary = "Удаление отчета в базе данных",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "возвращает удаленную в базе данных отчет",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Report.class))
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "возвращает исключение если отчет по id в структуре запроса не найдена в базе данных",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = NotFoundReportByIdException.class))
                            )
                    )
            },
            tags = "Report"
    )
    @DeleteMapping
    public ResponseEntity<Report> deleteReport(@Parameter(description = "структура отчет", required = true)
                                               @RequestBody Report report) {
        return ResponseEntity.ok(reportService.remove(report));
    }

    @Operation(summary = " волонтера в базе данных",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "возвращает удаленную в базе данных отчет",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Report.class))
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "возвращает исключение если отчет по id в структуре запроса не найдена в базе данных",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = NotFoundReportByIdException.class))
                            )
                    )
            },
            tags = "Report"
    )
    @PutMapping("/{reportId}/approve")
    public ResponseEntity<Report> approvedReport(
                                            @Parameter(description = "идентификатор отчета", required = true)
                                            @RequestBody Long reportId,
                                            @Parameter(description = "Проверен", required = true)
                                            @RequestBody Boolean isApproved,
                                            @Parameter(description = "Каким волонтерем проведена проверка отчета", required = true)
                                            @RequestBody Volunteers whomApproved
    ) {
        return ResponseEntity.ok(reportService.approved(reportId, isApproved, whomApproved));
    }

    @Operation(summary = " волонтера в базе данных",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "возвращает коллекцию найденных в базе данных отчетов",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Collections.class))
                            )
                    )
            },
            tags = "Report"
    )
    @GetMapping("/all-by-pet")
    public ResponseEntity<Collection<Report>> getReportsByPet(
            @Parameter(description = "идентификатор домашнего животного", required = true)
            @RequestBody Long petId
    ) {
        return ResponseEntity.ok(reportService.getReportsByPetId(petId));
    }
}
