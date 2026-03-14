package edu.unimagdalena.lms.Repositories;

import edu.unimagdalena.lms.entitles.Course;
import edu.unimagdalena.lms.entitles.Enrollment;
import edu.unimagdalena.lms.entitles.Enums.CourseStatus;
import edu.unimagdalena.lms.entitles.Enums.EnrollmentStatus;
import edu.unimagdalena.lms.entitles.Instructor;
import edu.unimagdalena.lms.entitles.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;



@DataJpaTest
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;MODE=PostgreSQL;DEFAULT_NULL_ORDERING=HIGH",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
class EnrollmentRepositoryTest {

    @Autowired
    EnrollmentRepository repoEnrollment;
    @Autowired StudentRepository repoStudent;
    @Autowired CourseRepository repoCourse;
    @Autowired InstructorRepository repoInstructor;

    private Student student;
    private Course course;
    private Instructor instructor;

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

        repoEnrollment.save(Enrollment.builder()
                .student(student)
                .course(course)
                .status(EnrollmentStatus.ACTIVE)
                .enrolledAt(Instant.now())
                .build());
    }

    @Test
    void findByStudentId() {
        List<Enrollment> result = repoEnrollment.findByStudentId(student.getId());

        assertThat(result).isNotEmpty();
        assertThat(result).allMatch(e -> e.getStudent().getId().equals(student.getId()));
    }

    @Test
    void findByStatusAndCourseId() {
        repoEnrollment.save(Enrollment.builder()
                .student(repoStudent.save(Student.builder()
                        .email("estudiantereprobado@unimag.edu")
                        .fullName("Pedrito")
                        .build()))
                .course(course)
                .status(EnrollmentStatus.FAILED)
                .enrolledAt(Instant.now())
                .build());

        List<Enrollment> result = repoEnrollment.findByStatusAndCourseId(
                EnrollmentStatus.FAILED,
                course.getId()
        );

        assertThat(result).isNotEmpty();
        assertThat(result).allMatch(e -> e.getStatus() == EnrollmentStatus.FAILED);
        assertThat(result).allMatch(e -> e.getCourse().getId().equals(course.getId()));
    }

    @Test
    void existsByStudentIdAndCourseId_WhenExists() {
        boolean exists = repoEnrollment.existsByStudentIdAndCourseId(
                student.getId(),
                course.getId()
        );

        assertThat(exists).isTrue();
    }

    @Test
    void existsByStudentIdAndCourseId_WhenNotExists() {
        boolean exists = repoEnrollment.existsByStudentIdAndCourseId(
                UUID.randomUUID(),
                UUID.randomUUID()
        );

        assertThat(exists).isFalse();
    }

    @Test
    void findByCourseId() {
        List<Enrollment> result = repoEnrollment.findByCourseId(course.getId());

        assertThat(result).isNotEmpty();
        assertThat(result).allMatch(e -> e.getCourse().getId().equals(course.getId()));
    }
}