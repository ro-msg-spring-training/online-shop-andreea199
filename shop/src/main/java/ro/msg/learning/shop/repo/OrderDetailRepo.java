package ro.msg.learning.shop.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ro.msg.learning.shop.entity.OrderDetail;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderDetailRepo extends JpaRepository<OrderDetail, Integer> {

    Optional<OrderDetail> findByOrderId(Integer orderId);

    Optional<OrderDetail> findByProductId(Integer productID);
}