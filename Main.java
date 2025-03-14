import java.nio.file.*;

public class Main {
	public static void main(String[] args) {
		directoryLister dLister = new directoryLister();
		dLister.listFiles(args[0]);
		// App app = new app();
		// app.run(args); | app.start(args);
	}
}
