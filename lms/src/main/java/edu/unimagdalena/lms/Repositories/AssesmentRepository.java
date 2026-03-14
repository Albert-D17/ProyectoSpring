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

}
