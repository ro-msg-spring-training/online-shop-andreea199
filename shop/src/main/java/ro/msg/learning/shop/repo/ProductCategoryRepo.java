package ro.msg.learning.shop.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ro.msg.learning.shop.entity.ProductCategory;

import java.util.Optional;

@Repository
public interface ProductCategoryRepo extends JpaRepository<ProductCategory, Integer> {
    Optional<ProductCategory> findByName(String name);
}