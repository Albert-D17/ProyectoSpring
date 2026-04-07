package edu.unimagdalena.lms.Service.Mappers;

import edu.unimagdalena.lms.DTO.AssessmentDTO;
import edu.unimagdalena.lms.DTO.AssessmentDTO.*;
import edu.unimagdalena.lms.entities.Assessment;
import edu.unimagdalena.lms.entities.Course;
import edu.unimagdalena.lms.entities.Student;
import edu.unimagdalena.lms.entities.Enums.AssessmentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class AssessmentMapperTest {

    private final AssessmentMapper mapper = Mappers.getMapper(AssessmentMapper.class);

    private UUID studentId;
    private UUID courseId;
    private UUID id;
    private Student student;
    private Course course;
    private Assessment entity;

    @BeforeEach
    void setUp() {
        studentId = UUID.randomUUID();
        courseId = UUID.randomUUID();
        id = UUID.randomUUID();
        student = Student.builder().id(studentId).build();
        course = Course.builder().id(courseId).build();
        entity = Assessment.builder()
                .id(id)
                .type(AssessmentType.EXPOSITION)
                .score(85)
                .takenAt(Instant.now())
                .student(student)
                .course(course)
                .build();
    }

    @Test
    void toEntity_shouldMapCreateRequest() {
        Assessment result = mapper.toEntity(new AssessmentCreateRequest(
                AssessmentType.QUIZ, 90, Instant.now(), studentId, courseId));

        assertThat(result.getType()).isEqualTo(AssessmentType.QUIZ);
        assertThat(result.getScore()).isEqualTo(90);
        assertThat(result.getStudent().getId()).isEqualTo(studentId);
        assertThat(result.getCourse().getId()).isEqualTo(courseId);
    }

    @Test
    void toResponse_shouldMapEntity() {
        AssessmentDTO.AssesmentResponse response = mapper.toResponse(entity);

        assertThat(response.id()).isEqualTo(id);
        assertThat(response.type()).isEqualTo(AssessmentType.EXPOSITION);
        assertThat(response.score()).isEqualTo(85);
        assertThat(response.studentId()).isEqualTo(studentId);
        assertThat(response.courseId()).isEqualTo(courseId);
    }

    @Test
    void updateEntity_shouldIgnoreNullFields() {
        mapper.updateEntity(new AssessmentDTO.AssesmentUpdateRequest(AssessmentType.QUIZ, 95, null, null, null), entity);

        assertThat(entity.getType()).isEqualTo(AssessmentType.QUIZ);
        assertThat(entity.getScore()).isEqualTo(95);
    }
}