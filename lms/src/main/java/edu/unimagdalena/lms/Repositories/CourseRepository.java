package edu.unimagdalena.lms.Repositories;

import edu.unimagdalena.lms.entitles.Course;
import edu.unimagdalena.lms.entitles.Enums.CourseStatus;
import edu.unimagdalena.lms.entitles.Lesson;
import edu.unimagdalena.lms.entitles.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CourseRepository extends JpaRepository<Course, UUID> {



    Optional<Course> findByTitle (String title);

    //Encuentra cursos en un estado específico (se pasa como parámetro)
    @Query("SELECT c FROM Course c WHERE c.status = :status AND c.instructor.id = :instructorId")
    List<Course> findByStatusAndInstructorId(@Param("status") CourseStatus status,
                                             @Param("instructorId") UUID instructorId
    );

    //Encuentra estudiantes por Id curso
    @Query("SELECT e.student FROM Enrollment e WHERE e.course.id = :courseId")
    List<Student> findStudentByCourseId (@Param("courseId") UUID courseId);

    // Cursos por estado
    List<Course> findByStatus(CourseStatus status);

    // Cursos de un instructor
    List<Course> findByInstructorId(UUID instructorId);




}
