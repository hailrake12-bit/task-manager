package taskmanagment.createlist.Entity;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Table("user_lists")
public class UserList {

    @Id
    private Integer id;

    private LocalDateTime createdAt = LocalDateTime.now();

    @NotBlank(message = "Name can not be empty")
    private String name;

    private String description;

    @Transient
    private List<UserListItem> items;
}

