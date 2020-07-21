/*
 * @created 19/07/2020 - 06:51
 * @project gutenberg-analysis
 * @author Varsha Varadarajan
 */

package utils;

import java.nio.file.Files;
import java.nio.file.Paths;

public class FileReader implements IFileReader {
    private static FileReader fileReader;

    private String data;
    public String getData() {
        return data;
    }

    private FileReader() { }

    public static FileReader getInstance() {
        if (fileReader == null) {
            fileReader = new FileReader();
        }
        return fileReader;
    }

    public String readFileAsString(String fileName) throws Exception {
        if (fileName == null) {
            throw new Exception("Invalid filename");
        }
        data = new String(Files.readAllBytes(Paths.get(fileName)));
        return data;
    }
}
