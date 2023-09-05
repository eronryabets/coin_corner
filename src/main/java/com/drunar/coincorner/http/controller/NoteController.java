package com.drunar.coincorner.http.controller;

import com.drunar.coincorner.database.filter.NoteFilter;
import com.drunar.coincorner.dto.NoteDTO;
import com.drunar.coincorner.dto.PageResponse;
import com.drunar.coincorner.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/notes")
@RequiredArgsConstructor
@PreAuthorize("#userId == authentication.principal.id")
public class NoteController {

    private final NoteService noteService;


    @GetMapping("/my")
    public String findAllByUser(Model model, NoteFilter filter, Pageable pageable,
                                @ModelAttribute NoteDTO note, @RequestParam Long userId) {

        Page<NoteDTO> page = noteService.findAll(filter, pageable);
        model.addAttribute("notes", PageResponse.of(page));
        model.addAttribute("filter", filter);
        return "notes/notes";

    }


    @PostMapping("/create/{userId}")
    public String processCreateForm(@Validated NoteDTO note, BindingResult bindingResult,
                                    @PathVariable Long userId, RedirectAttributes redirectAttributes) {

        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("text", note.getText());
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/notes/my?userId=" + userId;
        }

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
    public String processUpdateForm(@ModelAttribute @Validated NoteDTO note, BindingResult bindingResult,
                                    @PathVariable("id") Long id, @PathVariable Long userId,
                                    RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/notes/" + userId + "/update/" + id;
        }
        noteService.update(id,note);
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
