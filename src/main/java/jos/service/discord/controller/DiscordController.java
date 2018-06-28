package jos.service.discord.controller;

import jos.service.discord.manager.MessageReceivedManager;
import jos.service.discord.manager.ReactionAddedManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventDispatcher;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.reaction.ReactionAddEvent;
import sx.blah.discord.handle.obj.IUser;

@RestController
public class DiscordController {
    private static final String BOT_TOKEN = "NDQzNzc1OTMzNzU3Nzg0MDY1.DdSXEg.Zvkv3DHddO8iQeAHJmDmzIpBStg";

    private final IDiscordClient iDiscordClient;
    private final EventDispatcher dispatcher;
    private final IUser discordBotAsUser;
    @Autowired
    private MessageReceivedManager messageReceivedManager;
    @Autowired
    private ReactionAddedManager reactionAddedManager;

    public DiscordController() {
        ClientBuilder clientBuilder = new ClientBuilder();
        clientBuilder.withToken(BOT_TOKEN);
        iDiscordClient = clientBuilder.login();
        dispatcher = iDiscordClient.getDispatcher();
        dispatcher.registerListener(this);
        discordBotAsUser = iDiscordClient.getApplicationOwner();
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
