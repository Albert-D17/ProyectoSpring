package edu.unimagdalena.lms.DTO;

import edu.unimagdalena.lms.entitles.Enums.AssessmentType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

public class AssessmentDTO {
public record  AssessmentCreateRequest(
        AssessmentType type,
        @Min(0)
        @Max(100)
        Integer score,
        Instant takenAt,
        UUID studentId,
        UUID courseId
) implements Serializable {}

    public record  AssesmentUpdateRequest(
         AssessmentType type,
         @Min(0)
         @Max(100)
         int score,
         Instant takenAt,
         UUID studentId,
         UUID courseId
    ) implements Serializable {}


    public record  AssesmentResponseDTO(
            UUID id,
            AssessmentType type,
            int score,
            Instant takenAt,
            UUID studentId,
            UUID courseId
    ) implements Serializable {}
}

