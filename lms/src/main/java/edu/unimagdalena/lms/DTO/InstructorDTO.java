package edu.unimagdalena.lms.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

public class InstructorDTO {
    public record InstructorProfileDto(
            String phone,
            String bio
    ) implements Serializable {}

    public record InstructorCreateRequest(
            @NotBlank String fullName,
            @Email @NotBlank String email,
            InstructorProfileDto profile  // anidado, opcional
    ) implements Serializable {}

    public record InstructorUpdateRequest(
            String fullName,
            String email,
            InstructorProfileDto profile
    ) implements Serializable {}

    public record InstructorResponse(
            UUID id,
            String fullName,
            String email,
            Instant createdAt,
            InstructorProfileDto profile
    ) implements Serializable {}
}
