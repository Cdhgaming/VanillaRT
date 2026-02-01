package net.vanillart;

import net.vanillart.parser.VanillaRTParser;
import net.vanillart.parser.VanillaRTParser.ParsedItem;
import net.vanillart.runtime.RuntimeManager;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

public class Main {

    public static final Path PMODS_FOLDER = Paths.get(System.getProperty("user.home"), ".minecraft", "vanillart", "pmods");
    public static final RuntimeManager runtimeManager = new RuntimeManager();

    public static void main(String[] args) {
        System.out.println("=== VanillaRT Modloader v1.0 ===");

        // Ensure pmods folder exists
        try {
            if (!Files.exists(PMODS_FOLDER)) {
                Files.createDirectories(PMODS_FOLDER);
                System.out.println("Created pmods folder at: " + PMODS_FOLDER.toString());
            }
        } catch (IOException e) {
            System.err.println("Failed to create pmods folder: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        // Initialize parser
        VanillaRTParser parser = new VanillaRTParser();

        // Scan for .vrt files
        try {
            List<Path> vrtFiles = Files.list(PMODS_FOLDER)
                    .filter(f -> f.toString().toLowerCase().endsWith(".vrt"))
                    .toList();

            if (vrtFiles.isEmpty()) {
                System.out.println("No Plug-Mods found in: " + PMODS_FOLDER);
            } else {
                System.out.println("Found " + vrtFiles.size() + " Plug-Mod(s).");
            }

            for (Path vrt : vrtFiles) {
                System.out.println("Loading: " + vrt.getFileName());
                List<ParsedItem> items = parser.parseFile(vrt);
                runtimeManager.executeItems(items);
            }

        } catch (IOException e) {
            System.err.println("Error scanning pmods folder: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("VanillaRT runtime initialized (dummy mode)");
        System.out.println("Ready to execute Plug-Mods...");
    }
}
