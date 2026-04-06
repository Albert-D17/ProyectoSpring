package edu.unimagdalena.lms.DTO;

import edu.unimagdalena.lms.entitles.Enums.EnrollmentStatus;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

public class EnrollmentDTO {
    public record  EnrollmentCreateRequest(
            EnrollmentStatus status,
            String enrolledAt,
            UUID CourseId,
            UUID studentId

    )implements Serializable {}

    public record  EnrollmentUpdateDTO(
            EnrollmentStatus status
    ) implements Serializable {}

    public record  EnrollmentResponseDTO(
            UUID id,
            EnrollmentStatus status,
            Instant enrolledAt,
            UUID studentId,
            UUID courseId
            )implements Serializable {}

}
