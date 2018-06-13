package de.jos.service.discord.discordservice.manager;


import de.jos.service.discord.discordservice.model.DiscordResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

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

        DiscordResponse response = doRequestAndResetUriBuilder(() -> restTemplate.getForObject(url, DiscordResponse.class));

        messageSender.sendMessage(response, event.getChannel(), event.getAuthor());
    }

    private DiscordResponse doRequestAndResetUriBuilder(Supplier<DiscordResponse> supplier) {
        DiscordResponse result = supplier.get();
        urlBuilder.clearBuilder();
        return result;
    }
}
