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
import pro.sky.telegrambot.entity.PetPhoto;
import pro.sky.telegrambot.interfaces.shelter.PetPhotoService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@Controller
@RequestMapping("/dog")
public class PetPhotoController {
    private final PetPhotoService petPhotoService;

    public PetPhotoController(PetPhotoService petPhotoService) {
        this.petPhotoService = petPhotoService;
    }


    @Operation(summary = "Добавление картинки домашнего животного в базу данных",
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
            tags = "Pet"
    )
    @PostMapping(value = "/{petId}/picture", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> upload(@PathVariable Long petId
            , @RequestParam MultipartFile picture) throws IOException {
        petPhotoService.upload(petId, picture);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{petId}/picture/from-db")
    public ResponseEntity<byte[]> download(@PathVariable Long petId) {
        PetPhoto picture = petPhotoService.find(petId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(picture.getMediaType()));
        headers.setContentLength(picture.getData().length);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(picture.getData());
    }

    @GetMapping(value = "/{petId}/picture/from-file")
    public void download(@PathVariable Long petId, HttpServletResponse response) throws IOException {
        PetPhoto picture = petPhotoService.find(petId);
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
