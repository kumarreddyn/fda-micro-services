package kumarreddyn.github.fda.order.entity;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public abstract class AbstractCommonEntity {

	@Column(name = "active")
	protected Boolean active;

	@Column(name = "created_by")
	protected Long createdBy;
	
	@Column(name = "created_date")
	protected Date createdDate;
	
	@Column(name = "updated_by")
	protected Long updatedBy;

	@Column(name = "updated_date")
	protected Date updatedDate;

}
