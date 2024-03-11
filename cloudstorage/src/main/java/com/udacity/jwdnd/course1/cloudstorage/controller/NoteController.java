package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.entity.Note;
import com.udacity.jwdnd.course1.cloudstorage.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class NoteController {

    @Autowired
    NoteService noteService;

    @PostMapping("/notes")
    public String addOrEditNote(@AuthenticationPrincipal User userDetail, Note note, Model model) {
        String mess = "";
        if (note.getNoteid() > 0) {
            noteService.updateNote(note);
            mess += "Updated Successfully";
        } else {
            noteService.insertNote(note, userDetail.getUserid());
            mess += "Inserted Successfully";
        }
        model.addAttribute("mess", mess);
        return "/success";

    }

    @GetMapping("/note/delete/{id}")
    public String deleteNote(@PathVariable("id") int id, Model model) {
        noteService.deleteNote(id);
        model.addAttribute("mess","Deleted Successfully");
        return "/success";

    }
}
