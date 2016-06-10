package Utils;

import java.util.HashMap;

public class MapUtils {
    private static HashMap<String, String> lingdaoMaps;
    static {
        lingdaoMaps = new HashMap<>();
        lingdaoMaps.put("王岩", "wangyan.png");
    }

    public static String getLingDaoPhoto(String name) {
        String file = lingdaoMaps.get(name);
        if (file == null) {
            return "default.png";
        } else {
            return file;
        }
    }
}
