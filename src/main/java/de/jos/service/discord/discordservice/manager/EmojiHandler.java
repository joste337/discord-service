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
    private Map<String, Integer> emojiNameToIndexMap;

    public EmojiHandler() {
        userToOptionsMap = new HashMap<>();
        initializeNumericEmojiList();
        initializeEmojiNameToIndexMap();
    }

    public List<Emoji> getNumericEmojiList() {
        return numericEmojiList;
    }

    public Map<IUser, Map<Integer, String>> getUserToOptionsMap() {
        return userToOptionsMap;
    }

    public Map<String, Integer> getEmojiNameToIndexMap() {
        return emojiNameToIndexMap;
    }

    private void initializeNumericEmojiList() {
        numericEmojiList = new LinkedList<>();
        numericEmojiList.add(EmojiManager.getForAlias(":one"));
        numericEmojiList.add(EmojiManager.getForAlias(":two"));
        numericEmojiList.add(EmojiManager.getForAlias(":three"));
        numericEmojiList.add(EmojiManager.getForAlias(":fout"));
        numericEmojiList.add(EmojiManager.getForAlias(":five"));
        numericEmojiList.add(EmojiManager.getForAlias(":six"));
        numericEmojiList.add(EmojiManager.getForAlias(":seven"));
        numericEmojiList.add(EmojiManager.getForAlias(":eight"));
        numericEmojiList.add(EmojiManager.getForAlias(":nine"));
    }

    private void initializeEmojiNameToIndexMap() {
        emojiNameToIndexMap = new HashMap<>();
        emojiNameToIndexMap.put("1", 1);
        emojiNameToIndexMap.put("2", 2);
        emojiNameToIndexMap.put("3", 3);
        emojiNameToIndexMap.put("4", 4);
        emojiNameToIndexMap.put("5", 5);
        emojiNameToIndexMap.put("6", 6);
        emojiNameToIndexMap.put("7", 7);
        emojiNameToIndexMap.put("8", 8);
        emojiNameToIndexMap.put("9", 9);
    }
}
