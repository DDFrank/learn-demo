package com.hust.hui.quicksilver.text;

import org.apache.commons.lang3.StringUtils;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yihui on 2017/3/28.
 */
public class TextFormat {

    // 获取patter的过程较为负责,这里初始化时,做一次即可
    private static Pattern pattern;

    static {
        pattern = Pattern.compile("((?<=\\{)([a-zA-Z_]{1,})(?=\\}))");
    }


    /**
     * 字符串替换, 将 {} 中的内容, 用给定的参数进行替换
     *
     * @param text
     * @param params
     * @return
     */
    public static String format(String text, Map<String, Object> params) {
        // 把文本中的所有需要替换的变量捞出来, 丢进keys
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            String key = matcher.group();
            text = StringUtils.replace(text, "{" + key + "}", params.get(key) + "");
            text = text.replaceAll("\\{" + key + "\\}", params.get(key) + "");
        }

        return text;
    }


    public static String formatV2(String text, Map<String, Object> params) {
        StringBuilder stringBuilder = new StringBuilder();

        int startIndex = 0;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '{') {
                if (startIndex > 0) {
                    stringBuilder.append(text.substring(startIndex, i));
                }
                startIndex = i + 1;
                continue;
            }

            if (text.charAt(i) == '}') {
                stringBuilder.append(params.get(text.substring(startIndex, i)));
                startIndex = i + 1;
            }
        }

        if (startIndex < text.length()) {
            stringBuilder.append(text.substring(startIndex));
        }

        return stringBuilder.toString();
    }


    public static List<String> batchFormat(String text, List<Map<String, Object>> params) {
        List<String> keys = new ArrayList<>();

        // 把文本中的所有需要替换的变量捞出来, 丢进keys
        Matcher matcher = pattern.matcher(text);
        int tempIndex = 0;
        while (matcher.find()) {
            String key = matcher.group();
            if (keys.contains(key)) {
                continue;
            }


            text = StringUtils.replace(text, key, tempIndex + "");
            tempIndex++;
            keys.add(key);
        }


        List<String> result = new ArrayList<>(params.size());
        String[] tempParamAry = new String[keys.size()];
        for (Map<String, Object> param : params) {

            for (int i = 0; i < keys.size(); i++) {
                tempParamAry[i] = param.get(keys.get(i)) + "";
            }

            result.add(MessageFormat.format(text, tempParamAry));
        }

        return result;
    }


    /**
     * 规定大括号中不能再次出现大括号, 即不允许迭代替换
     *
     * @param text
     * @param paramsList
     * @return
     */
    public static List<String> batchFormatV2(String text, List<Map<String, Object>> paramsList) {

        List<Word> textList = splitText2words(text);


        List<String> result = new ArrayList<>();

        StringBuilder stringBuilder;
        for (Map<String, Object> params : paramsList) {
            stringBuilder = new StringBuilder();
            for (Word word : textList) {
                stringBuilder.append(replaceWord(word, params));
            }
            result.add(stringBuilder.toString());
        }


        return result;
    }


    private static String replaceWord(Word word, Map<String, Object> params) {
        if (word.getIsReplaceKey()) {
            return params.get(word.getWord()) + "";
        } else {
            return word.getWord();
        }
    }

    /**
     * 将文本根据{}进行分割
     * <p/>
     * 如:  {place} is a good place, what do you think {user}?
     * 分割:
     * - Word("place", true)
     * - Word(" is a good place, what do you think ", false)
     * - Word("user", true)
     * - Word("?", false)
     *
     * @param text
     * @return
     */
    private static List<Word> splitText2words(String text) {
        List<Word> textList = new ArrayList<>();


        int startIndex = 0;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '{') {
                if (startIndex > 0) {
                    textList.add(new Word(text.substring(startIndex, i), false));
                }
                startIndex = i + 1;
                continue;
            }

            if (text.charAt(i) == '}') {
                textList.add(new Word(text.substring(startIndex, i), true));
                startIndex = i + 1;
            }
        }

        if (startIndex < text.length()) {
            textList.add(new Word(text.substring(startIndex), false));
        }

        return textList;
    }


    private static class Word {

        private String word;

        /**
         * true 则表示保存的是需要被替换的值
         */
        private Boolean isReplaceKey;

        public Word(String word, Boolean replaceKey) {
            this.word = word;
            this.isReplaceKey = replaceKey;
        }

        public String getWord() {
            return word;
        }

        public Boolean getIsReplaceKey() {
            return isReplaceKey;
        }

        @Override
        public String toString() {
            return "Word{" +
                    "word='" + word + '\'' +
                    ", isReplaceKey=" + isReplaceKey +
                    '}';
        }
    }
}
