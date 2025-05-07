package taskmanagment.registration.data;

import org.springframework.data.repository.CrudRepository;
import taskmanagment.registration.security.login.User;

public interface UserRepository extends CrudRepository<User, Integer> {

    User findByUsername(String username);

}
