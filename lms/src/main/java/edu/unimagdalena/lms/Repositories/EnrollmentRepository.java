package edu.unimagdalena.lms.Repositories;

import edu.unimagdalena.lms.entitles.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EnrollmentRepository extends JpaRepository<Enrollment, UUID> {

}
