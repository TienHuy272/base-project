package hnt.com.base.repositories;

import hnt.com.base.dto.ProductCategoryDto;
import hnt.com.base.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT new hnt.com.base.dto.ProductCategoryDto(p.name, c.name, p.price) " +
            "FROM Product p JOIN Category c ON p.categoryId = c.id " +
            "WHERE c.name = :categoryName")
    List<ProductCategoryDto> findProductsByCategoryName(@Param("categoryName") String categoryName);
}
