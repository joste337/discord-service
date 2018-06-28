package jos.service.discord.manager;

import jos.service.discord.controller.DiscordController;
import jos.service.discord.model.CommandServiceResponse;
import jos.service.discord.service.MessageSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import sx.blah.discord.handle.impl.events.guild.channel.message.reaction.ReactionAddEvent;

import java.util.Map;


@Component
public class ReactionAddedManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReactionAddedManager.class);
    @Autowired
    private EmojiHandler emojiHandler;
    private RestTemplate restTemplate;
    @Autowired
    private MessageSender messageSender;

    public ReactionAddedManager(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder.build();
    }

    public void handleAddEvent(ReactionAddEvent event) {
        if (event.getUser().getName().equals("mite-bot")) {
            return;
        }
        Map<Integer, String> optionsMap = emojiHandler.getUserToOptionsMap().get(event.getUser());
        LOGGER.info("Received reaction event");
        if (optionsMap == null) {
            LOGGER.info("OptionsMap is null");
            return;
        }
        Integer index = emojiHandler.getEmojiNameToIndexMap().get(event.getReaction().getEmoji().toString().subSequence(0, 1).toString());
        LOGGER.info("with index {}", index);

        messageSender.sendMessage(restTemplate.getForObject(optionsMap.get(index), CommandServiceResponse.class).getMessage(), event.getChannel());
    }
}
