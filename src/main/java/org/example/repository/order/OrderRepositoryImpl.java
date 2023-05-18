package org.example.repository.order;

import org.example.domain.model.Order;
import org.example.util.BeanUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrderRepositoryImpl implements OrderRepository{
    private final Connection connection = BeanUtil.getConnection();
    @Override
    public int save(Order order) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT);
            preparedStatement.setString(1, order.getUserId().toString());
            preparedStatement.setString(2, order.getProductId().toString());
            preparedStatement.setInt(3, order.getAmount());
            preparedStatement.setDouble(4, order.getTotalPrice());
            preparedStatement.setString(5, order.getProductName());

            preparedStatement.execute();
            return 1;
        } catch (SQLException e) {
            return 0;
        }
    }

    @Override
    public Order getById(UUID id) {
        return null;
    }

    @Override
    public List<Order> getAll() {
        return null;
    }

    @Override
    public Order update(Order order) {
        return null;
    }

    @Override
    public void remove(Order order) {

    }

    @Override
    public void remove(UUID id) {

    }

    @Override
    public ArrayList<Order> getUserOrders(UUID id) {
        ArrayList<Order> orders = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_ORDERS);
            preparedStatement.setString(1, id.toString());

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Order order = Order.map(resultSet);
                orders.add(order);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return orders;
    }
}
