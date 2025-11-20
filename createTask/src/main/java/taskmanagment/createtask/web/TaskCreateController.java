package taskmanagment.createtask.web;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.result.view.Rendering;
import reactor.core.publisher.Mono;
import taskmanagment.createtask.Entity.Task;
import taskmanagment.createtask.data.TaskRepository;


@Controller
@RequestMapping("/task")
public class TaskCreateController {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskCreateController(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }

    @GetMapping
    public Mono<Rendering> showCreateForm(Model model){
        //model.addAttribute("task", new Task());
        return Mono.just(Rendering.view("createTask")
                .modelAttribute("task", new Task())
                .build());
    }

    @PostMapping
    public Mono<Rendering> createTask(@ModelAttribute @Valid Task task, Errors errors){

        if(errors.hasErrors()) {
            return Mono.just(Rendering.view("createTask")
                    .modelAttribute("task", task)
                    .build());
        }

        return taskRepository.save(task).
                then(Mono.just(Rendering.redirectTo("/task").build()));
    }
}
