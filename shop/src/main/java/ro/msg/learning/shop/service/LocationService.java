package ro.msg.learning.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.dto.LocationDTO;
import ro.msg.learning.shop.entity.Location;
import ro.msg.learning.shop.entity.Stock;
import ro.msg.learning.shop.mapper.LocationMapper;
import ro.msg.learning.shop.repo.LocationRepo;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class LocationService {
    private final LocationRepo locationRepository;
    private final LocationMapper locationMapper;
    private final EntityManager entityManager;

    public List<LocationDTO> getAllLocations() {
        List<LocationDTO> allLocationToDTO = new ArrayList<>();
        List<Location> locations = locationRepository.findAll();
        for (Location l : locations) {
            allLocationToDTO.add(locationMapper.locationToLocationDTO(l));
        }
        return allLocationToDTO;
    }

    public List<Integer> findLocationWithProductAndQuantity(Map<Integer, Integer> productWithQuantity) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Integer> query = criteriaBuilder.createQuery(Integer.class);
        Root<Stock> stock = query.from(Stock.class);
        Path<Integer> productID = stock.get("product").get("id");
        Path<Integer> quantity = stock.get("quantity");
        Path<Integer> locationID = stock.get("location").get("id");
        List<Predicate> predicates = new ArrayList<>();

        productWithQuantity.forEach((prodID, prodQuantity) ->
                predicates.add(
                        criteriaBuilder.and(
                                criteriaBuilder.equal(productID, prodID),
                                criteriaBuilder.greaterThanOrEqualTo(quantity, prodQuantity)
                        )
                ));

        query.select(locationID)
                .where(criteriaBuilder.or(predicates.toArray(new Predicate[0])))
                .groupBy(locationID)
                .having(criteriaBuilder.equal(criteriaBuilder.count(productID), productWithQuantity.size()));

        return entityManager.createQuery(query)
                .getResultList();
    }
}