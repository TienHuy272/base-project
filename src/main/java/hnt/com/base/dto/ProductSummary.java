package hnt.com.base.dto;

import org.springframework.beans.factory.annotation.Value;

public interface ProductSummary {
    String getName();
    Float getPrice();
    @Value("#{target.name} #{target.price}")
    String getInfo();
}
