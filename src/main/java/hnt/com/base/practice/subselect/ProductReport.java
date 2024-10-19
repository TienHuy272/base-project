package hnt.com.base.practice.subselect;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

@Entity
@Immutable
@Subselect(
        value = "select p.id as id, p.name as product_name, c.name as category_name from products p left join categories c on c.id = p.category_id"
)
@Data
public class ProductReport {
    @Id
    private Long id;
    private String productName;
    private String categoryName;
}
