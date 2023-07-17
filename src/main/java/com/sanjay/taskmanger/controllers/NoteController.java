package com.sanjay.taskmanger.controllers;


import com.sanjay.taskmanger.dto.errors.ErrorResponseDTO;
import com.sanjay.taskmanger.dto.note.CreateNoteDTO;
import com.sanjay.taskmanger.dto.note.CreateNoteResponseDTO;
import com.sanjay.taskmanger.entities.Note;
import com.sanjay.taskmanger.error.NoElementFoundError;
import com.sanjay.taskmanger.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task/{taskId}/note")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @GetMapping("")
    public ResponseEntity<List<Note>> getNotes(@PathVariable("taskId") int taskId) {
        List<Note> notes = noteService.getNotesForTask(taskId);

        return ResponseEntity.ok(notes);
    }

    @PostMapping("")
    public ResponseEntity<CreateNoteResponseDTO> addNote(@PathVariable("taskId") int taskId, @RequestBody CreateNoteDTO body) {
        Note note = noteService.addNoteForTask(taskId, body.getTitle(), body.getBody());

        CreateNoteResponseDTO noteResponseDTO = new CreateNoteResponseDTO(taskId, note);

        return ResponseEntity.ok(noteResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNote(@PathVariable("id") int noteId, @PathVariable("taskId") int taskId) throws Exception {
        noteService.deleteNote(noteId, taskId);

        return ResponseEntity.ok("Note deleted");
    }

    @ExceptionHandler(NoElementFoundError.class)
    public ResponseEntity<ErrorResponseDTO> handleError(Exception error) {
        if (error instanceof NoElementFoundError) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.internalServerError().body(new ErrorResponseDTO("internal Server Error"));
    }
}
