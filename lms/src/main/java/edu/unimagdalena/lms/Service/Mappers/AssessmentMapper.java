package edu.unimagdalena.lms.Service.Mappers;

import edu.unimagdalena.lms.entities.Assessment;
import org.mapstruct.*;
import edu.unimagdalena.lms.DTO.AssessmentDTO;

@Mapper(componentModel = "spring")
    public interface AssessmentMapper {

        @Mapping(source = "student.id", target = "studentId")
        @Mapping(source = "course.id", target = "courseId")
        AssessmentDTO.AssesmentResponse toResponse(Assessment entity);

        @Mapping(source = "studentId", target = "student.id")
        @Mapping(source = "courseId", target = "course.id")
        Assessment toEntity(AssessmentDTO.AssessmentCreateRequest dto);

        @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
        @Mapping(source = "studentId", target = "student.id")
        @Mapping(source = "courseId", target = "course.id")
        void updateEntity(AssessmentDTO.AssesmentUpdateRequest dto, @MappingTarget Assessment entity);
    }

