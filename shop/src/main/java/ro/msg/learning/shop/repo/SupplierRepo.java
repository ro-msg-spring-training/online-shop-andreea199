package ro.msg.learning.shop.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.msg.learning.shop.entity.Supplier;

@Repository
public interface SupplierRepo  extends JpaRepository<Supplier, Integer> {
}
