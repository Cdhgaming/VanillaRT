package net.vanillart.runtime;

import net.vanillart.parser.VanillaRTParser.ParsedItem;
import java.util.List;

public class RuntimeManager {

    public void executeItems(List<ParsedItem> items) {
        for (ParsedItem item : items) {
            if (item.valid) {
                // Placeholder: hook into Minecraft runtime here
                System.out.println("Executing item: " + (item.name != null ? item.name : item.texture));
            } else {
                System.out.println("Skipping invalid item with errors: " + item.errors);
            }
        }
    }
}
