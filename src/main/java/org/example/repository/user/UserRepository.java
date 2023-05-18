package org.example.repository.user;

import org.example.domain.model.User;
import org.example.domain.model.UserState;
import org.example.repository.BaseRepository;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends BaseRepository<User> {
    String FIND_BY_CHAT_ID = "select * from users where chat_id = ?";

    String INSERT = """
            insert into users(full_name, phone_number, chat_id, user_state) 
            values(?, ?, ?, ?);""";

    String UPDATE_STATE = """
            update users set user_state = ? where chat_id = ?;""";

    Optional<User> findUserByChatId(String chatId);

    void updateUserState(UserState state, String chatId);


    User getByChatId(String chatId);
    String GET_BY_CHAT_ID = """
            select * from users where chat_id = ?;""";
    String UPDATE_USER_BALANCE = """
            update users set balance = balance - ? where id = ?::uuid;""";

    void updateUserBalance(UUID id, Double totalPrice);
}
