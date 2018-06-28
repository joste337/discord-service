package jos.service.discord.service;

import jos.service.discord.manager.EmojiHandler;
import jos.service.discord.model.MessageOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.RequestBuffer;

import java.util.HashMap;

@Service
public class MessageSender {
    @Autowired
    private EmojiHandler emojiHandler;

    public void sendMessage(String message, IChannel channel) {
        RequestBuffer.request(() -> channel.sendMessage(message));
    }

    public void sendMessageWithOptions(String message, MessageOption[] messageOptions, IChannel channel, IUser user) {
        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append(message);
        for (int i = 0; i < messageOptions.length; i++) {
            messageBuilder.append("\n").append((i + 1)).append(") ").append(messageOptions[i].getMessage());
        }

        RequestBuffer.request(() -> {
            IMessage iMessage = channel.sendMessage(messageBuilder.toString());
            emojiHandler.getUserToOptionsMap().put(user, new HashMap<>());
            for (int i = 0; i < messageOptions.length; i++) {
                final int index = i;
                RequestBuffer.request(() -> iMessage.addReaction(emojiHandler.getNumericEmojiList().get(index)));
                emojiHandler.getUserToOptionsMap().get(user).put(i + 1, messageOptions[i].getUrl());
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {

                }
            }
        });
    }
}
