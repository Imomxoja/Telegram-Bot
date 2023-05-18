package org.example.service.basket;

import org.example.domain.DTO.BaseResponse;
import org.example.domain.model.Basket;
import org.example.repository.basket.BasketRepository;
import org.example.repository.basket.BasketRepositoryImpl;

import java.util.ArrayList;
import java.util.UUID;

public class BasketServiceImpl implements BasketService{
    private final BasketRepository basketRepository = new BasketRepositoryImpl();
    @Override
    public BaseResponse save(Object o) {
        return null;
    }

    @Override
    public BaseResponse getById(UUID id) {
        return null;
    }

    @Override
    public int saveBasket(Basket basket) {
        return basketRepository.save(basket);
    }

    @Override
    public ArrayList<Basket> getUserBaskets(UUID id) {
        return basketRepository.getUserBaskets(id);
    }

    @Override
    public void remove(UUID id) {
        basketRepository.remove(id);
    }

}
