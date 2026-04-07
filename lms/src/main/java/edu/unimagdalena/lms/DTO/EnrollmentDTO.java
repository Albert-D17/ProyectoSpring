package edu.unimagdalena.lms.DTO;

import edu.unimagdalena.lms.entities.Enums.EnrollmentStatus;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

public class EnrollmentDTO {
    public record  EnrollmentCreateRequest(
            EnrollmentStatus status,
            Instant enrolledAt,
            UUID CourseId,
            UUID studentId

    )implements Serializable {}

    public record  EnrollmentUpdateRequest(
            EnrollmentStatus status
    ) implements Serializable {}

    public record  EnrollmentResponse(
            UUID id,
            EnrollmentStatus status,
            Instant enrolledAt,
            UUID studentId,
            UUID courseId
            )implements Serializable {}

}
