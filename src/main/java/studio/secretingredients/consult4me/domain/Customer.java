package studio.secretingredients.consult4me.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Customer entity
 *
 * @author corbandalas - created 15.05.2020
 * @since 0.1.0
 */
@Data
@Entity(name = "customer")
public class Customer {

	@Id
	private String email;

	@Column
	private String hashedPassword;

	@Column
	private String phone;

	@Column
	private String firstName;

	@Column
	private String lastName;

	@Column
	private boolean active;

	@Column
	private boolean agree;

	@Column
	private Date registrationDate;

	@OneToMany(cascade = {CascadeType.ALL }, fetch = FetchType.LAZY)
	private List<Channel> channels;
}

