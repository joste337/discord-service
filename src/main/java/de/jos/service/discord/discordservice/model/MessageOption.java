package de.jos.service.discord.discordservice.model;

public class MessageOption {
    private String message;
    private String url;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "MessageOption{" +
                "message='" + message + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
