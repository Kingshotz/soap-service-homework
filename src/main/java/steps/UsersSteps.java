package steps;

import db.models.User;

import static config.DataBaseConfig.usersMapper;

public class UsersSteps {
    public User getUserDb(String email) {
        return usersMapper()
                .getUserByEmail(email);
    }
}
