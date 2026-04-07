package edu.unimagdalena.lms.Service.Mappers;

import edu.unimagdalena.lms.DTO.LessonDTO;
import edu.unimagdalena.lms.entities.Course;
import edu.unimagdalena.lms.entities.Lesson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class LessonMapperTest {

    private final LessonMapper mapper = Mappers.getMapper(LessonMapper.class);

    private UUID id;
    private Course course;
    private Lesson entity;

    @BeforeEach
    void setUp() {
        id = UUID.randomUUID();
        course = Course.builder().id(UUID.randomUUID()).build();
        entity = Lesson.builder()
                .id(id)
                .title("Intro HTML")
                .orderIndex(1)
                .lessonDate(Instant.now())
                .course(course)
                .build();
    }

    @Test
    void toEntity_shouldMapCreateRequest() {
        Lesson result = mapper.toEntity(new LessonDTO.LessonCreateRequest(
                "Intro HTML", Instant.now(), 1, course.getId()));

        assertThat(result.getTitle()).isEqualTo("Intro HTML");
        assertThat(result.getOrderIndex()).isEqualTo(1);
        assertThat(result.getCourse().getId()).isEqualTo(course.getId());
    }

    @Test
    void toResponse_shouldMapEntity() {
        LessonDTO.LessonResponse response = mapper.toResponse(entity);

        assertThat(response.id()).isEqualTo(id);
        assertThat(response.title()).isEqualTo("Intro HTML");
        assertThat(response.courseId()).isEqualTo(course.getId());
    }

    @Test
    void updateEntity_shouldIgnoreNullFields() {
        mapper.updateEntity(new LessonDTO.LessonUpdateRequest("CSS Básico", null, 1), entity);

        assertThat(entity.getTitle()).isEqualTo("CSS Básico");
        assertThat(entity.getOrderIndex()).isEqualTo(1); // no cambió
    }
}