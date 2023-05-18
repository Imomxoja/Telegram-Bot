package org.example.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class User extends BaseModel{
    private String fullName;
    private String phoneNumber;
    private String chatId;
    private UserState state;
    private double balance;

    public static User map(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(UUID.fromString(resultSet.getString("id")));
        user.setFullName(resultSet.getString("full_name"));
        user.setPhoneNumber(resultSet.getString("phone_number"));
        user.setChatId(resultSet.getString("chat_id"));
        user.setState(UserState.valueOf(resultSet.getString("user_state")));
        user.setCreatedDate(resultSet.getTimestamp("created_date").toLocalDateTime());
        user.setUpdatedDate(resultSet.getTimestamp("updated_date").toLocalDateTime());
        user.setBalance(resultSet.getDouble("balance"));


        return user;
    }
}
