package studio.secretingredients.consult4me.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import studio.secretingredients.consult4me.domain.Account;
import studio.secretingredients.consult4me.domain.Channel;


/**
 * Channel repository interface
 *
 * @author corbandalas - created 16.05.2020
 * @since 0.1.0
 */

@Repository
public interface ChannelRepository extends CrudRepository<Channel, Integer> {
}
