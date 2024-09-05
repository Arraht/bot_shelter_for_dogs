package pro.sky.telegrambot.interfaces;

import pro.sky.telegrambot.entity.Shelter;

public interface ShelterService {
    Shelter add(Shelter shelter);
    Shelter find(Shelter shelter);
    Shelter edit(Shelter shelter);
    Shelter remove(Shelter shelter);

    Shelter findById(Long shelterId);
}
