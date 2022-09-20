package ro.msg.learning.shop.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.web.bind.annotation.*;
import ro.msg.learning.shop.dto.OrderDTO;
import ro.msg.learning.shop.service.OrderService;
import ro.msg.learning.shop.service.StockService;

@RestController
@RequiredArgsConstructor
@RequestMapping(value="/order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public OrderDTO createOrder(@RequestBody OrderDTO orderDTO) {
        return orderService.createOrder(orderDTO);
    }
}
