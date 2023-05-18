package org.example.service.product;

import org.example.domain.DTO.BaseResponse;
import org.example.domain.model.Product;
import org.example.repository.product.ProductRepository;
import org.example.repository.product.ProductRepositoryImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository = new ProductRepositoryImpl();

    @Override
    public BaseResponse save(Product product) {
        return null;
    }

    @Override
    public ArrayList<Product> getProducts() throws SQLException {
        List<Product> products = productRepository.getAll();
        return (ArrayList<Product>) products;
    }

    @Override
    public Product getByName(String name) {
        return productRepository.getByName(name);
    }


    @Override
    public BaseResponse save(Object o) {
        return null;
    }

    @Override
    public BaseResponse<Product> getById(UUID id) {
        Product product = productRepository.getById(id);
        if (product != null) {
            return new BaseResponse<>("success", 777, product);
        }
        return new BaseResponse<>("fail", 400);
    }
}
