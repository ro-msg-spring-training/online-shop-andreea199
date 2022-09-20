package ro.msg.learning.shop.strategy;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.msg.learning.shop.dto.OrderDetailDTO;
import ro.msg.learning.shop.dto.StockDTO;
import ro.msg.learning.shop.entity.Location;
import ro.msg.learning.shop.entity.Stock;
import ro.msg.learning.shop.exception.ProductsCantBeShipped;
import ro.msg.learning.shop.mapper.StockMapper;
import ro.msg.learning.shop.repo.LocationRepo;
import ro.msg.learning.shop.repo.StockRepo;
import ro.msg.learning.shop.service.LocationService;
import ro.msg.learning.shop.service.StockService;
import ro.msg.learning.shop.utils.ID;
import ro.msg.learning.shop.utils.LocationFormat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Component
@RequiredArgsConstructor
public class SingleLocationStrategy implements StrategyInterface {
    private final StockRepo stockRepository;
    private final StockService stockService;
    private final LocationService locationService;

    @Override
    public List<StockDTO> implementStrategy(List<OrderDetailDTO> orderDetailDTOList, LocationFormat deliveryAddress) {
        Map<Integer, Integer> productIDsAndQuantities = getOrderedProductsIDsAndQuantitiesMap(orderDetailDTOList);
        List<Integer> locationsIDsWithAvailableStocks = locationService.findLocationWithProductAndQuantity(productIDsAndQuantities);
        if (!locationsIDsWithAvailableStocks.isEmpty()) {
            List<Stock> stocks = stockService.findStocksByLocationAndProductIDs(createLocationAndProductIDsList(locationsIDsWithAvailableStocks.get(0), getOrderedProductsIDsList(orderDetailDTOList)));
            return getAndUpdateAvailableStocks(stocks, orderDetailDTOList, stockRepository, stockService);
        }
        throw new ProductsCantBeShipped("Demanded products can't be taken from single location!");
    }

    public List<ID> createLocationAndProductIDsList(Integer locationID, List<Integer> orderedProductsIDs) {
        List<ID> result = new ArrayList<>();
        for (Integer productID : orderedProductsIDs) {
            result.add(new ID(locationID, productID));
        }
        return result;
    }
}
