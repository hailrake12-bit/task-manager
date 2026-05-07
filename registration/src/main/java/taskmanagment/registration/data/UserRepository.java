package taskmanagment.registration.data;

import org.springframework.data.repository.CrudRepository;
import taskmanagment.registration.data.entity.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {

    Optional<User> findByUsername(String username);

}
