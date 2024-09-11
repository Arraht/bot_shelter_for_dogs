package pro.sky.telegrambot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.telegrambot.entity.PhotoForReport;
import pro.sky.telegrambot.interfaces.shelter.PhotoForReportService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@Controller
@RequestMapping("/report")
public class PhotoForReportController {
    private final PhotoForReportService photoForReportService;

    public PhotoForReportController(PhotoForReportService photoForReportService) {
        this.photoForReportService = photoForReportService;
    }


    @Operation(summary = "Добавление картинки направления до приюта в базу данных",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "возвращает ответ при успехе"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "возвращает исключение если возникло исключение при добавлении картинки",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = IOException.class))
                            )
                    )
            },
            tags = "Report"
    )
    @PostMapping(value = "/{reportId}/picture", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> upload(@PathVariable Long reportId
            , @RequestParam MultipartFile picture) throws IOException {
        photoForReportService.upload(reportId, picture);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{reportId}/picture/from-db")
    public ResponseEntity<byte[]> download(@PathVariable Long reportId) {
        PhotoForReport picture = photoForReportService.find(reportId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(picture.getMediaType()));
        headers.setContentLength(picture.getData().length);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(picture.getData());
    }

    @GetMapping(value = "/{reportId}/picture/from-file")
    public void download(@PathVariable Long reportId, HttpServletResponse response) throws IOException {
        PhotoForReport picture = photoForReportService.find(reportId);
        Path path = Path.of(picture.getFilePath());
        try (InputStream is = Files.newInputStream(path);
             OutputStream os = response.getOutputStream();
        ) {
            response.setStatus(200);
            response.setContentType(picture.getMediaType());
            response.setContentLength((int) picture.getFileSize());
            is.transferTo(os);
        }
    }
}