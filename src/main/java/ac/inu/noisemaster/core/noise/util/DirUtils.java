package ac.inu.noisemaster.core.noise.util;

import java.io.File;

public class DirUtils {
    public static String getDir(final String path) {
        File Folder = new File(path);

        if (!Folder.exists()) {
            Folder.mkdir();
        }
        return path;
    }
}
