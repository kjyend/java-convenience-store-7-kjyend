package store.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Utils {

    public List<String> readFile(String files) {
        try {
            Path path = Paths.get(files);
            return Files.readAllLines(path);
        } catch (IOException e) {
            throw new IllegalArgumentException("[ERROR] 경로에 파일이 존재하지 않습니다.");
        }
    }
}
