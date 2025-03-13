import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class ls {
    public static void list(String path) {
        Path dir = Paths.get(path);
        try {
			DirectoryStream<Path> files = Files.newDirectoryStream(dir);
			for (Path file: files) {
                Path fileName = file.getFileName();
				System.out.println(file.getFileName() + padding() + size(file));
                // System.out.println(file.getFileName());
            }
            files.close();
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
    }

    private static String padding() {
        return "";
    }

    private static String compress(long bytes) {
        return "";
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
