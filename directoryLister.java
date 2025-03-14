import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;

public class directoryLister {
    private Map<Path, Long> fileNamesAndSizes = new HashMap<Path, Long>();

    public void listFiles(String path) {
        try {
            Path directoryPath = Paths.get(path);
            // Gets the directory files in a stream 
			DirectoryStream<Path> files = Files.newDirectoryStream(directoryPath);
            // Iterate through the stream of files and add them to a map
			for (Path file: files) {
                fileNamesAndSizes.put(file.getFileName(), fileSizeCalculator(file));
            }
            files.close();
            
            // Calculation of maxLen of the fileName so that the padding aftewards
            // is added accordingly so that the data is in like a table format
            int maxLen = 0;
            for (Map.Entry<Path, Long> entry : fileNamesAndSizes.entrySet()) {
                int filenameSize = entry.getKey().toString().length();
                if (filenameSize > maxLen) {
                    maxLen = filenameSize;
                }
            }

            // Output the filesNames + padding (spaces used) + the compressed format of byte memory
            for (Map.Entry<Path, Long> entry : fileNamesAndSizes.entrySet()) {
                System.out.println(entry.getKey() + spacePadding(maxLen - entry.getKey().toString().length()) + byteCompresion(entry.getValue()));
            }
		} catch (Exception e) {
			System.err.println("Error: " + e);
		}
    }

    private String spacePadding(int maxLen) {  
        String padding = "";
        // Adds spaces for maxLen - fileName.length() + 10 so that the fileSize is in one
        // straight line
        for (int i = 0; i < maxLen + 10; i++) {
            padding += " ";
        }
        return padding;
    }

    private String byteCompresion(long size) {
        // Compares the byte number with exponential of 1024 to determine the best
        // suitable measurement unit for it 
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

    // Recursive function calculating the fileSize of the files
    // and directorySize by adding the sum of the files inside
    // the directory itself
    private long fileSizeCalculator(Path item) {
        try {
            BasicFileAttributes attrs = Files.readAttributes(item, BasicFileAttributes.class);
            if (Files.isDirectory(item)) {
                DirectoryStream<Path> files = Files.newDirectoryStream(item);
                long fileSize = 0;
                for (Path file: files) {
                    // Recursion occurs here 
                    fileSize += attrs.size() + fileSizeCalculator(file);
                }
                // Returns the final Size of one directory
                return fileSize;
            } else {
                // Returns the final Size of one file
                return attrs.size();
            }
		} catch (Exception e) {
			System.err.println("Error: " + e);
		}
        return 0;
    }
}
