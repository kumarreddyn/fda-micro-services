package kumarreddyn.github.fda.catalogue.respository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kumarreddyn.github.fda.catalogue.entity.FoodOutlet;

@Repository
public interface FoodOutletRepository extends JpaRepository<FoodOutlet, Long>{

	Set<FoodOutlet> findByActive(boolean active);
	
}
