package de.jos.service.discord.discordservice.controller;


import de.jos.service.discord.discordservice.manager.MessageReceivedManager;
import de.jos.service.discord.discordservice.manager.ReactionAddedManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventDispatcher;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.reaction.ReactionAddEvent;

@Controller
public class DiscordClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(DiscordClient.class);
    private static final String BOT_TOKEN = "NDQzNzc1OTMzNzU3Nzg0MDY1.DdSXEg.Zvkv3DHddO8iQeAHJmDmzIpBStg";

    private final IDiscordClient iDiscordClient;
    private final EventDispatcher dispatcher;

    @Autowired
    private MessageReceivedManager messageReceivedManager;
    @Autowired
    private ReactionAddedManager reactionAddedManager;

    public DiscordClient() {
        LOGGER.debug("Connecting Discord-Bot!");
        ClientBuilder clientBuilder = new ClientBuilder();
        clientBuilder.withToken(BOT_TOKEN);
        iDiscordClient = clientBuilder.login();
        dispatcher = iDiscordClient.getDispatcher();
        dispatcher.registerListener(this);
        LOGGER.debug("Connected Discord-Bot successfully!");
    }

    @EventSubscriber
    public void onReactionAddEvent(ReactionAddEvent event) {
        reactionAddedManager.handleAddEvent(event);
    }

    @EventSubscriber
    public void onMessageReceivedEvent(MessageReceivedEvent event) {
        messageReceivedManager.handleReceivedMessage(event);
    }
}
