package org.example.repository.basket;

import org.example.domain.model.Basket;
import org.example.util.BeanUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BasketRepositoryImpl implements BasketRepository{
    private final Connection connection = BeanUtil.getConnection();
    @Override
    public int save(Basket basket) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT);
            preparedStatement.setString(1, basket.getUserId().toString());
            preparedStatement.setString(2, basket.getProductId().toString());

            preparedStatement.setInt(3, basket.getAmount());
            preparedStatement.setDouble(4, basket.getTotalPrice());
            preparedStatement.setString(5, basket.getProductName());

            preparedStatement.execute();
            return 1;
        } catch (SQLException e) {
            return -1;
        }
    }

    @Override
    public Basket getById(UUID id) {
        return null;
    }

    @Override
    public List<Basket> getAll() throws SQLException {
        return null;
    }

    @Override
    public Basket update(Basket basket) {
        return null;
    }

    @Override
    public void remove(Basket basket) {

    }

    @Override
    public void remove(UUID id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(REMOVE);
            preparedStatement.setString(1, id.toString());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ArrayList<Basket> getUserBaskets(UUID id) {
        ArrayList<Basket> userBaskets = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_BASKET_BY_ID);
            preparedStatement.setString(1, id.toString());

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Basket basket = Basket.map(resultSet);
                userBaskets.add(basket);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userBaskets;
    }
}
