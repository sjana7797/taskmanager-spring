package com.sanjay.taskmanger.dto.task;

import com.sanjay.taskmanger.entities.Note;
import com.sanjay.taskmanger.entities.Task;
import lombok.Data;

import java.util.List;


@Data
public class TaskResponseDTO extends Task {
    private List<Note> notes;
}
