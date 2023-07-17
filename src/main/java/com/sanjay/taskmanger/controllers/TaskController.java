package com.sanjay.taskmanger.controllers;

import com.sanjay.taskmanger.dto.errors.ErrorResponseDTO;
import com.sanjay.taskmanger.dto.task.CreateTaskDTO;
import com.sanjay.taskmanger.dto.task.TaskResponseDTO;
import com.sanjay.taskmanger.dto.task.UpdateTaskDTO;
import com.sanjay.taskmanger.entities.Note;
import com.sanjay.taskmanger.entities.Task;
import com.sanjay.taskmanger.services.NoteService;
import com.sanjay.taskmanger.services.TaskService;
import jakarta.validation.constraints.Positive;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/task")
@Validated
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private NoteService noteService;

    private ModelMapper modelMapper = new ModelMapper();

    @GetMapping("")
    public ResponseEntity<List<Task>> getTasks() {
        List<Task> tasks = taskService.getTasks();

        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> getTaskById(@PathVariable("id") @Positive Integer id) {
        Task task = taskService.getTaskById(id);
        List<Note> notes = noteService.getNotesForTask(id);

        if (task == null) {
            return ResponseEntity.notFound().build();
        }

        TaskResponseDTO taskResponseDTO = modelMapper.map(task, TaskResponseDTO.class);

        taskResponseDTO.setNotes(notes);


        return ResponseEntity.ok(taskResponseDTO);
    }

    @PostMapping("")
    public ResponseEntity<Task> addTask(@RequestBody CreateTaskDTO taskDTO) throws ParseException {

        String title = taskDTO.getTitle();
        String description = taskDTO.getDescription();
        String deadline = taskDTO.getDeadline();

        Task task = taskService.addTask(title, description, deadline);

        return ResponseEntity.ok(task);

    }

    @PatchMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable("id") int id, @RequestBody UpdateTaskDTO taskDTO) throws ParseException {

        Task task = taskService.updateTaskById(id, taskDTO.getDescription(), taskDTO.getDeadline(), taskDTO.getCompleted());

        if (task == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(task);
    }

    @ExceptionHandler(ParseException.class)
    public ResponseEntity<ErrorResponseDTO> handleError(Exception error) {
        if (error instanceof ParseException) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO("Invalid Date format"));
        }
        error.printStackTrace();
        return ResponseEntity.internalServerError().body(new ErrorResponseDTO());
    }

}
