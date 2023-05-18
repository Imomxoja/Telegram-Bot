package org.example.service.order;

import org.example.domain.DTO.BaseResponse;
import org.example.domain.model.Basket;
import org.example.domain.model.Order;
import org.example.domain.model.User;
import org.example.service.BaseService;
import java.util.ArrayList;
import java.util.UUID;

public interface OrderService extends BaseService {
    BaseResponse orderProduct(User user, Basket basket);

    ArrayList<Order> getUserOrders(UUID id);
}
