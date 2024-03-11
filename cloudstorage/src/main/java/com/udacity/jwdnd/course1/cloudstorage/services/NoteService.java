package com.udacity.jwdnd.course1.cloudstorage.services;


import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.entity.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    @Autowired
    NoteMapper noteMapper;

    public List<Note> findAll(int id){
        return noteMapper.findAllByUserId(id);
    }

    public void insertNote(Note note,int id ){
           noteMapper.insert(note,id);
           //return 1;
    }

    public void deleteNote(int id){
        noteMapper.delete(id);
    }

    public void updateNote(Note note){
        noteMapper.update(note);
    }
}
