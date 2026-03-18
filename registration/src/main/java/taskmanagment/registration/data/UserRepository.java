package taskmanagment.registration.data;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;
import taskmanagment.registration.web.entity.User;

import java.util.Optional;

public interface UserRepository extends ReactiveCrudRepository<User, Integer> {

    Mono<User> findByUsername(String username);

}
