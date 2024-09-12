package pro.sky.telegrambot.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "photo_for_report")
public class PhotoForReport extends Picture {
    @OneToOne
    @JoinColumn(name = "report_id")
    private Report report;

    public PhotoForReport() {
        super();
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhotoForReport that = (PhotoForReport) o;
        return Objects.equals(super.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.getId());
    }
}
