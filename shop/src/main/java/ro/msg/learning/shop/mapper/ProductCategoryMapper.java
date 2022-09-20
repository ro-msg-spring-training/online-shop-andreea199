package ro.msg.learning.shop.mapper;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import ro.msg.learning.shop.dto.ProductCategoryDTO;
import ro.msg.learning.shop.entity.ProductCategory;

@Component
@NoArgsConstructor
public class ProductCategoryMapper {
    public ProductCategoryDTO categoryToCategoryDTO(ProductCategory productCategory){
        return ProductCategoryDTO.builder()
                .name(productCategory.getName())
                .description(productCategory.getDescription())
                .build();
    }
    public ProductCategory categoryDTOtoCategory(ProductCategoryDTO productCategoryDTO){
        return ProductCategory.builder()
                .name(productCategoryDTO.getName())
                .description(productCategoryDTO.getDescription())
                .build();
    }
}