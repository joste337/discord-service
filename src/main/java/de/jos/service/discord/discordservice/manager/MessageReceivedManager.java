package de.jos.service.discord.discordservice.manager;

import com.vdurmont.emoji.EmojiManager;
import de.jos.service.discord.discordservice.controller.DiscordClient;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventDispatcher;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.impl.obj.ReactionEmoji;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.RequestBuffer;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@Component
public class MessageReceivedManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageReceivedManager.class);

    @Autowired
    private URLBuilder urlBuilder;
    @Autowired
    private MessageSender messageSender;
    private RestTemplate restTemplate;

    public MessageReceivedManager(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder.build();
    }

    public void handleReceivedMessage(MessageReceivedEvent event) {
        String url = urlBuilder.buildUriFromMessageReceivedEvent(event);
        LOGGER.info("Requesting URL: {}", url);
        JSONObject response = new JSONObject(doRequestAndResetUriBuilder(() -> restTemplate.getForObject(url, String.class)));

        messageSender.sendMessage(response.get("message").toString(), event.getChannel());
    }

    private String doRequestAndResetUriBuilder(Supplier<String> supplier) {
        String result = supplier.get();
        urlBuilder.clearBuilder();
        return result;
    }
}
