package de.jos.service.discord.discordservice.manager;

import de.jos.service.discord.discordservice.model.DiscordResponse;
import de.jos.service.discord.discordservice.model.MessageOption;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.RequestBuffer;

import java.util.HashMap;


@Component
public class MessageSender {
    @Autowired
    private EmojiHandler emojiHandler;

    public void sendMessage(DiscordResponse discordResponse, IChannel channel, IUser user) {
        String message = discordResponse.getMessage();

        MessageOption[] messageOptions = discordResponse.getMessageOptions();

        if (messageOptions != null) {
            sendMessageWithOptions(message, messageOptions, channel, user);
        } else {
            sendMessageWithBuffer(message, channel);
        }
    }

    private void sendMessageWithBuffer(String message, IChannel channel) {
        RequestBuffer.request(() -> channel.sendMessage(message));
    }

    private void sendMessageWithOptions(String message, MessageOption[] messageOptions, IChannel channel, IUser user) {
        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append(message);
        for (int i = 0; i < messageOptions.length; i++) {
            messageBuilder.append("\n").append((i + 1)).append(") ").append(messageOptions[i]);
        }

        RequestBuffer.request(() -> {
            IMessage iMessage = channel.sendMessage(messageBuilder.toString());
            emojiHandler.getUserToOptionsMap().put(user, new HashMap<>());
            for (int i = 0; i < messageOptions.length; i++) {
                iMessage.addReaction(emojiHandler.getNumericEmojiList().get(i));
                emojiHandler.getUserToOptionsMap().get(user).put(i + 1, messageOptions[i].getUrl());
            }
        });
    }
}
