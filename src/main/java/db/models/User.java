package db.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class User {
    private long id;
    private String firstname;
    private String lastname;
    private String email;
    private String passwordHash;
    private String role;
    private Boolean active;
}
