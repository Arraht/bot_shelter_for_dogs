package pro.sky.telegrambot.service;

import pro.sky.telegrambot.Entity.Shelter;

public interface ShelterService {
    Shelter add(Shelter shelter);
    Shelter find(Shelter shelter);
    Shelter edit(Shelter shelter);
    Shelter remove(Shelter shelter);
}
