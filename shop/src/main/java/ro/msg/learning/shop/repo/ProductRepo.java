package ro.msg.learning.shop.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.msg.learning.shop.entity.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {
    void deleteById(Integer id);
}
