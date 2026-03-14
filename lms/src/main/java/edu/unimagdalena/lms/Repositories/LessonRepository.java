package edu.unimagdalena.lms.Repositories;

import edu.unimagdalena.lms.entitles.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LessonRepository extends JpaRepository<Lesson, UUID> {
}
