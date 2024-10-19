package hnt.com.base.repositories;

import hnt.com.base.dto.ProductCategoryDto;
import hnt.com.base.dto.ProductCategorySummary;
import hnt.com.base.dto.ProductSummary;
import hnt.com.base.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT new hnt.com.base.dto.ProductCategoryDto(p.name, c.name, p.price) " +
            "FROM Product p JOIN Category c ON p.categoryId = c.id " +
            "WHERE c.name = :categoryName")
    List<ProductCategoryDto> findProductsByCategoryName(@Param("categoryName") String categoryName);

    @Query("SELECT p.name AS productName, c.name AS categoryName, p.price AS price " +
            "FROM Product p JOIN Category c ON p.categoryId = c.id " +
            "WHERE c.name = :categoryName")
    List<ProductCategorySummary> findProductsByCategoryNameSummary(@Param("categoryName") String categoryName);

    Optional<ProductSummary> findFirstById(Long id);
}
