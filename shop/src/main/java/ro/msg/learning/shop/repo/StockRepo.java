package ro.msg.learning.shop.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import ro.msg.learning.shop.entity.Stock;

import java.util.List;
import java.util.Optional;

@Component
public interface StockRepo extends JpaRepository<Stock, Integer> {
    List<Stock> findAllByProductId(Integer productID);
}