package edu.unimagdalena.lms.Repositories;

import edu.unimagdalena.lms.entitles.InstructorProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InstructorProfileRepository extends JpaRepository<InstructorProfile, UUID> {

}
