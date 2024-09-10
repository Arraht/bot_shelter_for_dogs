package pro.sky.telegrambot.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@DiscriminatorValue("DOG")
public class Dog extends Pet {
    public Dog() {
        super();
    }
}
