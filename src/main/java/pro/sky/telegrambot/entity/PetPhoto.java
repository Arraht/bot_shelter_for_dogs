package pro.sky.telegrambot.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "pet_photo")
public class PetPhoto extends Picture {
    @OneToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;

    public PetPhoto() {
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PetPhoto petPhoto = (PetPhoto) o;
        return Objects.equals(super.getId(), petPhoto.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.getId());
    }
}
