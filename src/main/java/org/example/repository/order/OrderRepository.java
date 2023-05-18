package org.example.repository.order;

import org.example.domain.model.Order;
import org.example.repository.BaseRepository;

import java.util.ArrayList;
import java.util.UUID;

public interface OrderRepository extends BaseRepository<Order> {
    String INSERT = """
            insert into orders(user_id,product_id,amount,total_price, product_name)
            values(?::uuid, ?::uuid, ?, ?, ?)""";

    String GET_USER_ORDERS = """
            select * from orders where user_id = ?::uuid;""";

    ArrayList<Order> getUserOrders(UUID id);
}
