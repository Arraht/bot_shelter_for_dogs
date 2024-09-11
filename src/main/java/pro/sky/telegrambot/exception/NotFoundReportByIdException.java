package pro.sky.telegrambot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NotFoundReportByIdException extends RuntimeException {
    public NotFoundReportByIdException(String message) {
        super(message);
    }
}
