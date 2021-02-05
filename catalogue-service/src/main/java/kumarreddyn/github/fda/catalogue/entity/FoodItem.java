package kumarreddyn.github.fda.catalogue.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "food_items")
@Getter
@Setter
public class FoodItem extends AbstractCommonEntity{

	@Id	
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "food_item_sequence")
	@SequenceGenerator(sequenceName = "food_item_sequence", allocationSize = 1, name = "food_item_sequence")
	@Column(name = "food_item_id")
	private Long foodItemId;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "price")
	private Double price;
	
	@Column(name = "photo_url")
	private String photoURL;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name="food_outlet_id")
	@Where(clause = "active = true")
	private FoodOutlet foodOutlet;
	
}
