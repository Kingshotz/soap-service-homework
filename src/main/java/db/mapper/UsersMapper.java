package db.mapper;

import db.models.User;
import org.apache.ibatis.annotations.Select;

public interface UsersMapper {
    @Select("SELECT * FROM users WHERE email = #{email}")
    User getUserByEmail(String email);
}
