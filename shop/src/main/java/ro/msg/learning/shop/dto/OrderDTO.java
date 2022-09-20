package ro.msg.learning.shop.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.msg.learning.shop.utils.Address;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDTO {
    private Integer orderID;
    private Integer userID;
    private List<OrderDetailDTO> orderedProducts;
    private Address address;
}