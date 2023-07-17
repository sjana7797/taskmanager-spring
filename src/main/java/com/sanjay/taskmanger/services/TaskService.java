package com.sanjay.taskmanger.services;

import com.sanjay.taskmanger.entities.Task;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

@Service
public class TaskService {

    private ArrayList<Task> tasks = new ArrayList<>();
    private int taskId = 1;
    SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");

    public Task addTask(String title, String description, String deadline) throws ParseException {

        Task task = new Task();
        task.setId(taskId);
        task.setTitle(title);
        task.setDescription(description);
        task.setDeadline(dateFormatter.parse(deadline));
        task.setCompleted(false);

        tasks.add(task);

        taskId++;

        return task;

    }

    public Task getTaskById(int id) {

        return tasks.stream().findAny().filter(task1 -> task1.getId() == id).orElse(null);
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public Task updateTaskById(int id, String description, String deadline, Boolean completed) throws ParseException {

        Task task = getTaskById(id);
        if (task == null) {
            return null;
        }
        if (completed != null) {
            task.setCompleted(completed);
        }

        if (deadline != null) {
            task.setDeadline(dateFormatter.parse(deadline));
        }

        if (description != null) {
            task.setDescription(description);
        }

        return task;
    }
}
