package ro.msg.learning.shop.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ro.msg.learning.shop.entity.Revenue;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface RevenueRepo extends JpaRepository<Revenue, Integer> {
    List<Revenue> findAllByDate(LocalDate givenDate);

    Optional<Revenue> findById(Integer revenueId);
}