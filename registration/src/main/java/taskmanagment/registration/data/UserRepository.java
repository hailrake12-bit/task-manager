package taskmanagment.registration.data;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import taskmanagment.registration.security.login.User;

import java.util.Optional;

public interface UserRepository extends ReactiveCrudRepository<User, Integer> {

    Optional<User> findByUsername(String username);

}
