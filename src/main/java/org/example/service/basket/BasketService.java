package org.example.service.basket;

import org.example.domain.model.Basket;
import org.example.service.BaseService;
import java.util.ArrayList;
import java.util.UUID;

public interface BasketService extends BaseService {
    int saveBasket (Basket basket);

    ArrayList<Basket> getUserBaskets(UUID id);


    void remove(UUID id);
}
