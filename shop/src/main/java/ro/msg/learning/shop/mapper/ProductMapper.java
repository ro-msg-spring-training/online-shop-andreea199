package ro.msg.learning.shop.mapper;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ro.msg.learning.shop.dto.ProductDTO;
import ro.msg.learning.shop.entity.Product;

@Component
@RequiredArgsConstructor
public class ProductMapper {
    private final CategoryMapper categoryMapper;
    public ProductDTO productToProductDTO(Product product){
        return ProductDTO.builder()
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .imageUrl(product.getImageUrl())
                .productCategory(categoryMapper.categoryToCategoryDTO(product.getProductCategory()))
                .weight(product.getWeight())
                .build();
    }

}