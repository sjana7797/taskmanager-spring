package com.sanjay.taskmanger.dto.note;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateNoteDTO {
    private String title;
    private String body;
}
