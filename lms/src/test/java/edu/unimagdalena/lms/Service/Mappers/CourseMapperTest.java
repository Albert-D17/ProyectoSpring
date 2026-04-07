package edu.unimagdalena.lms.Service.Mappers;

import edu.unimagdalena.lms.DTO.CourseDTO;
import edu.unimagdalena.lms.entities.Course;
import edu.unimagdalena.lms.entities.Enums.CourseStatus;
import edu.unimagdalena.lms.entities.Instructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CourseMapperTest {

    private final CourseMapper mapper = Mappers.getMapper(CourseMapper.class);

    private UUID id;
    private UUID instructorId;
    private Instructor instructor;
    private Course entity;

    @BeforeEach
    void setUp() {
        id = UUID.randomUUID();
        instructorId = UUID.randomUUID();
        instructor = Instructor.builder().id(instructorId).build();
        entity = Course.builder()
                .id(id)
                .title("Web Programming")
                .status(CourseStatus.ACTIVE)
                .instructor(instructor)
                .build();
    }

    @Test
    void toEntity_shouldMapCreateRequest() {
        Course result = mapper.toEntity(new CourseDTO.CourseCreateRequest("Web Programming", CourseStatus.ACTIVE, instructorId));

        assertThat(result.getTitle()).isEqualTo("Web Programming");
        assertThat(result.getStatus()).isEqualTo(CourseStatus.ACTIVE);
        assertThat(result.getInstructor().getId()).isEqualTo(instructorId);
    }

    @Test
    void toResponse_shouldMapEntity() {
        CourseDTO.CourseResponse response = mapper.toResponse(entity);

        assertThat(response.id()).isEqualTo(id);
        assertThat(response.title()).isEqualTo("Web Programming");
        assertThat(response.instructorId()).isEqualTo(instructorId);
    }

    @Test
    void updateEntity_shouldIgnoreNullFields() {
        mapper.updateEntity(new CourseDTO.CourseUpdateRequest("Mobile Programming", CourseStatus.ACTIVE, instructorId), entity);

        assertThat(entity.getTitle()).isEqualTo("Mobile Programming");
        assertThat(entity.getStatus()).isEqualTo(CourseStatus.ACTIVE); // no cambió
    }
}