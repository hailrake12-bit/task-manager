package taskmanagment.createtask.Entity;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Table
public class Task {

    @Id
    private Integer id;

    private LocalDateTime createdAt = LocalDateTime.now();

    @NotBlank(message = "Name can not be empty")
    private String name;

    private String description;
}

