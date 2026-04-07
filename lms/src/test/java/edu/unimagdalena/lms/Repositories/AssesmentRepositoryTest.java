package edu.unimagdalena.lms.Repositories;

import edu.unimagdalena.lms.entities.*;
import edu.unimagdalena.lms.entities.Enums.AssessmentType;
import edu.unimagdalena.lms.entities.Enums.CourseStatus;
import edu.unimagdalena.lms.entities.Enums.EnrollmentStatus;
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
        "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;MODE=PostgreSQL;DEFAULT_NULL_ORDERING=HIGH",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
class AssesmentRepositoryTest {

    @Autowired EnrollmentRepository repoEnrollment;
    @Autowired StudentRepository repoStudent;
    @Autowired CourseRepository repoCourse;
    @Autowired InstructorRepository repoInstructor;
    @Autowired AssesmentRepository repoAssessment;

    private Student student;
    private Course course;
    private Instructor instructor;
    private Assessment assessment;
    private Enrollment enrollment;

    @BeforeEach
    void setUp() {
        instructor = repoInstructor.save(Instructor.builder()
                .email("prof@unimag.edu")
                .fullName("Teacher Jaime")
                .build());

        course = repoCourse.save(Course.builder()
                .title("Web Programming")
                .status(CourseStatus.ACTIVE)
                .instructor(instructor)
                .build());

        student = repoStudent.save(Student.builder()
                .email("estudiante@unimag.edu")
                .fullName("Pepito")
                .build());

        enrollment = repoEnrollment.save(Enrollment.builder()
                .student(student)
                .course(course)
                .status(EnrollmentStatus.ACTIVE)
                .enrolledAt(Instant.now())
                .build());

        assessment = repoAssessment.save(Assessment.builder()
                .course(course)
                .student(student)
                .type(AssessmentType.EXPOSITION)
                .score(20)
                .takenAt(Instant.parse("2026-04-01T10:00:00Z"))
                .build());
    }

    @Test
    void findByType() {
        Optional<Assessment> result = repoAssessment.findByType(AssessmentType.EXPOSITION);

        assertThat(result).isNotEmpty();
        assertThat(result).matches(e -> e.get().getType().equals(AssessmentType.EXPOSITION));



    }

    @Test
    void findByStudentIdInDateRange() {
    List<Assessment> result = repoAssessment.findByStudentIdInDateRange(
            student.getId(),
            Instant.parse("2026-02-14T11:00:00Z"),
            Instant.parse("2026-06-01T10:00:00Z"));

    assertThat(result).isNotEmpty();
    assertThat(result).allMatch(a -> a.getStudent().getId().equals(student.getId()));
    }

    @Test
    void findByStudentId() {
    List<Assessment> result = repoAssessment.findByStudentId(student.getId());

    assertThat(result).isNotEmpty();
    assertThat(result).allMatch(e->e.getStudent().getId().equals(student.getId()));
    }

    @Test
    void findByCourseId() {
        List<Assessment> result = repoAssessment.findByCourseId(course.getId());

        assertThat(result).isNotEmpty();
        assertThat(result).allMatch(e->e.getCourse().getId().equals(course.getId()));
    }

    @Test
    void findByCourseIdAndType() {
        List<Assessment> result = repoAssessment.findByCourseIdAndType(course.getId(), AssessmentType.EXPOSITION);

        assertThat(result).isNotEmpty();
        assertThat(result).allMatch(e->e.getCourse().getId().equals(course.getId()));
        assertThat(result).allMatch(e->e.getType().equals(AssessmentType.EXPOSITION));
    }

    @Test
    void findAverageScoreByStudentAndCourse() {
        Double average = repoAssessment.findAverageScoreByStudentAndCourse(
                student.getId(),
                course.getId());

        assertThat(average).isNotNull();
        assertThat(average).isEqualTo(20.0);
    }
}