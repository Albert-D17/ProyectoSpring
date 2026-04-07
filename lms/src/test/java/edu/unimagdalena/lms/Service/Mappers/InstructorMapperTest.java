package edu.unimagdalena.lms.Service.Mappers;

import edu.unimagdalena.lms.DTO.InstructorDTO;
import edu.unimagdalena.lms.entities.Instructor;
import edu.unimagdalena.lms.entities.InstructorProfile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class InstructorMapperTest {

    private final InstructorMapper mapper = Mappers.getMapper(InstructorMapper.class);

    private UUID id;
    private InstructorProfile profile;
    private Instructor entity;

    @BeforeEach
    void setUp() {
        id = UUID.randomUUID();
        profile = InstructorProfile.builder()
                .phone("3001234567")
                .bio("Profesor de sistemas")
                .build();
        entity = Instructor.builder()
                .id(id)
                .fullName("Teacher Jaime")
                .email("prof@unimag.edu")
                .profile(profile)
                .build();
    }

    @Test
    void toEntity_shouldMapCreateRequest() {
        Instructor result = mapper.toEntity(new InstructorDTO.InstructorCreateRequest(
                "Teacher Jaime", "prof@unimag.edu",
                new InstructorDTO.InstructorProfileDto("3001234567", "Profesor de sistemas")));

        assertThat(result.getFullName()).isEqualTo("Teacher Jaime");
        assertThat(result.getEmail()).isEqualTo("prof@unimag.edu");
    }

    @Test
    void toResponse_shouldMapEntity() {
        InstructorDTO.InstructorResponse response = mapper.toResponse(entity);

        assertThat(response.id()).isEqualTo(id);
        assertThat(response.fullName()).isEqualTo("Teacher Jaime");
        assertThat(response.profile().phone()).isEqualTo("3001234567");
    }

    @Test
    void updateEntity_shouldIgnoreNullFields() {
        mapper.updateEntity(new InstructorDTO.InstructorUpdateRequest("Teacher Carlos", null, null), entity);

        assertThat(entity.getFullName()).isEqualTo("Teacher Carlos");
        assertThat(entity.getEmail()).isEqualTo("prof@unimag.edu"); // no cambió
    }
}
