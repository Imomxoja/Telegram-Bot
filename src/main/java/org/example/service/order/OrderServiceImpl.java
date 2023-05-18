package org.example.service.order;

import org.example.domain.DTO.BaseResponse;
import org.example.domain.model.Basket;
import org.example.domain.model.Order;
import org.example.domain.model.User;
import org.example.repository.order.OrderRepository;
import org.example.repository.order.OrderRepositoryImpl;
import org.example.service.basket.BasketService;
import org.example.service.basket.BasketServiceImpl;
import org.example.service.user.UserService;
import org.example.service.user.UserServiceImpl;

import java.util.ArrayList;
import java.util.UUID;

public class OrderServiceImpl implements OrderService {
    private final UserService userService = new UserServiceImpl();
    private final BasketService basketService = new BasketServiceImpl();
    private final OrderRepository orderRepository = new OrderRepositoryImpl();

    @Override
    public BaseResponse save(Object o) {
        return null;
    }

    public int save(Order order) {
        int save = orderRepository.save(order);
        if (save == 1){
            return 1;
        }
        return 0;
    }

    @Override
    public BaseResponse getById(UUID id) {
        return null;
    }


    @Override
    public BaseResponse orderProduct(User user, Basket basket) {
        if (user.getBalance() < basket.getTotalPrice()) {
            return new BaseResponse("Not enough money \uD83D\uDCB8", 401);
        }

        user.setBalance(user.getBalance() - basket.getTotalPrice());
        userService.setUserBalance(user.getId(), basket.getTotalPrice());
        basketService.remove(basket.getId());

        Order order = new Order()
                .setProductId(basket.getProductId())
                .setUserId(basket.getUserId())
                .setAmount(basket.getAmount())
                .setTotalPrice(basket.getTotalPrice())
                .setProductName(basket.getProductName());
        int save = save(order);

        if (save == 1){
            return new BaseResponse<>("Succesfully was purchased", 777);
        }
        return new BaseResponse<>("fail", 402);
    }

    @Override
    public ArrayList<Order> getUserOrders(UUID id) {
        return orderRepository.getUserOrders(id);
    }
}
