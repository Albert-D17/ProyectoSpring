package edu.unimagdalena.lms.Repositories;

import edu.unimagdalena.lms.entitles.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LessonRepository extends JpaRepository<Lesson, UUID> {
    List<Lesson> findByCourseIdOrderByOrderIndexAsc(UUID courseId);

    // Contar lecciones de un curso
    int countByCourseId(UUID courseId);

    // Buscar lección por título en un curso
    Optional<Lesson> findByCourseIdAndTitle(UUID courseId, String title);

    // Buscar lecciones por fecha
    @Query("SELECT l FROM Lesson l WHERE l.course.id = :courseId AND l.lessonDate BETWEEN :start AND :end")
    List<Lesson> findByCourseIdInDateRange(@Param("courseId") UUID courseId,
                                           @Param("start") Instant start,
                                           @Param("end") Instant end);
}
