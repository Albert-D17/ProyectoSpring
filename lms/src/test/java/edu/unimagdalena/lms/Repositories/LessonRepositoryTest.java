package edu.unimagdalena.lms.Repositories;

import edu.unimagdalena.lms.entitles.*;
import edu.unimagdalena.lms.entitles.Enums.CourseStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;MODE=PostgreSQL",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
class LessonRepositoryTest {

    @Autowired LessonRepository repoLesson;
    @Autowired CourseRepository repoCourse;
    @Autowired InstructorRepository repoInstructor;

    private Course course;
    private Lesson lesson1;
    private Lesson lesson2;
    private Lesson lesson3;

    @BeforeEach
    void setUp() {
        Instructor instructor = repoInstructor.save(Instructor.builder()
                .email("prof@unimag.edu")
                .fullName("Teacher Jaime")
                .build());

        course = repoCourse.save(Course.builder()
                .title("Web Programming")
                .status(CourseStatus.ACTIVE)
                .instructor(instructor)
                .build());

        lesson1 = repoLesson.save(Lesson.builder()
                .title("Data Model")
                .orderIndex(1)
                .lessonDate(Instant.parse("2025-03-01T10:00:00Z"))
                .course(course)
                .build());

        lesson2 = repoLesson.save(Lesson.builder()
                .title("Repository Layer")
                .orderIndex(2)
                .lessonDate(Instant.parse("2025-04-01T10:00:00Z"))
                .course(course)
                .build());

        lesson3 = repoLesson.save(Lesson.builder()
                .title("React")
                .orderIndex(3)
                .lessonDate(Instant.parse("2025-05-01T10:00:00Z"))
                .course(course)
                .build());
    }

    @Test
    void findByCourseIdOrderByOrderIndexAsc() {
        List<Lesson> result = repoLesson.findByCourseIdOrderByOrderIndexAsc(course.getId());

        assertThat(result).hasSize(3);
        assertThat(result.get(0).getOrderIndex()).isLessThan(result.get(1).getOrderIndex());
        assertThat(result.get(1).getOrderIndex()).isLessThan(result.get(2).getOrderIndex());
    }

    @Test
    void countByCourseId() {
        int count = repoLesson.countByCourseId(course.getId());

        assertThat(count).isEqualTo(3);
    }

    @Test
    void findByCourseIdAndTitle() {
        Optional<Lesson> result = repoLesson.findByCourseIdAndTitle(course.getId(), "Repository Layer");

        assertThat(result).isPresent();
        assertThat(result.get().getTitle()).isEqualTo("Repository Layer");
        assertThat(result.get().getCourse().getId()).isEqualTo(course.getId());
    }

    @Test
    void findByCourseIdInDateRange() {
        List<Lesson> result = repoLesson.findByCourseIdInDateRange(
                course.getId(),
                Instant.parse("2025-03-01T00:00:00Z"),
                Instant.parse("2025-04-30T23:59:59Z"));

        // Solo lesson1 y lesson2 están en el rango, lesson3 (mayo) queda fuera
        assertThat(result).hasSize(2);
        assertThat(result).allMatch(l -> l.getCourse().getId().equals(course.getId()));
        assertThat(result).allMatch(l ->
                !l.getLessonDate().isBefore(Instant.parse("2025-03-01T00:00:00Z")) &&
                        !l.getLessonDate().isAfter(Instant.parse("2025-04-30T23:59:59Z")));
    }
}