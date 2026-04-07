package edu.unimagdalena.lms.Service.Mappers;

import edu.unimagdalena.lms.DTO.EnrollmentDTO;
import edu.unimagdalena.lms.entities.Course;
import edu.unimagdalena.lms.entities.Enrollment;
import edu.unimagdalena.lms.entities.Enums.EnrollmentStatus;
import edu.unimagdalena.lms.entities.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class EnrollmentMapperTest {

    private final EnrollmentMapper mapper = Mappers.getMapper(EnrollmentMapper.class);

    private UUID id;
    private Student student;
    private Course course;
    private Enrollment entity;

    @BeforeEach
    void setUp() {
        id = UUID.randomUUID();
        student = Student.builder().id(UUID.randomUUID()).build();
        course = Course.builder().id(UUID.randomUUID()).build();
        entity = Enrollment.builder()
                .id(id)
                .student(student)
                .course(course)
                .status(EnrollmentStatus.ACTIVE)
                .enrolledAt(Instant.now())
                .build();
    }

    @Test
    void toEntity_shouldMapCreateRequest() {
        Enrollment result = mapper.toEntity(new EnrollmentDTO.EnrollmentCreateRequest(
                EnrollmentStatus.ACTIVE, Instant.now(), course.getId(), student.getId()));

        assertThat(result.getStudent().getId()).isEqualTo(student.getId());
        assertThat(result.getCourse().getId()).isEqualTo(course.getId());
        assertThat(result.getStatus()).isEqualTo(EnrollmentStatus.ACTIVE);
    }

    @Test
    void toResponse_shouldMapEntity() {
        EnrollmentDTO.EnrollmentResponse response = mapper.toResponse(entity);

        assertThat(response.id()).isEqualTo(id);
        assertThat(response.studentId()).isEqualTo(student.getId());
        assertThat(response.courseId()).isEqualTo(course.getId());
        assertThat(response.status()).isEqualTo(EnrollmentStatus.ACTIVE);
    }

    @Test
    void updateEntity_shouldIgnoreNullFields() {
        mapper.updateEntity(new EnrollmentDTO.EnrollmentUpdateRequest(null), entity);

        assertThat(entity.getStatus()).isEqualTo(EnrollmentStatus.ACTIVE); // no cambió
    }
}