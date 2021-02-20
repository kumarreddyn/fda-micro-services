package kumarreddyn.github.fda.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kumarreddyn.github.fda.order.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{

}
