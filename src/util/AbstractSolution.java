package util;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSolution implements Serializable {
    protected final List<String> input = new ArrayList<>();

    protected final void loadInput(String filename) {
        try {
            String currentPackage = getClass().getPackageName().replace('.','/') + "/";
            input.addAll(Files.readAllLines(Path.of("src/" + currentPackage + filename)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
