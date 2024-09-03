package pro.sky.telegrambot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.telegrambot.interfaces.PictureService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
public class PictureServiceImpl implements PictureService {

    private Logger logger = LoggerFactory.getLogger(PictureServiceImpl.class);

    @Value("${path.to.pictures.folder}")
    private String picturesDir;

    @Override
    public Path uploadPicture(Long id, MultipartFile file) throws IOException {

        String filName = id + "." + getExtensions(file.getOriginalFilename());
        Path filePath = Path.of(picturesDir, filName);
        logger.debug("filePath={}",filePath);
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try (
                InputStream is = file.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        } catch (IOException e) {
            logger.error("An error occurred while transferring the file. {}{} , message", picturesDir, filName, e.getMessage());
            throw e;
        }
        return filePath;
    }

    private String getExtensions(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}
