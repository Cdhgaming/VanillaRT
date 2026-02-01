package net.vanillart.util;

import java.nio.file.*;
import java.io.IOException;

public class FileLocker {

    public static void lockFile(Path source, Path dest) throws IOException {
        Files.copy(source, dest, StandardCopyOption.REPLACE_EXISTING);
        dest.toFile().setReadOnly();
    }

}
