package taskmanagment.createlist.Entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("user_list_items")
public class UserListItem {

    @Id
    private Integer id;

    private Integer userListId;

    private String content;
}
