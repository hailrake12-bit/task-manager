package taskmanagment.createlist.web;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import taskmanagment.createlist.Entity.UserList;
import taskmanagment.createlist.data.UserListItemRepository;
import taskmanagment.createlist.data.UserListRepository;

@RestController
@RequestMapping("/list")
@CrossOrigin(origins = "*")
public class UserListCreateController {

    private final UserListRepository userListRepository;
    private final UserListItemRepository userListItemRepository;

    @Autowired
    public UserListCreateController(UserListRepository userListRepository,
                                    UserListItemRepository userListItemRepository) {
        this.userListRepository = userListRepository;
        this.userListItemRepository = userListItemRepository;
    }

    // Получить все списки
    @GetMapping
    public Flux<UserList> getAllLists() {
        return userListRepository.findAll()
                .flatMap(list ->
                        userListItemRepository.findByUserListId(list.getId())
                                .collectList()
                                .map(items -> {
                                    list.setItems(items);
                                    return list;
                                })
                );
    }

    // Создать новый список
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<UserList> createList(@RequestBody @Valid UserList userList) {
        return userListRepository.save(userList)
                .flatMap(savedList -> {
                    if (userList.getItems() == null || userList.getItems().isEmpty()) {
                        return Mono.just(savedList);
                    }
                    userList.getItems().forEach(item -> item.setUserListId(savedList.getId()));
                    return userListItemRepository.saveAll(userList.getItems())
                            .collectList()
                            .map(savedItems -> {
                                savedList.setItems(savedItems);
                                return savedList;
                            });
                });
    }
}