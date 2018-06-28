package jos.service.discord.manager;


import jos.service.discord.model.CommandServiceResponse;
import jos.service.discord.service.CommandServiceCaller;
import jos.service.discord.service.MessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

@Component
public class MessageReceivedManager {
    @Autowired
    private MessageSender messageSender;
    @Autowired
    private CommandServiceCaller commandServiceCaller;

    public void handleReceivedMessage(MessageReceivedEvent event) {
        CommandServiceResponse commandServiceResponse = commandServiceCaller.callCommandService(event);

        if (commandServiceResponse.getMessageOptions() == null) {
            messageSender.sendMessage(commandServiceResponse.getMessage(), event.getChannel());
        } else {
            messageSender.sendMessageWithOptions(commandServiceResponse.getMessage(), commandServiceResponse.getMessageOptions(), event.getChannel(), event.getAuthor());
        }
    }
}
