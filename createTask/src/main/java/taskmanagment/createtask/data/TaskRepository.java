package taskmanagment.createtask.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import taskmanagment.createtask.Entity.Task;

public interface TaskRepository extends ReactiveCrudRepository<Task, Integer> {
}
