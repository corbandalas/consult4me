package studio.secretingredients.consult4me.domain;

import lombok.*;

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
@Data
@Entity(name = "property")
public class Property {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Integer id;

	private String key;

    private String value;

    private String description;

}

