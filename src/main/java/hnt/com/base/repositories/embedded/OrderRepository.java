package hnt.com.base.repositories.embedded;

import hnt.com.base.entities.embedded.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
