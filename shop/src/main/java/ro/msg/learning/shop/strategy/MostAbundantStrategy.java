package ro.msg.learning.shop.strategy;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.msg.learning.shop.dto.OrderDetailDTO;
import ro.msg.learning.shop.dto.StockDTO;
import ro.msg.learning.shop.entity.Stock;
import ro.msg.learning.shop.exception.ProductsCantBeShipped;
import ro.msg.learning.shop.mapper.StockMapper;
import ro.msg.learning.shop.repo.StockRepo;
import ro.msg.learning.shop.service.StockService;
import ro.msg.learning.shop.utils.LocationFormat;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MostAbundantStrategy implements StrategyInterface{
    private final StockRepo stockRepository;
    private final StockService stockService;

    @Override
    public List<StockDTO> implementStrategy(List<OrderDetailDTO> orderDetailDTOList, LocationFormat deliveryAddress) {

        List<StockDTO> resultedStocks = new ArrayList<>();

        for (OrderDetailDTO demandedProduct : orderDetailDTOList) {
            List<Stock> stocksContainingDemandedProduct = stockRepository.findAllByProductId(demandedProduct.getProductId());

            Stock mostAbundant = stocksContainingDemandedProduct.stream().max(Comparator.comparing(Stock::getQuantity)).get();
            if (demandedProduct.getQuantity() <= mostAbundant.getQuantity()) {
                StockDTO targetStock = StockDTO.builder()
                        .quantity(demandedProduct.getQuantity())
                        .locationId(mostAbundant.getLocation().getId())
                        .productId(demandedProduct.getProductId())
                        .build();
                resultedStocks.add(targetStock);
                stockService.updateStock(mostAbundant, demandedProduct.getQuantity());
                if (resultedStocks.size() == orderDetailDTOList.size()) {
                    return resultedStocks;
                }
            }
        }
        throw new ProductsCantBeShipped("Products not available at the moment!");
    }
}