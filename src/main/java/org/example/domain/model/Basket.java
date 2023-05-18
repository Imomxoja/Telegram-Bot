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
public class Basket extends BaseModel{
    private UUID productId;
    private UUID userId;
    private String productName;
    private Integer amount;
    private Double totalPrice;

    public static Basket map(ResultSet resultSet) throws SQLException {
        Basket basket = new Basket();
        basket.setId(UUID.fromString(resultSet.getString("id")));
        basket.setUserId(UUID.fromString(resultSet.getString("user_id")));
        basket.setProductName(resultSet.getString("product_name"));
        basket.setProductId(UUID.fromString(resultSet.getString("product_id")));
        basket.setCreatedDate(resultSet.getTimestamp("created_date").toLocalDateTime());
        basket.setUpdatedDate(resultSet.getTimestamp("updated_date").toLocalDateTime());
        basket.setAmount(resultSet.getInt("amount"));
        basket.setTotalPrice(resultSet.getDouble("total_price"));
        return basket;
    }
}


