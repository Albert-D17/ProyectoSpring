package edu.unimagdalena.lms.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

public class StudentDTO {
    public record StudentCreateRequest(
            @NotBlank String fullName,
            @Email @NotBlank String email
    ) implements Serializable {}

    public record StudentUpdateRequest(
            String fullName,
            String email
    ) implements Serializable {}

    public record StudentResponse(
            UUID id,
            String fullName,
            String email,
            Instant createdAt
    ) implements Serializable {}
}
