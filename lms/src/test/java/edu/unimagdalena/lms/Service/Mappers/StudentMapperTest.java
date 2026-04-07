package edu.unimagdalena.lms.Service.Mappers;

import edu.unimagdalena.lms.DTO.StudentDTO;
import edu.unimagdalena.lms.entities.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class StudentMapperTest {

    private final StudentMapper mapper = Mappers.getMapper(StudentMapper.class);

    private UUID id;
    private Student entity;

    @BeforeEach
    void setUp() {
        id = UUID.randomUUID();
        entity = Student.builder()
                .id(id)
                .fullName("Pepito")
                .email("pepito@unimag.edu")
                .createdAt(Instant.now())
                .build();
    }

    @Test
    void toEntity_shouldMapCreateRequest() {
        Student result = mapper.toEntity(new StudentDTO.StudentCreateRequest("Pepito", "pepito@unimag.edu"));

        assertThat(result.getFullName()).isEqualTo("Pepito");
        assertThat(result.getEmail()).isEqualTo("pepito@unimag.edu");
    }

    @Test
    void toResponse_shouldMapEntity() {
        StudentDTO.StudentResponse response = mapper.toResponse(entity);

        assertThat(response.id()).isEqualTo(id);
        assertThat(response.fullName()).isEqualTo("Pepito");
        assertThat(response.email()).isEqualTo("pepito@unimag.edu");
    }

    @Test
    void updateEntity_shouldIgnoreNullFields() {
        mapper.updateEntity(new StudentDTO.StudentUpdateRequest("Juanito", null), entity);

        assertThat(entity.getFullName()).isEqualTo("Juanito");
        assertThat(entity.getEmail()).isEqualTo("pepito@unimag.edu"); // no cambió
    }
}