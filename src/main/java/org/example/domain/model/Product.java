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
public class Product extends BaseModel{
    private String name;
    private double price;
    private ProductType type;
    private Integer amount;

    public static Product map(ResultSet resultSet) throws SQLException {
        Product product = new Product();
        product.setId(UUID.fromString(resultSet.getString("id")));
        product.setCreatedDate(resultSet.getTimestamp("created_date").toLocalDateTime());
        product.setUpdatedDate(resultSet.getTimestamp("updated_date").toLocalDateTime());
        product.setName(resultSet.getString("name"));
        product.setType(ProductType.valueOf(resultSet.getString("type")));
        product.setAmount(resultSet.getInt("amount"));
        product.setPrice(resultSet.getDouble("price"));
        return product;
    }
}
