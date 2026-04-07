package edu.unimagdalena.lms.DTO;

import edu.unimagdalena.lms.entities.Enums.CourseStatus;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

public class CourseDTO {
    public record  CourseCreateRequest(
            @NotBlank String title,
            CourseStatus status,
            UUID instructorId
    )implements Serializable {}

    public record  CourseUpdateRequest(
            @NotBlank String title,
            CourseStatus status,
            UUID instructorId
    )implements Serializable {}

    public record  CourseResponse(
            UUID id,
            String title,
            CourseStatus status,
            Instant createdAt,
            UUID instructorId
    )implements Serializable {}

}
