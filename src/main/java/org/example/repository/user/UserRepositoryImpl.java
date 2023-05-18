package org.example.repository.user;

import org.example.domain.model.User;
import org.example.domain.model.UserState;
import org.example.util.BeanUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserRepositoryImpl implements UserRepository{
    private final Connection connection = BeanUtil.getConnection();

    private static final UserRepositoryImpl instance = new UserRepositoryImpl();

    public static UserRepositoryImpl getInstance() {
        return instance;
    }

    private UserRepositoryImpl() {
    }

    @Override
    public int save(User user) {
        try {
            PreparedStatement insertStatement = connection.prepareStatement(INSERT);
            insertStatement.setString(1, user.getFullName());
            insertStatement.setString(2, user.getPhoneNumber());
            insertStatement.setString(3, user.getChatId());
            insertStatement.setString(4, user.getState().toString());

            insertStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 200;
    }

    @Override
    public User getById(UUID id) {
        return null;
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public void remove(User user) {

    }

    @Override
    public void remove(UUID id) {

    }

    @Override
    public Optional<User> findUserByChatId(String chatId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_CHAT_ID);
            preparedStatement.setString(1, chatId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                return Optional.of(User.map(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public void updateUserState(UserState state, String chatId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_STATE);
            preparedStatement.setString(1, state.toString());
            preparedStatement.setString(2, chatId);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User getByChatId(String chatId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_CHAT_ID);
            preparedStatement.setString(1, chatId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                return User.map(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void updateUserBalance(UUID id, Double totalPrice) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_BALANCE);
            preparedStatement.setDouble(1, totalPrice);
            preparedStatement.setString(2, id.toString());

            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
