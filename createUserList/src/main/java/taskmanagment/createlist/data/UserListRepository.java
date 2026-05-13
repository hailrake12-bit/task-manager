package taskmanagment.createlist.data;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import taskmanagment.createlist.Entity.UserList;

public interface UserListRepository extends ReactiveCrudRepository<UserList, Integer> {
}
