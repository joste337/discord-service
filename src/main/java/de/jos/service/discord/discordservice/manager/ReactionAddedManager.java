package de.jos.service.discord.discordservice.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import sx.blah.discord.handle.impl.events.guild.channel.message.reaction.ReactionAddEvent;

import java.util.HashMap;
import java.util.Map;


@Component
public class ReactionAddedManager {
    @Autowired
    private EmojiHandler emojiHandler;
    private RestTemplate restTemplate;

    public ReactionAddedManager(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder.build();
    }

    public void handleAddEvent(ReactionAddEvent event) {
        Map<Integer, String> optionsMap = emojiHandler.getUserToOptionsMap().get(event.getAuthor());
        if (optionsMap == null) {
            return;
        }

        Integer index = emojiHandler.getEmojiNameToIndexMap().get(event.getReaction().getEmoji().toString().subSequence(0, 1).toString());

        restTemplate.getForObject(optionsMap.get(index), Object.class);
    }
}
