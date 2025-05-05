package taskmanagment.createtask.data;

import org.springframework.data.repository.CrudRepository;
import taskmanagment.createtask.Entity.Task;

public interface TaskRepository extends CrudRepository<Task, Integer> {
}
