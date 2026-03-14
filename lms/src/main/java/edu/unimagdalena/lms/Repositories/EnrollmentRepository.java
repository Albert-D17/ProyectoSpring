package edu.unimagdalena.lms.Repositories;

import edu.unimagdalena.lms.entitles.Course;
import edu.unimagdalena.lms.entitles.Enrollment;
import edu.unimagdalena.lms.entitles.Enums.EnrollmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface EnrollmentRepository extends JpaRepository<Enrollment, UUID> {
    // Cursos Matriculados  de un estudiante
    List<Enrollment> findByStudentId(UUID studentId);

    // Inscripciones por estado (Cuantos Reprobaron)
    List<Enrollment> findByStatusAndCourseId(EnrollmentStatus status, UUID CourseId);

    // Verificar si ya existe inscripción
    boolean existsByStudentIdAndCourseId(UUID studentId, UUID courseId);

    // Estudiantes Matriculados  de un curso
    List<Enrollment> findByCourseId(UUID courseId);
}
