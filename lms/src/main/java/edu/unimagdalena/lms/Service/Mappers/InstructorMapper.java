package edu.unimagdalena.lms.Service.Mappers;

import edu.unimagdalena.lms.DTO.InstructorDTO;
import edu.unimagdalena.lms.entities.Instructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.BeanMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

    @Mapper()
    public interface InstructorMapper {

        @Mapping(source = "profile.phone", target = "profile.phone")
        @Mapping(source = "profile.bio", target = "profile.bio")
        InstructorDTO.InstructorResponse toResponse(Instructor entity);

        @Mapping(source = "profile.phone", target = "profile.phone")
        @Mapping(source = "profile.bio", target = "profile.bio")
        Instructor toEntity(InstructorDTO.InstructorCreateRequest dto);

        @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
        void updateEntity(InstructorDTO.InstructorUpdateRequest dto, @MappingTarget Instructor entity);
    }

