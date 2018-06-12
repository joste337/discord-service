package de.jos.service.discord.discordservice.model;

public class DiscordResponse {
    private String message;
    private MessageOption[] messageOptions;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MessageOption[] getMessageOptions() {
        return messageOptions;
    }

    public void setMessageOptions(MessageOption[] messageOptions) {
        this.messageOptions = messageOptions;
    }
}
