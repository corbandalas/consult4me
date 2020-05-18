package studio.secretingredients.consult4me.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Account entity
 *
 * @author corbandalas - created 16.05.2020
 * @since 0.1.0
 */
@Data
@Entity(name = "account")
public class Account {

	@Id
	private Integer id;

	@Column
	private String contactPersonName;

	@Column
	private String phoneNumber;

	@Column
	private String privateKey;

	@Column
	private boolean active;

	@Column
	private Date registerDate;

	@OneToMany(cascade = {CascadeType.ALL }, fetch = FetchType.LAZY)
	private List<User> users;

}

