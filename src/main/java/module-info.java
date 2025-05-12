module StardewValley17 {
    requires java.desktop;
    requires com.google.gson;

    exports org.example.views;
    opens org.example.views to javafx.fxml;

    opens org.example.models to com.google.gson;
    opens org.example.models.market to com.google.gson;
    opens org.example.models.NPCS to com.google.gson;
    opens org.example.models.plant to com.google.gson;
    opens org.example.models.tools to com.google.gson;
    opens org.example.models.foraging to com.google.gson;
    opens org.example.models.cooking to com.google.gson;
    opens org.example.models.crafting to com.google.gson;
    opens org.example.models.artisan to com.google.gson;
    opens org.example.models.enums to com.google.gson;
    opens org.example.models.map to com.google.gson;

    exports org.example.models;
    exports org.example.models.enums;
    exports org.example.models.map;
}
