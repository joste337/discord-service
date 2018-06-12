package de.jos.service.discord.discordservice.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sx.blah.discord.handle.impl.events.guild.channel.message.reaction.ReactionAddEvent;

import java.util.HashMap;
import java.util.Map;


@Component
public class ReactionAddedManager {
    @Autowired
    private EmojiHandler emojiHandler;

    public void handleAddEvent(ReactionAddEvent event) {
        Map<String, Integer> map = emojiHandler.getEmojiNameToIndexMap();
        String emojiName = event.getReaction().getEmoji().toString().subSequence(0, 1).toString();

        System.out.println("map: " + map.keySet() + ";  emojiName: " + emojiName + ":");

        map.containsKey(emojiName);

        Integer index = map.get(emojiName);



        System.out.println("index: " + index);


//        emojiHandler.getUserToOptionsMap().get(event.getAuthor()).get("");
    }
}
