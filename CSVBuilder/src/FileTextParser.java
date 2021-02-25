import java.io.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class FileTextParser {
    private long _wordCount;
    private Map<String, Long> _wordMap;

    FileTextParser() {
        _wordCount = 0;
        _wordMap = new HashMap<>();
    }

    FileTextParser(String filePath) throws IOException {
        _wordCount = 0;
        try {
            buildWordMap(filePath);
        } catch (IOException e) {
            throw e;
        }
    }

    public long getWordCount() {
        return _wordCount;
    }

    public Map<String, Long> getWordMap() {
        return _wordMap;
    }

    public void buildWordMap(String filePath) throws IOException {
        _wordCount = 0;
        Map<String, Long> wordMap = new HashMap<>();
        Reader reader = null;
        try {
            reader = new InputStreamReader(new FileInputStream(filePath));
            StringBuilder stringBuilder = new StringBuilder();
            int characterCode = 0;
            while ((characterCode = reader.read()) != -1) {
                char character = Character.toLowerCase((char)characterCode);
                if (Character.isLetterOrDigit(character)) {
                    stringBuilder.append(character);
                } else if (stringBuilder.length() > 0) {
                    wordMap.compute(stringBuilder.toString(), (k, v) -> v == null ? 1 : v + 1);
                    ++_wordCount;
                    stringBuilder.setLength(0);
                }
            }
            if (stringBuilder.length() > 0) {
                wordMap.compute(stringBuilder.toString(), (k, v) -> v == null ? 1 : v + 1);
                ++_wordCount;
            }
            wordMap = sortMapByKey(wordMap);
            wordMap = sortMapByValue(wordMap);
            _wordMap = wordMap;
        } catch (IOException e) {
            System.err.println("Error while reading file: " + e.getLocalizedMessage());
        } finally {
            if (null != reader) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace(System.err);
                }
            }
        }
    }

    private Map<String, Long> sortMapByKey(Map<String, Long> map) {
        return map.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    private Map<String, Long> sortMapByValue(Map<String, Long> map) {
        return map.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

}
