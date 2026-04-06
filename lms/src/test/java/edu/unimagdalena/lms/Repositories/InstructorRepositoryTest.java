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
class InstructorRepositoryTest {

    @Autowired InstructorRepository repoInstructor;
    @Autowired CourseRepository repoCourse;
    @Autowired StudentRepository repoStudent;
    @Autowired EnrollmentRepository repoEnrollment;

    private Instructor instructor;
    private Student student1;
    private Student student2;
    private Course course;

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

        student1 = repoStudent.save(Student.builder()
                .email("estudiante1@unimag.edu")
                .fullName("Pepito")
                .build());

        student2 = repoStudent.save(Student.builder()
                .email("estudiante2@unimag.edu")
                .fullName("Juanito")
                .build());

        // Dos estudiantes ACTIVE
        repoEnrollment.save(Enrollment.builder()
                .student(student1)
                .course(course)
                .status(EnrollmentStatus.ACTIVE)
                .enrolledAt(Instant.now())
                .build());

        repoEnrollment.save(Enrollment.builder()
                .student(student2)
                .course(course)
                .status(EnrollmentStatus.ACTIVE)
                .enrolledAt(Instant.now())
                .build());

        // Un estudiante CANCELLED (no debe contar)
        repoEnrollment.save(Enrollment.builder()
                .student(student1)
                .course(course)
                .status(EnrollmentStatus.CANCELLED)
                .enrolledAt(Instant.now())
                .build());
    }

    @Test
    void findInstructorStudentCount() {
        List<Object[]> result = repoInstructor.findInstructorStudentCount(EnrollmentStatus.ACTIVE);

        assertThat(result).isNotEmpty();

        Object[] row = result.get(0);
        Instructor resultInstructor = (Instructor) row[0];
        Long studentCount = (Long) row[1];

        assertThat(resultInstructor.getId()).isEqualTo(instructor.getId());
        assertThat(studentCount).isEqualTo(2L); // Solo los ACTIVE y distintos
    }
}