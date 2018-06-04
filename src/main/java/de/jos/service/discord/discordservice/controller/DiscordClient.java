package de.jos.service.discord.discordservice.controller;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.URIBuilder;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventDispatcher;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.util.RequestBuffer;

import java.net.URISyntaxException;
import java.util.function.Supplier;

@Controller
public class DiscordClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(DiscordClient.class);

    @Value("${commandhandler.host}")
    private String host;
    @Value("${commandhandler.port}")
    private int port;
    private static final String BOT_TOKEN = "NDQzNzc1OTMzNzU3Nzg0MDY1.DdSXEg.Zvkv3DHddO8iQeAHJmDmzIpBStg";

    private final IDiscordClient iDiscordClient;
    private final EventDispatcher dispatcher;
    private RestTemplate restTemplate;
    private URIBuilder uriBuilder;

    public DiscordClient(RestTemplateBuilder restTemplateBuilder) {
        LOGGER.debug("Connecting Discord-Bot!");
        ClientBuilder clientBuilder = new ClientBuilder();
        clientBuilder.withToken(BOT_TOKEN);
        iDiscordClient = clientBuilder.login();
        dispatcher = iDiscordClient.getDispatcher();
        dispatcher.registerListener(this);
        restTemplate = restTemplateBuilder.build();
        LOGGER.debug("Connected to Discord-Bot successfully!");
    }

    @EventSubscriber
    public void onMessageReceivedEvent(MessageReceivedEvent event) {
        initializeBuilder();
        uriBuilder.setParameter("message", event.getMessage().toString());
        uriBuilder.setParameter("id", event.getAuthor().getName().hashCode() + event.getAuthor().getDiscriminator());
        uriBuilder.setParameter("userName", event.getAuthor().getName());

        String botReply;
        try {
            String url = uriBuilder.build().toString();
            LOGGER.info("Requesting URL: {}", url);
            JSONObject response = new JSONObject(doRequestAndResetUriBuilder(() -> restTemplate.getForObject(url, String.class)));
            botReply = response.get("message").toString();
        } catch (URISyntaxException e) {
            initializeBuilder();
            botReply = "uri builder failed;";
        }

        IChannel channel = event.getChannel();

        sendMessage(botReply, channel);
    }

    private void sendMessage(String message, IChannel channel) {
        if (message.length() > 2000) {
            String remainingMessage = StringUtils.substring(message,  2000);
            sendMessageWithBuffer(StringUtils.substring(message, 0, 2000), channel);
            sendMessage(remainingMessage, channel);
            return;
        }
        sendMessageWithBuffer(message, channel);
    }

    private void sendMessageWithBuffer(String message, IChannel channel) {
        RequestBuffer.request(() -> {
            channel.sendMessage(message);
        });
    }

    private String doRequestAndResetUriBuilder(Supplier<String> supplier) {
        String result = supplier.get();
        uriBuilder.clearParameters();
        return result;
    }

    private void initializeBuilder() {
        uriBuilder = new URIBuilder();
        uriBuilder.setPort(port);
        uriBuilder.setHost(host);
        uriBuilder.setScheme("http");
        uriBuilder.setPath("/handleMessage");
    }
}
