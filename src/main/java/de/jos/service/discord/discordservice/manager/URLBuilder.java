package de.jos.service.discord.discordservice.manager;

import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

import javax.annotation.PostConstruct;
import java.net.URISyntaxException;

@Component
public class URLBuilder {
    @Value("${commandhandler.host}")
    private String host;
    @Value("${commandhandler.port}")
    private int port;
    private URIBuilder uriBuilder;

    @PostConstruct
    public void initializeBuilder() {
        uriBuilder = new URIBuilder();
        uriBuilder.setPort(port);
        uriBuilder.setHost(host);
        uriBuilder.setScheme("http");
        uriBuilder.setPath("/handleMessage");
    }

    public String buildUriFromMessageReceivedEvent(MessageReceivedEvent event) {
        uriBuilder.setParameter("message", event.getMessage().toString());
        uriBuilder.setParameter("id", event.getAuthor().getName().hashCode() + event.getAuthor().getDiscriminator());
        uriBuilder.setParameter("userName", event.getAuthor().getName());
        try {
            return uriBuilder.build().toString();
        } catch (URISyntaxException e) {
            throw new RuntimeException("");
        }
    }

    public void clearBuilder() {
        uriBuilder.clearParameters();
    }
}
