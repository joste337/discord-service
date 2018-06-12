package de.jos.service.discord.discordservice.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sx.blah.discord.handle.impl.events.guild.channel.message.reaction.ReactionAddEvent;

@Component
public class ReactionAddedManager {
    @Autowired
    private EmojiHandler emojiHandler;

    public void handleAddEvent(ReactionAddEvent event) {
        event.getReaction().getEmoji().getName();


        emojiHandler.getUserToOptionsMap().get(event.getAuthor()).get("");
    }
}
