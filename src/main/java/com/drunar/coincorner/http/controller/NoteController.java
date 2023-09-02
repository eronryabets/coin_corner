package com.drunar.coincorner.http.controller;

import com.drunar.coincorner.database.entity.Note;
import com.drunar.coincorner.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/notes")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @GetMapping("/my")
    public String showCreateForm(Model model, @ModelAttribute Note note){
        model.addAttribute("note", note);
        return "notes/notes";
    }

}
