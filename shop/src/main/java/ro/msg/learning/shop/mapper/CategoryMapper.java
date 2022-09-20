package ro.msg.learning.shop.mapper;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import ro.msg.learning.shop.dto.CategoryDTO;
import ro.msg.learning.shop.entity.ProductCategory;

@Component
@NoArgsConstructor
public class CategoryMapper {
    public CategoryDTO categoryToCategoryDTO(ProductCategory productCategory) {
        return CategoryDTO.builder()
                .name(productCategory.getName())
                .description(productCategory.getDescription())
                .build();
    }

    public ProductCategory categoryDTOtoProductCategory(CategoryDTO categoryDTO) {
        return ProductCategory.builder()
                .name(categoryDTO.getName())
                .description(categoryDTO.getDescription())
                .build();
    }
}
