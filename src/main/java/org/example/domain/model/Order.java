package org.example.domain.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Order extends BaseModel{
    private UUID productId;
    private UUID userId;
    private Integer amount;
    private Double totalPrice;
    private String productName;

    public static Order map(ResultSet resultSet) throws SQLException {
        Order order = new Order();
        order.setId(UUID.fromString(resultSet.getString("id")));
        order.setUserId(UUID.fromString(resultSet.getString("user_id")));
        order.setProductId(UUID.fromString(resultSet.getString("product_id")));
        order.setCreatedDate(resultSet.getTimestamp("created_date").toLocalDateTime());
        order.setUpdatedDate(resultSet.getTimestamp("updated_date").toLocalDateTime());
        order.setAmount(resultSet.getInt("amount"));
        order.setTotalPrice(resultSet.getDouble("total_price"));
        order.setProductName(resultSet.getString("product_name"));
        return order;
    }
}
