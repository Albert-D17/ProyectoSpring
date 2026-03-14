package edu.unimagdalena.lms.Repositories;

import edu.unimagdalena.lms.entitles.Course;
import edu.unimagdalena.lms.entitles.Enums.EnrollmentStatus;
import edu.unimagdalena.lms.entitles.Student;
import org.hibernate.sql.model.jdbc.OptionalTableUpdateOperation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StudentRepository extends JpaRepository<Student, UUID> {

    Optional<Student> findByEmail(String email);
    Optional<Student> findByfullName (String fullName);

    //Los cursos que tiene un estudiante
    @Query("SELECT e.course FROM Enrollment e WHERE e.student.id = :studentId")
    List<Course> findCoursesByStudentId(@Param("studentId") UUID studentId);

    //Estudiantes Matriculados en un intervalo
    @Query("SELECT s FROM Student s WHERE s.createdAt BETWEEN :start AND :end")
    List<Student> findByDateRange(@Param("start") Instant start, @Param("end") Instant end);


    // Estudiantes inscritos en un curso con estado específico
    @Query("SELECT e.student FROM Enrollment e WHERE e.course.id = :courseId AND e.status = :status")
    List<Student> findByCourseIdAndEnrollmentStatus(@Param("courseId") UUID courseId,
                                                    @Param("status") EnrollmentStatus status);

    // Verificar si estudiante ya está inscrito en un curso
    @Query("SELECT COUNT(e) > 0 FROM Enrollment e WHERE e.student.id = :studentId AND e.course.id = :courseId")
    boolean existsEnrollmentByStudentAndCourse(@Param("studentId") UUID studentId,
                                               @Param("courseId") UUID courseId);
}
