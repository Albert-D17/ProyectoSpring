package edu.unimagdalena.lms.Repositories;

import edu.unimagdalena.lms.entitles.*;
import edu.unimagdalena.lms.entitles.Enums.CourseStatus;
import edu.unimagdalena.lms.entitles.Enums.EnrollmentStatus;
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
class StudentRepositoryTest {

    @Autowired StudentRepository repoStudent;
    @Autowired CourseRepository repoCourse;
    @Autowired InstructorRepository repoInstructor;
    @Autowired EnrollmentRepository repoEnrollment;

    private Student student;
    private Course course;

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

        student = repoStudent.save(Student.builder()
                .email("pepito@unimag.edu")
                .fullName("Pepito Perez")
                .createdAt(Instant.parse("2025-03-15T10:00:00Z"))
                .build());

        repoEnrollment.save(Enrollment.builder()
                .student(student)
                .course(course)
                .status(EnrollmentStatus.ACTIVE)
                .enrolledAt(Instant.now())
                .build());
    }

    @Test
    void findByEmail() {
        Optional<Student> result = repoStudent.findByEmail("pepito@unimag.edu");

        assertThat(result).isPresent();
        assertThat(result.get().getEmail()).isEqualTo("pepito@unimag.edu");
    }

    @Test
    void findByFullName() {
        Optional<Student> result = repoStudent.findByfullName("Pepito Perez");

        assertThat(result).isPresent();
        assertThat(result.get().getFullName()).isEqualTo("Pepito Perez");
    }

    @Test
    void findCoursesByStudentId() {
        List<Course> result = repoStudent.findCoursesByStudentId(student.getId());

        assertThat(result).isNotEmpty();
        assertThat(result).allMatch(c -> c.getId().equals(course.getId()));
    }

    @Test
    void findByDateRange() {
        List<Student> result = repoStudent.findByDateRange(
                Instant.parse("2025-01-01T00:00:00Z"),
                Instant.parse("2025-12-31T23:59:59Z"));

        assertThat(result).isNotEmpty();
        assertThat(result).allMatch(s -> s.getId().equals(student.getId()));
    }

    @Test
    void findByCourseIdAndEnrollmentStatus() {
        List<Student> result = repoStudent.findByCourseIdAndEnrollmentStatus(
                course.getId(),
                EnrollmentStatus.ACTIVE);

        assertThat(result).isNotEmpty();
        assertThat(result).allMatch(s -> s.getId().equals(student.getId()));
    }

    @Test
    void existsEnrollmentByStudentAndCourse() {
        boolean exists = repoStudent.existsEnrollmentByStudentAndCourse(
                student.getId(),
                course.getId());

        assertThat(exists).isTrue();
    }
}