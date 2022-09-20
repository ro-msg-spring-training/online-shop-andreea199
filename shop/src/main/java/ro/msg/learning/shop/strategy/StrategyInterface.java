package ro.msg.learning.shop.strategy;

import org.springframework.stereotype.Component;
import ro.msg.learning.shop.dto.OrderDetailDTO;
import ro.msg.learning.shop.dto.StockDTO;
import ro.msg.learning.shop.entity.Stock;
import ro.msg.learning.shop.exception.ProductsCantBeShipped;
import ro.msg.learning.shop.repo.StockRepo;
import ro.msg.learning.shop.service.StockService;
import ro.msg.learning.shop.utils.LocationFormat;

import java.util.*;

@Component
public interface StrategyInterface {
    public List<StockDTO> implementStrategy(List<OrderDetailDTO> orderDetailDTOList, LocationFormat deliveryAddress);

    default Map<Integer, Integer> getOrderedProductsIDsAndQuantitiesMap(List<OrderDetailDTO> orderDetailDTOList) {
        Map<Integer, Integer> orderedProductIdsAndQuantities = new HashMap<>();
        for (OrderDetailDTO orderDetailDTO : orderDetailDTOList) {
            orderedProductIdsAndQuantities.put(orderDetailDTO.getProductId(), orderDetailDTO.getQuantity());
        }
        return orderedProductIdsAndQuantities;
    }

    default List<Integer> getOrderedProductsIDsList(List<OrderDetailDTO> orderDetailDTOList) {
        List<Integer> orderedProductIds = new ArrayList<>();
        for (OrderDetailDTO orderDetailDTO : orderDetailDTOList) {
            orderedProductIds.add(orderDetailDTO.getProductId());
        }
        return orderedProductIds;
    }

    default List<StockDTO> getAndUpdateAvailableStocks(List<Stock> stocks, List<OrderDetailDTO> orderDetailDTOList, StockRepo stockRepositoryOptional, StockService stockServiceOptional) {
        List<StockDTO> resultStockList = new ArrayList<>();
        for (Stock crtStock : stocks) {
            for (OrderDetailDTO orderProduct : orderDetailDTOList) {
                if (orderProduct.getProductId().equals(crtStock.getProduct().getId())) {
                    resultStockList.add(StockDTO.builder()
                            .id(crtStock.getId())
                            .productId(orderProduct.getProductId())
                            .locationId(crtStock.getLocation().getId())
                            .quantity(orderProduct.getQuantity())
                            .build());
                    if (resultStockList.size() == orderDetailDTOList.size()) {
                        updateStocks(resultStockList, stockRepositoryOptional, stockServiceOptional);
                        return resultStockList;
                    }
                }
            }
        }
        throw new ProductsCantBeShipped("Demanded products can't be taken from single location!");
    }

    default void updateStocks(List<StockDTO> resultStocks, StockRepo stockRepository, StockService stockService) {
        for (StockDTO stockDTO : resultStocks) {
            Optional<Stock> toUpdate = stockRepository.findById(stockDTO.getId());
            toUpdate.ifPresent(stock -> stockService.updateStock(stock, stockDTO.getQuantity()));
        }
    }
}