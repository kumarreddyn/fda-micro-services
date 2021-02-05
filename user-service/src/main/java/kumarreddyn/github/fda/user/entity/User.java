package kumarreddyn.github.fda.user.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User extends AbstractCommonEntity{

	@Id	
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
	@SequenceGenerator(sequenceName = "user_sequence", allocationSize = 1, name = "user_sequence")
	@Column(name = "user_id")
	private Long userId;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "email_address", unique = true)
	private String emailAddress;
	
	@Column(name = "country_code")
	private String countryCode;
	
	@Column(name = "mobile_number", unique = true)
	private String mobileNumber;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "photo_url")
	private String photoURL;
	
	@ManyToMany(targetEntity = Role.class, fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.DETACH,
			CascadeType.MERGE, CascadeType.REFRESH })
	@JoinTable(name = "user_roles", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = {
			@JoinColumn(name = "role_id") })
	@Where(clause = "active = true")
	private Set<Role> roles;
	
	
	@OneToMany(targetEntity = Address.class,  fetch = FetchType.LAZY, mappedBy = "user",
			cascade = { CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
	@Where(clause = "active = true")
	private Set<Address> addresses;
	
}
