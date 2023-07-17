package com.sanjay.taskmanger.dto.task;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateTaskDTO {
    private String description;
    private String deadline;
    private Boolean completed;
}
