package pro.sky.telegrambot.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Objects;

/**
 * @author Игорь:
 * Сущность для хранения данных клиентов в БД
 */
@Data
@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long chatId;
    private Long contact;
    private String userName;
    private boolean parent;

    public Client(Long id, String name, Long chatId) {
        this.id = id;
        this.name = name;
        this.chatId = chatId;
    }

    public Client(Long id, String name, Long chatId, Long contact) {
        this.id = id;
        this.name = name;
        this.chatId = chatId;
        this.contact = contact;
    }

    public Client(Long id, String name, Long chatId, Long contact, String userName) {
        this.id = id;
        this.name = name;
        this.chatId = chatId;
        this.contact = contact;
        this.userName = userName;
    }

    public Client(Long id, String name, Long chatId, Long contact, String userName, boolean parent) {
        this.id = id;
        this.name = name;
        this.chatId = chatId;
        this.contact = contact;
        this.userName = userName;
        this.parent = parent;
    }

    public Client() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(id, client.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public String toString() {
        return "id клиента " + this.id + "; Имя клиента: " + this.name;
    }
}