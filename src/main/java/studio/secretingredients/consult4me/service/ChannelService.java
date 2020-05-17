package studio.secretingredients.consult4me.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import studio.secretingredients.consult4me.domain.Channel;
import studio.secretingredients.consult4me.repository.ChannelRepository;

import java.util.List;

/**
 * Channel service
 *
 * @author corbandalas - created 17.05.2020
 * @since 0.1.0
 */
@Service("channelService")
public class ChannelService {

    @Autowired
    ChannelRepository channelRepository;

    public Channel save(Channel channel) {
        return channelRepository.save(channel);
    }
}