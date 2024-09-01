package pro.sky.telegrambot.Entity;

import javax.persistence.*;
import java.util.Objects;


@Entity
public class Shelter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;
    private String workSchedule;
    @OneToOne
    private DirectionToShelterPicture directionsPicture;
    private String securityContact;
    private String generalRecommendationsOnSafety;

    public Shelter() {

    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public DirectionToShelterPicture getDirectionsPicture() {
        return directionsPicture;
    }

    public void setDirectionsPicture(DirectionToShelterPicture directionsPicture) {
        this.directionsPicture = directionsPicture;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGeneralRecommendationsOnSafety() {
        return generalRecommendationsOnSafety;
    }

    public void setGeneralRecommendationsOnSafety(String generalRecommendationsOnSafety) {
        this.generalRecommendationsOnSafety = generalRecommendationsOnSafety;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecurityContact() {
        return securityContact;
    }

    public void setSecurityContact(String securityContact) {
        this.securityContact = securityContact;
    }

    public String getWorkSchedule() {
        return workSchedule;
    }

    public void setWorkSchedule(String workSchedule) {
        this.workSchedule = workSchedule;
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shelter shelter = (Shelter) o;
        return Objects.equals(id, shelter.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
