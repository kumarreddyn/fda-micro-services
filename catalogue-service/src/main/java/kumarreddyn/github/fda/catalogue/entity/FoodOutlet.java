package kumarreddyn.github.fda.catalogue.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "food_outlets")
@Getter
@Setter
public class FoodOutlet extends AbstractCommonEntity{

	@Id	
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "food_outlet_sequence")
	@SequenceGenerator(sequenceName = "food_outlet_sequence", allocationSize = 1, name = "food_outlet_sequence")
	@Column(name = "food_outlet_id")
	private Long foodOutletId;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "location")
	private String location;
	
	@Column(name = "photo_url")
	private String photoURL;
	
	@OneToMany(targetEntity = FoodItem.class,  fetch = FetchType.LAZY, mappedBy = "foodOutlet",
			cascade = { CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
	@Where(clause = "active = true")
	private Set<FoodItem> foodItems;
}
