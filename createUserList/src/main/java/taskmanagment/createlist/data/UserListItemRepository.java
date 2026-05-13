package taskmanagment.createlist.data;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import taskmanagment.createlist.Entity.UserListItem;

public interface UserListItemRepository extends ReactiveCrudRepository<UserListItem, Integer> {
    Flux<UserListItem> findByUserListId(Integer listId);
}
