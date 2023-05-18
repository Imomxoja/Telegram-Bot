package org.example.repository.product;

import org.example.domain.model.Product;
import org.example.repository.BaseRepository;


public interface ProductRepository extends BaseRepository<Product> {
    String INSERT = """
            insert into products(name, price, type, amount)
            values(?, ?, ?, ?);""";

    String GET_ALL = """
            select * from products;""";
    String GET_BY_ID = """
            select * from products where id = ?;""";

    String GET_BY_PRODUCT_TYPE = """
            select * from products where type = ?;""";

    Product getByName(String name);
    String GET_BY_NAME = """
            select * from products where name = ?;""";


}
