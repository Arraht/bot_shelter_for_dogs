package pro.sky.telegrambot.Entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "direction_to_shelter_picture")
public class DirectionToShelterPicture extends Picture {

    @OneToOne
    @JoinColumn(name = "shelter_id")
    private Shelter shelter;

    public DirectionToShelterPicture() {
        super();
    }

    public DirectionToShelterPicture(Long id, String filePath, long fileSize, String mediaType, byte[] data, Shelter shelter) {
        super(id, filePath, fileSize, mediaType, data);
        this.shelter = shelter;
    }

    public Shelter getShelter() {
        return shelter;
    }

    public void setShelter(Shelter shelter) {
        this.shelter = shelter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DirectionToShelterPicture directionToShelterPicture = (DirectionToShelterPicture) o;
        return Objects.equals(super.getId(), directionToShelterPicture.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.getId());
    }

    @Override
    public String toString() {
        return "DirectionToShelterPicture{" +
                "shelter=" + shelter +
                "} " + super.toString();
    }
}
