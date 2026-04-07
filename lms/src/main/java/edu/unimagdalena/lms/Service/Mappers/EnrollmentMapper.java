package edu.unimagdalena.lms.Service.Mappers;

import edu.unimagdalena.lms.DTO.EnrollmentDTO;
import edu.unimagdalena.lms.entitles.Enrollment;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
 public interface EnrollmentMapper {

    @Mapping(source = "student.id", target = "studentId")
    @Mapping(source = "course.id", target = "courseId")
    EnrollmentDTO.EnrollmentResponse toResponse(Enrollment entity);

    @Mapping(source = "studentId", target = "student.id")
    @Mapping(source = "courseId", target = "course.id")
    Enrollment toEntity(EnrollmentDTO.EnrollmentCreateRequest dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(EnrollmentDTO.EnrollmentUpdateRequest dto, @MappingTarget Enrollment entity);
}