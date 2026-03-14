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

    @Query("SELECT c FROM Course c WHERE c.status = :status AND c.instructor.id = :instructorId")
    List<Course> findByStatusAndInstructorId(@Param("status") CourseStatus status,
                                             @Param("instructorId") UUID instructorId
    );


    @Query("SELECT s.student FROM Enrollment e WHERE e.course.id = :courseId")
    List<Student> findStudentByCourseId (@Param("courseId") UUID courseId);


    List<Lesson> findByCourseIdOrderByOrderIndexAsc(UUID courseId);

}
