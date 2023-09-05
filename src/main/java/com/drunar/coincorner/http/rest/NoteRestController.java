package com.drunar.coincorner.http.rest;

import com.drunar.coincorner.database.filter.NoteFilter;
import com.drunar.coincorner.dto.NoteDTO;
import com.drunar.coincorner.dto.PageResponse;
import com.drunar.coincorner.service.NoteService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/notes")
@Tag(name = "Note Rest Controller", description = "Notes CRUD operations.")
@RequiredArgsConstructor
public class NoteRestController {

    private final NoteService noteService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public PageResponse<NoteDTO> findAllByUser(NoteFilter filter, Pageable pageable) {

        Page<NoteDTO> page = noteService.findAll(filter, pageable);
        return PageResponse.of(page);
    }

    @PostMapping( consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public NoteDTO create(@Validated @RequestBody NoteDTO note) {
        return noteService.create(note);
    }

    @PutMapping(path = "/{id}",consumes = MediaType.APPLICATION_JSON_VALUE )
    public NoteDTO update(@Validated @RequestBody NoteDTO note,
                                    @PathVariable("id") Long id){
        return noteService.update(id,note)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<String> delete(@PathVariable("id") Long id){
        if (!noteService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
         } return ResponseEntity.ok("The note has been successfully deleted");

    }


}
