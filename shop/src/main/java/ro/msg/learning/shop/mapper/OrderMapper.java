package ro.msg.learning.shop.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ro.msg.learning.shop.dto.OrderDTO;
import ro.msg.learning.shop.entity.Order;
import ro.msg.learning.shop.repo.CustomerRepo;
import ro.msg.learning.shop.repo.OrderDetailRepo;

@Component
@RequiredArgsConstructor
public class OrderMapper {
    private final OrderDetailMapper orderDetailMapper;

    public OrderDTO orderToOrderDto(Order order) {
        return OrderDTO.builder()
                .orderID(order.getId())
                .userID(order.getCustomer().getId())
                .address(order.getAddress())
                .orderedProducts(orderDetailMapper.orderDetailListToOrderDetailDTOList(order.getOrderDetails()))
                .build();
    }
}
