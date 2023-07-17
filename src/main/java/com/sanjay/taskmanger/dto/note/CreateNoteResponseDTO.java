package com.sanjay.taskmanger.dto.note;

import com.sanjay.taskmanger.entities.Note;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateNoteResponseDTO {
    private Integer taskId;
    private Note note;
}
