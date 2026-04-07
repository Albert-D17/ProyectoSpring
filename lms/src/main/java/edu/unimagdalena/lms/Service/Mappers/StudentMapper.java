package edu.unimagdalena.lms.Service.Mappers;

import edu.unimagdalena.lms.DTO.StudentDTO;
import edu.unimagdalena.lms.entities.Student;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper()
public interface StudentMapper {

    StudentDTO.StudentResponse toResponse(Student entity);

    Student toEntity(StudentDTO.StudentCreateRequest dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(StudentDTO.StudentUpdateRequest dto, @MappingTarget Student entity);
}