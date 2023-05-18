package org.example.repository.basket;

import org.example.domain.model.Basket;
import org.example.repository.BaseRepository;

import java.util.ArrayList;
import java.util.UUID;

public interface BasketRepository extends BaseRepository<Basket> {
    String INSERT = """
            insert into basket(user_id, product_id, amount, total_price, product_name)
            values(?::uuid, ?::uuid, ?, ?, ?);""";

    ArrayList<Basket> getUserBaskets(UUID id);
    String GET_USER_BASKET_BY_ID = """
            select * from basket where user_id = ?::uuid;""";
    String REMOVE = """
            delete from basket where id = ?::uuid;""";
}
