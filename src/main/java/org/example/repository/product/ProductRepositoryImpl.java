package org.example.repository.product;

import org.example.domain.model.Product;
import org.example.util.BeanUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProductRepositoryImpl implements ProductRepository{
    private final Connection connection = BeanUtil.getConnection();
    @Override
    public int save(Product product) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setString(3, product.getType().toString());
            preparedStatement.setInt(4, product.getAmount());

            preparedStatement.execute();
            return 1;
        } catch (SQLException e) {
            return -1;
        }
    }

    @Override
    public Product getById(UUID id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_ID);
            preparedStatement.setString(1, "id");
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return Product.map(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<Product> getAll() throws SQLException {
        List<Product> products = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            Product product = Product.map(resultSet);
            products.add(product);
        }

        return products;
    }

    @Override
    public Product update(Product product) {
        return null;
    }

    @Override
    public void remove(Product product) {

    }

    @Override
    public void remove(UUID id) {

    }

    @Override
    public Product getByName(String name) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_NAME);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return Product.map(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
