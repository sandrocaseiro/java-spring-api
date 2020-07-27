package dev.sandrocaseiro.apitemplate.models.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class DUserReportResp {
    private int id;
    private String name;
    private String email;
    private int groupId;
    @JsonProperty("balanceAmount")
    private String balance;
    private int rolesCount;
    @JsonProperty("isActive")
    private boolean active;
    @JsonProperty("creationDateTime")
    private LocalDateTime creationDate;

    @JsonProperty("creationDate")
    public LocalDate getDate() {
        return creationDate.toLocalDate();
    }

    @JsonProperty("creationTime")
    public LocalTime getTime() {
        return creationDate.toLocalTime();
    }
}
