package edu.unimagdalena.lms.DTO;

import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

public class LessonDTO {
    public record LessonCreateRequest(
            @NotBlank String title,
            Instant lessonDate,
            int orderIndex,
            UUID courseId
    ) implements Serializable {}

    public record LessonUpdateRequest(
            String title,
            Instant lessonDate,
            int orderIndex
    ) implements Serializable {}

    public record LessonResponse(
            UUID id,
            String title,
            Instant lessonDate,
            int orderIndex,
            UUID courseId
    ) implements Serializable {}
}
