package jos.service.discord.service;

import jos.service.discord.model.CommandServiceResponse;
import jos.service.discord.util.CommandServiceSettings;
import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

import java.net.URI;
import java.net.URISyntaxException;

@Service
public class CommandServiceCaller {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommandServiceCaller.class);
    @Autowired
    private CommandServiceSettings commandServiceSettings;
    private RestTemplate restTemplate;

    public CommandServiceCaller(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder.build();
    }

    public CommandServiceResponse callCommandService(MessageReceivedEvent event) {
        URIBuilder uriBuilder = getInitializedURIBuilder();
        uriBuilder.setParameter("message", event.getMessage().toString());
        uriBuilder.setParameter("messengerId", event.getAuthor().getName().hashCode() + event.getAuthor().getDiscriminator());
        uriBuilder.setParameter("userName", event.getAuthor().getName());

        try {
            return callCommandService(uriBuilder.build());
        } catch (URISyntaxException exception) {
            return CommandServiceResponse.getErrorCommandServiceResponse();
        }
    }

    private CommandServiceResponse callCommandService(URI requestURI) {
        LOGGER.info("Requesting {}", requestURI);
        try {
            return restTemplate.getForObject(requestURI, CommandServiceResponse.class);
        } catch (Exception exception) {
            return CommandServiceResponse.getErrorCommandServiceResponse();
        }
    }

    private URIBuilder getInitializedURIBuilder() {
        URIBuilder uriBuilder = new URIBuilder();
        uriBuilder.setHost(commandServiceSettings.getHost());
        uriBuilder.setPort(commandServiceSettings.getPort());
        uriBuilder.setScheme(commandServiceSettings.getSchema());
        uriBuilder.setPath(commandServiceSettings.getPath());
        return uriBuilder;
    }
}
