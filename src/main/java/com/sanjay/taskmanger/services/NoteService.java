package com.sanjay.taskmanger.services;


import com.sanjay.taskmanger.entities.Note;
import com.sanjay.taskmanger.entities.Task;
import com.sanjay.taskmanger.error.NoElementFoundError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class NoteService {
    @Autowired
    private TaskService taskService;

    private HashMap<Integer, TaskNotesHolder> taskNoteHolder = new HashMap<>();

    public List<Note> getNotesForTask(int taskId) {
        Task task = taskService.getTaskById(taskId);

        if (task == null) {
            return null;
        }
        if (taskNoteHolder.get(taskId) == null) {
            taskNoteHolder.put(taskId, new TaskNotesHolder());
        }

        return taskNoteHolder.get(taskId).notes;
    }

    public Note addNoteForTask(int taskId, String title, String body) {
        Task task = taskService.getTaskById(taskId);

        if (task == null) {
            return null;
        }

        if (taskNoteHolder.get(taskId) == null) {
            taskNoteHolder.put(taskId, new TaskNotesHolder());
        }

        TaskNotesHolder taskNotesHolder = taskNoteHolder.get(taskId);

        Note note = new Note();
        note.setBody(body);
        note.setTitle(title);
        note.setId(taskNotesHolder.noteId);

        taskNotesHolder.notes.add(note);
        taskNotesHolder.noteId++;

        return note;
    }

    public void deleteNote(int noteId, int taskId) throws Exception {

        TaskNotesHolder taskNotesHolder = taskNoteHolder.get(taskId);

        if (taskNotesHolder == null) {
            throw new NoElementFoundError("No Notes found");
        }


        boolean noteDeleted = taskNotesHolder.notes.removeIf(note -> note.getId() == noteId);

        if (!noteDeleted) {
            throw new NoElementFoundError("No Notes found");
        }

    }

    class TaskNotesHolder {
        protected int noteId = 1;
        protected ArrayList<Note> notes = new ArrayList<>();
    }
}
