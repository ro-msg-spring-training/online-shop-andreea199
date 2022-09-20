package ro.msg.learning.shop.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ro.msg.learning.shop.dto.StockDTO;
import ro.msg.learning.shop.entity.Location;
import ro.msg.learning.shop.entity.Product;
import ro.msg.learning.shop.entity.Stock;
import ro.msg.learning.shop.exception.MappingException;
import ro.msg.learning.shop.repo.LocationRepo;
import ro.msg.learning.shop.repo.ProductRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class StockMapper {
    private final LocationRepo locationRepository;
    private final ProductRepo productRepository;

    public StockDTO stockToStockDTO(Stock stock) {
        return StockDTO.builder()
                .id(stock.getId())
                .quantity(stock.getQuantity())
                .locationId(stock.getLocation().getId())
                .productId(stock.getProduct().getId())
                .build();
    }

    public Stock stockDTOToStock(StockDTO stockDTO) {
        Optional<Location> stockLocation = locationRepository.findById(stockDTO.getLocationId());
        Optional<Product> stockProduct = productRepository.findById(stockDTO.getProductId());
        if (stockLocation.isPresent() && stockProduct.isPresent()) {
            return Stock.builder()
                    .quantity(stockDTO.getQuantity())
                    .location(stockLocation.get())
                    .product(stockProduct.get())
                    .build();

        } else {
            throw new MappingException("Couldn't map the data!");
        }
    }

    public List<StockDTO> stockListToStockListDTO(List<Stock> stocks) {
        List<StockDTO> resultedStocksDTO = new ArrayList<>();
        for (Stock crtStock : stocks) {
            resultedStocksDTO.add(stockToStockDTO(crtStock));
        }
        return resultedStocksDTO;
    }
}