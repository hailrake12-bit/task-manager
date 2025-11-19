package taskmanagment.createtask.web;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import taskmanagment.createtask.Entity.Task;
import taskmanagment.createtask.data.TaskRepository;


@Controller
@RequestMapping("/task")
public class TaskCreateController {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskCreateController(
            TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }

    @GetMapping
    public String showCreateForm(Model model){
        model.addAttribute("task", new Task());
        return "createTask";
    }

    @PostMapping
    public String createTask(@ModelAttribute @Valid Task task, Errors errors){

        if(errors.hasErrors()) return "createTask";

        taskRepository.save(task);
        return "redirect:/task";
    }
}
