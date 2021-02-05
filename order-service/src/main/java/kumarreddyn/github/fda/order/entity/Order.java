package kumarreddyn.github.fda.order.entity;

import java.util.Date;
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
@Table(name = "orders")
@Getter
@Setter
public class Order extends AbstractCommonEntity{

	@Id	
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_sequence")
	@SequenceGenerator(sequenceName = "order_sequence", allocationSize = 1, name = "order_sequence")
	@Column(name = "order_id")
	private Long orderId;
	
	@Column(name = "order_number")
	private String orderNumber;
	
	@Column(name = "food_outlet_id")
	private Long foodOutletId;
	
	@Column(name = "total_price")
	private Double totalPrice;
	
	@Column(name = "ordered_by")
	private Long orderedBy;
	
	@Column(name = "ordered_date")
	private Date orderedDate;
	
	@Column(name = "address_id")
	private Long addressId;
	
	@OneToMany(targetEntity = OrderItem.class,  fetch = FetchType.LAZY, mappedBy = "order",
			cascade = { CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
	@Where(clause = "active = true")
	private Set<OrderItem> orderItems;
}
