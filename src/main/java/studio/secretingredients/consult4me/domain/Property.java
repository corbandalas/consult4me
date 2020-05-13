package studio.secretingredients.consult4me.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Property entity
 *
 * @author corbandalas - created 12.05.2020
 * @since 0.1.0
 */
@Entity(name = "property")
public class Property {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Integer id;

	private String key;

    private String value;

    private String description;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Property() {
	}

	public Property(String key, String value, String description) {
		this.key = key;
		this.value = value;
		this.description = description;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@Override
	public String toString() {
		return "Property{" +
				"id=" + id +
				", key='" + key + '\'' +
				", value='" + value + '\'' +
				", description='" + description + '\'' +
				'}';
	}
}

