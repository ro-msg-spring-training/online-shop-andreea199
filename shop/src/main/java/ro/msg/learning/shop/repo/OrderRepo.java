package ro.msg.learning.shop.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import ro.msg.learning.shop.entity.Order;

import java.time.LocalDateTime;
import java.util.List;

@Component
public interface OrderRepo extends JpaRepository<Order, Integer> {
}
