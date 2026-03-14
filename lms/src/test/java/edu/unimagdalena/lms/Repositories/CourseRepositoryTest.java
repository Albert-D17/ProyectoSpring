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

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.test.context.TestPropertySource;



@DataJpaTest
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;MODE=PostgreSQL;DEFAULT_NULL_ORDERING=HIGH",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
class CourseRepositoryTest {

    @Autowired CourseRepository repoCourse;
    @Autowired InstructorRepository repoInstructor;
    @Autowired StudentRepository repoStudent;
    @Autowired EnrollmentRepository repoEnrollment;

    private Instructor instructor;
    private Course course;
    private Student student;

    @BeforeEach
    void setUp() {
        instructor = repoInstructor.save(Instructor.builder()
                .email("profesorweb@unimag.edu")
                .fullName("Julian Pizarro")
                .build());

        course = repoCourse.save(Course.builder()
                .title("Web Programming")
                .status(CourseStatus.ACTIVE)
                .instructor(instructor)
                .build());

        student = repoStudent.save(Student.builder()
                .email("estudianteweb@unimag.edu")
                .fullName("Pancracio")
                .build());
    }

    @Test
    void findByTitle() {
        Optional<Course> result = repoCourse.findByTitle("Web Programming");

        assertThat(result).isPresent();
        assertThat(result.get().getTitle()).isEqualTo("Web Programming");
    }

    @Test
    void findByStatus() {
        repoCourse.save(Course.builder()
                .title("Base de Datos")
                .status(CourseStatus.INACTIVE)
                .instructor(instructor)
                .build());

        List<Course> result = repoCourse.findByStatus(CourseStatus.ACTIVE);

        assertThat(result).isNotEmpty();
        assertThat(result).allMatch(c -> c.getStatus() == CourseStatus.ACTIVE);
    }

    @Test
    void findByInstructorId() {
        repoCourse.save(Course.builder()
                .title("Base de Datos")
                .status(CourseStatus.ACTIVE)
                .instructor(instructor)
                .build());

        List<Course> result = repoCourse.findByInstructorId(instructor.getId());

        assertThat(result).hasSize(2);
        assertThat(result).allMatch(c -> c.getInstructor().getId().equals(instructor.getId()));
    }

    @Test
    void findByStatusAndInstructorId() {
        List<Course> result = repoCourse.findByStatusAndInstructorId(
                CourseStatus.ACTIVE,
                instructor.getId()
        );

        assertThat(result).isNotEmpty();
        assertThat(result.get(0).getStatus()).isEqualTo(CourseStatus.ACTIVE);
        assertThat(result.get(0).getInstructor().getId()).isEqualTo(instructor.getId());
    }

    @Test
    void findStudentsByCourseId() {
        repoEnrollment.save(Enrollment.builder()
                .course(course)
                .student(student)
                .status(EnrollmentStatus.ACTIVE)
                .build());

        List<Student> result = repoCourse.findStudentByCourseId(course.getId());

        assertThat(result).isNotEmpty();
        assertThat(result.get(0).getEmail()).isEqualTo("estudianteweb@unimag.edu");
    }
}