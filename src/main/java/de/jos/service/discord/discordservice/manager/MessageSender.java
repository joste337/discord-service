package de.jos.service.discord.discordservice.manager;

import com.vdurmont.emoji.EmojiManager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.RequestBuffer;

@Component
public class MessageSender {
    public void sendMessage(String message, IChannel channel) {
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
            IMessage a = channel.sendMessage(message);
            a.addReaction(EmojiManager.getForAlias(":one:"));
        });
    }
}
