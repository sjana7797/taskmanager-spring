package com.sanjay.taskmanger.entities;

import lombok.Data;

import java.util.Date;

@Data
public class Task {
    private int id;
    private String title;
    private String description;
    private Date deadline;
    private boolean completed;
}
