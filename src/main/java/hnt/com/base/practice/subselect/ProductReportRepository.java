package hnt.com.base.practice.subselect;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductReportRepository extends JpaRepository<ProductReport, Long> {
}
