package ro.msg.learning.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.dto.OrderDTO;
import ro.msg.learning.shop.dto.OrderDetailDTO;
import ro.msg.learning.shop.dto.ProductDTO;
import ro.msg.learning.shop.dto.StockDTO;
import ro.msg.learning.shop.entity.*;
import ro.msg.learning.shop.exception.ProductNotFoundException;
import ro.msg.learning.shop.exception.ProductsCantBeShipped;
import ro.msg.learning.shop.mapper.OrderDetailMapper;
import ro.msg.learning.shop.mapper.OrderMapper;
import ro.msg.learning.shop.mapper.ProductMapper;
import ro.msg.learning.shop.repo.*;
import ro.msg.learning.shop.config.ConfigureStrategy;
import ro.msg.learning.shop.utils.LocationFormat;

import java.time.LocalDateTime;
import java.util.*;


@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepo orderRepository;
    private final OrderMapper orderMapper;
    private final OrderDetailRepo orderDetailsRepository;
    private final OrderDetailMapper orderDetailMapper;
    private final ProductRepo productRepository;
    private final ProductMapper productMapper;
    private final CustomerRepo customerRepository;
    private final ConfigureStrategy strategyConfiguration;
    private final LocationRepo locationRepository;


    public OrderDTO createOrder(OrderDTO orderDTO) {
        OrderDTO newOrder;
        LocalDateTime localDateTime = LocalDateTime.now();
        List<OrderDetail> result = registerOrderedProducts(orderDTO.getOrderedProducts(), false);
        LocationFormat deliveryLocation = new LocationFormat(null, orderDTO.getAddress().getCity(), orderDTO.getAddress().getCountry());
        List<StockDTO> strategyResult = strategyConfiguration.decideStrategy().implementStrategy(orderDetailMapper.orderDetailListToOrderDetailDTOList(result), deliveryLocation);
        Set<Location> locationsForShippingProducts = getShippingLocations(strategyResult);
        if (!strategyResult.isEmpty()) {
            Optional<Customer> client = customerRepository.findById(orderDTO.getUserID());
            if (client.isPresent()) {
                Order createNewOrder = Order.builder()
                        .customer(client.get())
                        .address(orderDTO.getAddress())
                        .orderDetails(registerOrderedProducts(orderDTO.getOrderedProducts(), true))
                        .shippedFrom(locationsForShippingProducts)
                        .createdAt(localDateTime)
                        .build();

                List<OrderDetail> crtOrderDetails = createNewOrder.getOrderDetails();
                for (OrderDetail orderDetail : crtOrderDetails) {
                    orderDetail.setOrder(createNewOrder);
                }
                orderRepository.save(createNewOrder);
                newOrder = orderMapper.orderToOrderDto(createNewOrder);
                return newOrder;
            } else {
                throw new RuntimeException("Client is not registered in the system!");
            }
        } else {
            throw new ProductsCantBeShipped("No location to take products from");
        }
    }

    public Set<Location> getShippingLocations(List<StockDTO> stocks) {
        Set<Location> resultLocationsForShipping = new HashSet<>();
        for (StockDTO crtStock : stocks) {
            Optional<Location> crtStockLocation = locationRepository.findById(crtStock.getLocationId());
            crtStockLocation.ifPresent(resultLocationsForShipping::add);
        }
        return resultLocationsForShipping;

    }

    public List<OrderDetail> registerOrderedProducts(List<OrderDetailDTO> productDetails, boolean saveData) {
        List<OrderDetail> orderDetails = new ArrayList<>();
        OrderDetail crtOrderDetail;
        Optional<ProductDTO> productCheck;
        for (OrderDetailDTO productInfo : productDetails) {
            Optional<Product> orderedProduct = productRepository.findById(productInfo.getProductId());
            if (orderedProduct.isPresent()) {
                productCheck = Optional.ofNullable(productMapper.productToProductDTO(orderedProduct.get()));
                if (productCheck.isPresent()) {
                    if (saveData) {
                        crtOrderDetail = orderDetailMapper.orderDetailDTOToOrderDetail(productInfo);
                        crtOrderDetail.setProduct(orderedProduct.get());
                        orderDetailsRepository.save(crtOrderDetail);
                        orderDetails.add(crtOrderDetail);
                    } else {
                        crtOrderDetail = orderDetailMapper.orderDetailDTOToOrderDetail(productInfo);
                        crtOrderDetail.setProduct(orderedProduct.get());
                        orderDetails.add(crtOrderDetail);
                    }
                }
            } else {
                throw new ProductNotFoundException("Product not found!");
            }
        }
        return orderDetails;
    }
}