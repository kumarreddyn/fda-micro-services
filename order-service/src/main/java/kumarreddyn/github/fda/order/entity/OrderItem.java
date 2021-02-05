package kumarreddyn.github.fda.order.entity;

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
@Table(name = "order_items")
@Getter
@Setter
public class OrderItem extends AbstractCommonEntity{

	@Id	
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_item_sequence")
	@SequenceGenerator(sequenceName = "order_item_sequence", allocationSize = 1, name = "order_item_sequence")
	@Column(name = "order_item_id")
	private Long orderItemId;
	
	@Column(name = "food_item_id")
	private Long foodItemId;
	
	@Column(name = "item_price")
	private Double itemPrice;
	
	@Column(name = "quantity")
	private Integer quantity;
	
	@Column(name = "total_price")
	private Double totalPrice;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name="food_outlet_id")
	@Where(clause = "active = true")
	private Order order;
	
}
