package edu.unimagdalena.lms.Repositories;

import edu.unimagdalena.lms.entitles.Assessment;
import edu.unimagdalena.lms.entitles.Enums.AssessmentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AssesmentRepository extends JpaRepository<Assessment, UUID> {

    Optional<Assessment> findByType(AssessmentType assesment);

    @Query("SELECT a FROM Assessment a WHERE a.student.id = :studentId AND a.takenAt BETWEEN :start AND :end")
    List<Assessment> findByStudentIdInDateRange(@Param("studentId") UUID studentId,
                                                @Param("start") Instant start,
                                                @Param("end") Instant end);

    // Evaluaciones de un estudiante
    List<Assessment> findByStudentId(UUID studentId);

    // Evaluaciones de un curso
    List<Assessment> findByCourseId(UUID courseId);

    // Evaluaciones por tipo en un curso
    @Query("SELECT a FROM Assessment a WHERE a.course.id = :courseId AND a.type = :type")
    List<Assessment> findByCourseIdAndType(@Param("courseId") UUID courseId,
                                           @Param("type") AssessmentType type);

    // Promedio de score de un estudiante en un curso
    @Query("SELECT AVG(a.score) FROM Assessment a WHERE a.student.id = :studentId AND a.course.id = :courseId")
    Double findAverageScoreByStudentAndCourse(@Param("studentId") UUID studentId,
                                              @Param("courseId") UUID courseId);
}
