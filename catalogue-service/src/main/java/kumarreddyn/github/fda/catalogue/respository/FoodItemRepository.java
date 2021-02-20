package kumarreddyn.github.fda.catalogue.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kumarreddyn.github.fda.catalogue.entity.FoodItem;

@Repository
public interface FoodItemRepository extends JpaRepository<FoodItem, Long>{

}
