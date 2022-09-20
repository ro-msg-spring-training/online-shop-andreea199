package ro.msg.learning.shop.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ro.msg.learning.shop.entity.Location;

@Repository
public interface LocationRepo extends JpaRepository<Location, Integer> {
}