import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;

public class ls {
    public static void list(String path) {
        Path dir = Paths.get(path);
        Map<Path, Long> filesSize = new HashMap<Path, Long>();
        try {
			DirectoryStream<Path> files = Files.newDirectoryStream(dir);
			for (Path file: files) {
                filesSize.put(file.getFileName(), size(file));
				// System.out.println(file.getFileName() + padding() + size(file));
                // System.out.println(file.getFileName());
            }
            files.close();

            int maxLen = 0;
            for (Map.Entry<Path, Long> entry : filesSize.entrySet()) {
                int filenameSize = entry.getKey().toString().length();
                if (filenameSize > maxLen) {
                    maxLen = filenameSize;
                }
            }

            for (Map.Entry<Path, Long> entry : filesSize.entrySet()) {
                System.out.println(entry.getKey() + padding(maxLen - entry.getKey().toString().length()) + compress(entry.getValue()));
            }

            System.out.println(maxLen);
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
    }

    private static String padding(int maxLen) {
        String padding = "";
        for (int i = 0; i < maxLen + 10; i++) {
            padding += " ";
        }
        return padding;
    }

    private static String compress(long size) {
        if (size < 1024) {
            return size + " B";
        } else if (size < 1024 * 1024) {
            return size / 1024 + " KB";
        } else if (size < 1024 * 1024 * 1024) {
            return size / 1024 / 1024 + " MB";
        } else {
            return size / 1024 / 1024 / 1024 + " GB";
        }
    }

    private static long size(Path item) {
        try {
            BasicFileAttributes attrs = Files.readAttributes(item, BasicFileAttributes.class);
            if (Files.isDirectory(item)) {
                DirectoryStream<Path> files = Files.newDirectoryStream(item);
                long fileSize = 0;
                for (Path file: files) {
                    // System.out.println(file.getFileName());
                    fileSize += attrs.size() + size(file);
                }
                return fileSize;
            } else {
                return attrs.size();
            }
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
        return 0;
    }
}
