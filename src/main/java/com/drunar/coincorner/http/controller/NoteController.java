package com.drunar.coincorner.http.controller;

import com.drunar.coincorner.dto.NoteDTO;
import com.drunar.coincorner.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/notes")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @GetMapping("/my")
    @PreAuthorize("#userId == authentication.principal.id")
    public String findAllByUser(Model model, @ModelAttribute NoteDTO note, @RequestParam Long userId) {
        return noteService.findAllByUser(note)
                .map(notes -> {
                    model.addAttribute("notes", notes);
                    return "notes/notes";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }


    @PostMapping("/create/{userId}")
    public String processCreateForm(@ModelAttribute("note") NoteDTO note, @PathVariable Long userId) {
        noteService.create(note);
        return "redirect:/notes/my?userId=" + userId;
    }

    @GetMapping("/{userId}/update/{id}")
    public String showUpdateForm(@PathVariable("id") Long id,@PathVariable("userId") Long userId, Model model){
        return noteService.findById(id)
                .map(entity -> {
                    model.addAttribute("note", entity);
                    return "notes/updateNote";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    }

    @PostMapping("/{userId}/update/{id}")
    public String processUpdateForm(@PathVariable("id") Long id, String text, @PathVariable Long userId){
        noteService.update(id,text);
        return "redirect:/notes/" + userId + "/update/" + id;
    }


    @RequestMapping(value = "/{userId}/delete/{id}",
            method = {RequestMethod.DELETE, RequestMethod.GET})
    public String delete(@PathVariable("id") Long id, @PathVariable Long userId) {
        if (!noteService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/notes/my?userId=" + userId;
    }


}
