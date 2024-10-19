package hnt.com.base.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductCategoryDto {
    private String productName;
    private String categoryName;
    private Float productPrice;
}
