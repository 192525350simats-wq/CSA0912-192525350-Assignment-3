import java.io.*;
import java.nio.file.*;
import java.util.*;

public class ReportGenerator {
    static String manual(Path dir, List<String> names) {
        StringBuilder report = new StringBuilder("DAILY SALES SUMMARY\n");
        for (String name : names) {
            BufferedReader reader = null;
            try {
                reader = Files.newBufferedReader(dir.resolve(name));
                String line;
                while ((line = reader.readLine()) != null) report.append(name).append(": ").append(line).append('\n');
            } catch (IOException e) {
                System.out.println("Manual version failed at " + name + ": " + e.getMessage());
                // This demonstration deliberately mirrors a fragile manual-close path.
                break;
            } finally {
                if (reader != null) {
                    try { reader.close(); } catch (IOException e) { System.out.println("Close error: " + e.getMessage()); }
                }
            }
        }
        return report.toString();
    }
    static String safe(Path dir, List<String> names) {
        StringBuilder report = new StringBuilder("DAILY SALES SUMMARY\n");
        for (String name : names) {
            try (BufferedReader reader = Files.newBufferedReader(dir.resolve(name))) {
                String line;
                while ((line = reader.readLine()) != null) report.append(name).append(": ").append(line).append('\n');
            } catch (IOException e) {
                System.out.println("Try-with-resources logged " + name + ": " + e.getMessage());
            }
        }
        return report.toString();
    }
    public static void main(String[] args) throws Exception {
        Path dir = Paths.get("input");
        List<String> files = Arrays.asList("store1.txt", "missing_store.txt", "store2.txt");
        System.out.println("--- Manual close version ---");
        String a = manual(dir, files);
        System.out.print(a);
        System.out.println("--- Try-with-resources version ---");
        String b = safe(dir, files);
        System.out.print(b);
        Files.writeString(Paths.get("output/manual_report.txt"), a);
        Files.writeString(Paths.get("output/safe_report.txt"), b);
        System.out.println("Both versions completed their processing loops; resource closure is automatic in the second version.");
    }
}
