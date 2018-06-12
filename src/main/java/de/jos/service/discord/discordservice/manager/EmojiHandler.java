package de.jos.service.discord.discordservice.manager;

import com.vdurmont.emoji.Emoji;
import com.vdurmont.emoji.EmojiManager;
import org.springframework.stereotype.Component;
import sx.blah.discord.handle.obj.IUser;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Component
public class EmojiHandler {
    private List<Emoji> numericEmojiList;
    private Map<IUser, Map<Integer, String>> userToOptionsMap;

    public EmojiHandler() {
        userToOptionsMap = new HashMap<>();
        initializeNumericEmojiList();
    }

    public List<Emoji> getNumericEmojiList() {
        return numericEmojiList;
    }

    public Map<IUser, Map<Integer, String>> getUserToOptionsMap() {
        return userToOptionsMap;
    }

    private void initializeNumericEmojiList() {
        List<Emoji> numericEmojiList = new LinkedList<>();
        numericEmojiList.add(EmojiManager.getForAlias(":one"));
        numericEmojiList.add(EmojiManager.getForAlias(":two"));
        numericEmojiList.add(EmojiManager.getForAlias(":three"));
        numericEmojiList.add(EmojiManager.getForAlias(":fout"));
        numericEmojiList.add(EmojiManager.getForAlias(":five"));
        numericEmojiList.add(EmojiManager.getForAlias(":six"));
        numericEmojiList.add(EmojiManager.getForAlias(":sevem"));
        numericEmojiList.add(EmojiManager.getForAlias(":eight"));
        numericEmojiList.add(EmojiManager.getForAlias(":nine"));
        this.numericEmojiList = numericEmojiList;
    }
}
