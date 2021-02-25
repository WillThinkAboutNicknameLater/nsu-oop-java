import java.io.*;
import java.util.*;

public class CSVBuilder {
    String _inputFilePath = null;
    String _outputFilePath = null;

    CSVBuilder(String inputFilePath, String outputFilePath) {
        _inputFilePath = inputFilePath;
        _outputFilePath = outputFilePath;
    }

    public void createFile() throws IOException {
        try {
            FileTextParser textParser = new FileTextParser(_inputFilePath);
            long wordCount = textParser.getWordCount();
            Map<String, Long> wordMap = textParser.getWordMap();
            FileWriter writer = new FileWriter(_outputFilePath);
            writer.write("Word,Frequency,Percentage\n");
            for(Map.Entry entry : wordMap.entrySet()) {
                double frequency = ((Number)entry.getValue()).doubleValue() / wordCount;
                writer.write(entry.getKey() + "," + entry.getValue() + "," + frequency * 100 + "\n");
            }
            writer.close();
        } catch(IOException e) {
            throw e;
        }
    }

}