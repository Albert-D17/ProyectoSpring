package edu.unimagdalena.lms.Repositories;

import edu.unimagdalena.lms.entitles.Enums.EnrollmentStatus;
import edu.unimagdalena.lms.entitles.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface InstructorRepository extends JpaRepository<Instructor, UUID> {
    //Total de estudiantes a los que da Clases
    @Query("SELECT i, COUNT(DISTINCT e.student) FROM Instructor i " +
            "JOIN i.courses c " +
            "JOIN c.enrollments e " +
            "WHERE e.status = :status " +
            "GROUP BY i")
    List<Object[]> findInstructorStudentCount(@Param("status") EnrollmentStatus status);
}
