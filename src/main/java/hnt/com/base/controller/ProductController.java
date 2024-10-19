package hnt.com.base.controller;

import hnt.com.base.dto.ProductCategoryDto;
import hnt.com.base.dto.ProductCategorySummary;
import hnt.com.base.dto.ProductSummary;
import hnt.com.base.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1/product-category")
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepository productRepository;

    @GetMapping("/{categoryName}")
    public List<ProductCategoryDto> getUsers(@PathVariable String categoryName) {
        List<ProductCategorySummary> productCategorySummaries =
                productRepository.findProductsByCategoryNameSummary(categoryName);
        System.out.println("NAME: " + productCategorySummaries.get(0).getProductName());
        System.out.println("Price: " + productCategorySummaries.get(0).getPrice());
        System.out.println("Category name: " + productCategorySummaries.get(0).getCategoryName());
        System.out.println("INFO : " + productCategorySummaries.get(0).getInfo());

        return productRepository.findProductsByCategoryName(categoryName);
    }

    @GetMapping("/product/{id}")
    public String getUsers(@PathVariable Long id) {
        Optional<ProductSummary> productSummaryOptional = productRepository.findFirstById(id);
        System.out.println("NAME: " + productSummaryOptional.get().getName());
        System.out.println("INFO: " + productSummaryOptional.get().getInfo());
        System.out.println("PRICE: " + productSummaryOptional.get().getPrice());
        return productSummaryOptional.get().getName();
    }
}
