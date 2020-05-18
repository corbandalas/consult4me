package studio.secretingredients.consult4me.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * User entity
 *
 * @author corbandalas - created 18.05.2020
 * @since 0.1.0
 */
@Data
@Entity(name = "admin_user")
public class User {

	@Id
	private String email;

	@Column
	private String hashedPassword;

	@Column
	private String name;

	@Column
	private boolean active;

	@Column
	private Date registerDate;

	@ElementCollection(targetClass = AdminRole.class)
	@CollectionTable(name = "user_roles",
			joinColumns = @JoinColumn(name = "user_id"))
	@Column(name = "role")
	@Enumerated(EnumType.STRING)
	private Set<AdminRole> roles = new HashSet<>();
}

