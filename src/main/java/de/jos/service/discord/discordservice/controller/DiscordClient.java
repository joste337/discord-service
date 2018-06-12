package de.jos.service.discord.discordservice.controller;

import com.vdurmont.emoji.Emoji;
import com.vdurmont.emoji.EmojiManager;
import de.jos.service.discord.discordservice.manager.MessageReceivedManager;
import de.jos.service.discord.discordservice.manager.URLBuilder;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.URIBuilder;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventDispatcher;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.reaction.ReactionAddEvent;
import sx.blah.discord.handle.impl.obj.ReactionEmoji;
import sx.blah.discord.handle.obj.IUser;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Controller
public class DiscordClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(DiscordClient.class);

    private static final String BOT_TOKEN = "NDQzNzc1OTMzNzU3Nzg0MDY1.DdSXEg.Zvkv3DHddO8iQeAHJmDmzIpBStg";
    private Map<IUser, Map<ReactionEmoji, String>> numberReactionToUrlMapForUser = new HashMap<>();

    private final IDiscordClient iDiscordClient;
    private final EventDispatcher dispatcher;

    @Autowired
    private URLBuilder urlBuilder;
    @Autowired
    private MessageReceivedManager messageReceivedManager;

    public DiscordClient() {
        LOGGER.debug("Connecting Discord-Bot!");
        ClientBuilder clientBuilder = new ClientBuilder();
        clientBuilder.withToken(BOT_TOKEN);
        iDiscordClient = clientBuilder.login();
        dispatcher = iDiscordClient.getDispatcher();
        dispatcher.registerListener(this);
        LOGGER.debug("Connected to Discord-Bot successfully!");
    }

    @EventSubscriber
    public void onReactionAddEvent(ReactionAddEvent event) {
        ReactionEmoji reactionEmoji = event.getReaction().getEmoji();
        System.out.println("reaction emoji: " + reactionEmoji.getName());
    }

    @EventSubscriber
    public void onMessageReceivedEvent(MessageReceivedEvent event) {
        messageReceivedManager.handleReceivedMessage(event);
    }

    private List<ReactionEmoji> getNumberEmojiList() {
        List<ReactionEmoji> numberEmojiList = new LinkedList<>();
        numberEmojiList.add(ReactionEmoji.of(":one:"));
        numberEmojiList.add(ReactionEmoji.of(":two:"));
        numberEmojiList.add(ReactionEmoji.of(":three:"));
        numberEmojiList.add(ReactionEmoji.of(":fout:"));
        numberEmojiList.add(ReactionEmoji.of(":five:"));
        numberEmojiList.add(ReactionEmoji.of(":six:"));
        numberEmojiList.add(ReactionEmoji.of(":sevem:"));
        numberEmojiList.add(ReactionEmoji.of(":eight:"));
        numberEmojiList.add(ReactionEmoji.of(":nine:"));
        return numberEmojiList;
    }
}
