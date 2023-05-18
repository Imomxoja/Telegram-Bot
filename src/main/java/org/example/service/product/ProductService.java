package org.example.service.product;

import org.example.domain.DTO.BaseResponse;
import org.example.domain.model.Product;
import org.example.service.BaseService;

import java.sql.SQLException;
import java.util.ArrayList;


public interface ProductService extends BaseService {
    BaseResponse save(Product product);

    ArrayList<Product> getProducts() throws SQLException;
    Product getByName(String name);


}
