package ro.msg.learning.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.dto.CategoryDTO;
import ro.msg.learning.shop.dto.ProductDTO;
import ro.msg.learning.shop.entity.Product;
import ro.msg.learning.shop.entity.ProductCategory;
import ro.msg.learning.shop.mapper.ProductMapper;
import ro.msg.learning.shop.repo.ProductCategoryRepo;
import ro.msg.learning.shop.repo.ProductRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepo productRepo;
    private final ProductMapper productMapper;
    private final ProductCategoryRepo productCategoryRepo;

    public ProductDTO createProduct(ProductDTO productDto){
        Product product= Product.builder()
                .name(productDto.getName())
                .description(productDto.getDescription())
                .price(productDto.getPrice())
                .weight(productDto.getWeight())
                .imageUrl(productDto.getImageUrl())
                .productCategory(checkCategoryExistence(productDto.getProductCategory()))
                .build();
        productRepo.save(product);
        return productMapper.productToProductDTO(product);
    }
    public ProductCategory checkCategoryExistence(CategoryDTO category){
        Optional<ProductCategory> searchedCategory = productCategoryRepo.findByName(category.getName());
        ProductCategory crtProductCategory=null;
        if(searchedCategory.isPresent()){
            crtProductCategory=searchedCategory.get();
        }else{
            crtProductCategory = new ProductCategory();
            crtProductCategory.setName(category.getName());
            crtProductCategory.setDescription(category.getDescription());
            productCategoryRepo.save(crtProductCategory);
        }
        return  crtProductCategory;

    }

    public ProductDTO updateProduct(Integer id, ProductDTO updatedProduct){
        ProductDTO resultedProduct=null;
        Optional<Product> productToUpdate= productRepo.findById(id);
        if(productToUpdate.isPresent()){
            Product updated = productRepo.findById(id).get();
            updated.setName(updatedProduct.getName());
            updated.setPrice(updatedProduct.getPrice());
            updated.setDescription(updatedProduct.getDescription());
            updated.setWeight(updatedProduct.getWeight());
            updated.setImageUrl(updatedProduct.getImageUrl());
            updated.setProductCategory(checkCategoryExistence(updatedProduct.getProductCategory()));
            productRepo.save(updated);
            resultedProduct=productMapper.productToProductDTO(updated);
        }
        return  resultedProduct;
    }
    public void deleteProductByID(Integer id) {
        productRepo.deleteById(id);
    }

    public ProductDTO getProductByID(Integer id) {
        Optional<Product>searchedProduct= productRepo.findById(id);
        if(searchedProduct.isPresent()){
            return productMapper.productToProductDTO(searchedProduct.get());
        }else{
            throw new RuntimeException("Product not found!");
        }
    }

    public List<ProductDTO> getAllProducts(){
        List<ProductDTO> existingProducts= new ArrayList<>();
        try{
            List<Product> products = productRepo.findAll();
            if(products.isEmpty()){
                throw new RuntimeException("No products were found");
            }else{
                for(Product p: products){
                    existingProducts.add(productMapper.productToProductDTO(p));
                }
            }
        }catch (RuntimeException ex){
            System.out.println(ex.getMessage());
        }
        return existingProducts;

    }
}
