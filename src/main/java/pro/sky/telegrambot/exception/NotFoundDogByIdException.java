package pro.sky.telegrambot.exception;

public class NotFoundDogByIdException extends RuntimeException {
    public NotFoundDogByIdException(String message) {
        super(message);
    }
}
