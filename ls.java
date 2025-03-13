import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class ls {
    public static void list(String path) {
        Path dir = Paths.get(path);
        try {
			DirectoryStream<Path> files = Files.newDirectoryStream(dir);
			for (Path file: files) {
				System.out.println(file.getFileName() + " " + size(file));
                // System.out.println(file.getFileName());
            }
            files.close();
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
    }

    private static long size(Path item) {
        try {
            BasicFileAttributes attrs = Files.readAttributes(item, BasicFileAttributes.class);
            if (Files.isDirectory(item)) {
                DirectoryStream<Path> files = Files.newDirectoryStream(item);
                int fileSize = 0;
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
