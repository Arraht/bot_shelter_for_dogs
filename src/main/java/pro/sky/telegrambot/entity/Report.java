package pro.sky.telegrambot.entity;


import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "report")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "time_created")
    private LocalDateTime timeCreated;

    @Column(name = "time_received_text")
    private LocalDateTime timeReceivedText;

    @Column(name = "text_from_client")
    private String textReportFromClient;

    @Column(name = "time_received_photo")
    private LocalDateTime timeReceivedPhoto;

    private Boolean accepted;

    @OneToOne
    @JoinColumn(name = "volunteer_id")
    private Volunteer whoAccepted;

    @OneToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;

    public Report() {
    }

    public Boolean getAccepted() {
        return accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public String getTextReportFromClient() {
        return textReportFromClient;
    }

    public void setTextReportFromClient(String textReportFromClient) {
        this.textReportFromClient = textReportFromClient;
    }

    public LocalDateTime getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(LocalDateTime timeCreated) {
        this.timeCreated = timeCreated;
    }

    public LocalDateTime getTimeReceivedPhoto() {
        return timeReceivedPhoto;
    }

    public void setTimeReceivedPhoto(LocalDateTime timeReceivedPhoto) {
        this.timeReceivedPhoto = timeReceivedPhoto;
    }

    public LocalDateTime getTimeReceivedText() {
        return timeReceivedText;
    }

    public void setTimeReceivedText(LocalDateTime timeReceivedText) {
        this.timeReceivedText = timeReceivedText;
    }

    public Volunteer getWhoAccepted() {
        return whoAccepted;
    }

    public void setWhoAccepted(Volunteer whoAccepted) {
        this.whoAccepted = whoAccepted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Report report = (Report) o;
        return Objects.equals(id, report.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", pet=" + pet +
                ", timeCreated=" + timeCreated +
                ", timeReceivedPhoto=" + timeReceivedPhoto +
                ", timeReceivedText=" + timeReceivedText +
                ", textReportFromClient='" + textReportFromClient + '\'' +
                ", client=" + client +
                ", accepted=" + accepted +
                ", whoAccepted=" + whoAccepted +
                '}';
    }
}
