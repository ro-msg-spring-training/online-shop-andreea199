package ro.msg.learning.shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ro.msg.learning.shop.dto.ProductDTO;
import ro.msg.learning.shop.service.ProductService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/products")
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ProductDTO createProduct(@RequestBody ProductDTO productDTO){
        return productService.createProduct(productDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Integer id){
        productService.deleteProductByID(id);
    }

    @GetMapping
    public List<ProductDTO> readProducts(){
        return productService.getAllProducts();
    }

    @PostMapping("/{id}")
    public ProductDTO updateProduct(@PathVariable Integer id, @RequestBody ProductDTO productDTO){
        return productService.updateProduct(id,productDTO);
    }

    @GetMapping("/{id}")
    public ProductDTO readProductById(@PathVariable Integer id){
        return productService.getProductByID(id);
    }
}
