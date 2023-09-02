package com.drunar.coincorner.mapper;

import com.drunar.coincorner.database.entity.Note;
import com.drunar.coincorner.dto.NoteDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface NoteMapper {

    NoteMapper INSTANCE = Mappers.getMapper(NoteMapper.class);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.username", target = "username")
    NoteDTO noteToNoteDTO(Note note);

    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "username", target = "user.username")
    Note noteDTOToNote(NoteDTO noteDTO);
}
