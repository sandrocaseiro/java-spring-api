package dev.sandrocaseiro.template.models.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Schema(description = "Model to show how API must enforce value formats and property names")
public class DUserReportResp {
    @Schema(description = "User's id", example = "1")
    private int id;
    @Schema(description = "User's name", example = "user1")
    private String name;
    @Schema(description = "User's email", example = "user1@mail.com")
    private String email;
    @Schema(description = "User's group id", example = "1")
    private int groupId;
    @Schema(description = "User's balance. Should be preceded by the monetary code", example = "USD 155.75")
    private String balance;
    @Schema(description = "User's roles count. Counters should have the sulfix 'Count'", example = "1")
    private int rolesCount;
    @JsonProperty("isActive")
    @Schema(description = "User's status", example = "true")
    private boolean active;
    @JsonProperty("creationDateTime")
    @Schema(description = "User's creation datetime. DateTime should have the sulfix 'DateTime' and the format 'uuuu-MM-ddTHH:mm:ssZ'", example = "2020-12-01T20:53:15Z")
    private LocalDateTime creationDate;

    @JsonProperty("creationDate")
    @Schema(description = "User's creation date. Date should have the sulfix 'Date' and the format 'uuuu-MM-dd'", example = "2020-12-01")
    public LocalDate getDate() {
        return creationDate.toLocalDate();
    }

    @JsonProperty("creationTime")
    @Schema(description = "User's creation time. Time should have the sulfix 'Time' and the format 'HH:mm:ss'", example = "20:53:15")
    public LocalTime getTime() {
        return creationDate.toLocalTime();
    }
}
