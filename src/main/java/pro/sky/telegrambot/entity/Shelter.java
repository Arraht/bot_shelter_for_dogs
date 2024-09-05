package pro.sky.telegrambot.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Objects;


@Entity
public class Shelter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;
    private String workSchedule;
    private String securityContact;
    private String generalRecommendationsOnSafety;

    public Shelter() {}

    public Shelter(Long id, String name, String address, String workSchedule, String securityContact, String generalRecommendationsOnSafety) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.workSchedule = workSchedule;
        this.securityContact = securityContact;
        this.generalRecommendationsOnSafety = generalRecommendationsOnSafety;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
    public String toString() {
        return "Shelter{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", generalRecommendationsOnSafety='" + generalRecommendationsOnSafety + '\'' +
                ", securityContact='" + securityContact + '\'' +
                ", workSchedule='" + workSchedule + '\'' +
                '}';
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
