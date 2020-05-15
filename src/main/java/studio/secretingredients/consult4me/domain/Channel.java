package studio.secretingredients.consult4me.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * Social channel entity
 *
 * @author corbandalas - created 15.05.2020
 * @since 0.1.0
 */
@Data
@Entity(name = "property")
public class Channel {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Integer id;

	@Enumerated(EnumType.STRING)
	private ChannelCategory channelCategory;

	@Column
	private String account;

}
