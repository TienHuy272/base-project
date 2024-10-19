package hnt.com.base.controller;

import hnt.com.base.dto.ProductCategoryDto;
import hnt.com.base.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/product-category")
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepository productRepository;

    @GetMapping("/{categoryName}")
    public List<ProductCategoryDto> getUsers(@PathVariable String categoryName) {
        return productRepository.findProductsByCategoryName(categoryName);
    }
}
