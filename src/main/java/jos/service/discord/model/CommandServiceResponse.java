package jos.service.discord.model;

public class CommandServiceResponse {
    private String message;
    private MessageOption[] messageOptions;

    public CommandServiceResponse() {
    }

    public CommandServiceResponse(String message) {
        this.message = message;
    }

    public static CommandServiceResponse getErrorCommandServiceResponse() {
        return new CommandServiceResponse("Oops, an error occured :(");
    }

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
