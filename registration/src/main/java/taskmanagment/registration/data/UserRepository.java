package taskmanagment.registration.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import taskmanagment.registration.web.entity.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {

    User findByUsername(String username);

}
