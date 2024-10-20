package hnt.com.base.practice.subselect;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import java.math.BigDecimal;

@Entity
@Immutable
@Subselect(
        value = "select p.id as id, p.name as product_name, c.name as category_name, p.price as price from products p left join categories c on c.id = p.category_id"
)
@Data
public class ProductReport {
    @Id
    private Long id;
    private String productName;
    private String categoryName;
    @Formula(
            "(SELECT AVG(p.price) FROM products p WHERE p.category_id = 1)"
    )
    private BigDecimal averagePrice;

    @ColumnTransformer(
            read = "price * 2",
            write = "? * 2.20462"
    )
    private BigDecimal transformPrice;
}
