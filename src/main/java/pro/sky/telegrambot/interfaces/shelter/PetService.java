package pro.sky.telegrambot.interfaces.shelter;

import pro.sky.telegrambot.entity.Pet;

public interface PetService {
    Pet add(Pet pet);
    Pet find(Pet pet);
    Pet edit(Pet pet);
    Pet remove(Pet pet);
    Pet findById(Long pet);
}
