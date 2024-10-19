package hnt.com.base.dto;

import org.springframework.beans.factory.annotation.Value;

public interface ProductCategorySummary {
    String getProductName();
    Float getPrice();
    String getCategoryName();

    @Value("#{target.productName} #{target.price} #{target.categoryName}")
    String getInfo();
}
