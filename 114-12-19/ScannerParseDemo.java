import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.Scanner;

public class ScannerParseDemo {
    private static class Student {
        final String name;
        final int score;
        final double gpa;

        Student(String name, int score, double gpa) {
            this.name = name;
            this.score = score;
            this.gpa = gpa;
        }
    }

    public static void main(String[] args) {
        //createTestFile();
        List<Student> students = new ArrayList<>();
        
        try (Scanner scanner = new Scanner(new File("scores.txt"))) {
            System.out.println("=== 成績資料 ===");
            
            while (scanner.hasNext()) {
                String name = scanner.next();      // 讀取字串
                int score = scanner.nextInt();     // 讀取整數
                double gpa = scanner.nextDouble(); // 讀取浮點數
                
                System.out.printf("%s: %d 分, GPA: %.2f%n", name, score, gpa);
                students.add(new Student(name, score, gpa));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

        try (Formatter formatter = new Formatter("outputScores.txt")) {
            formatter.format("姓名\t分數\tGPA%n");
            for (Student student : students) {
                formatter.format("%s\t%d\t%.2f%n", student.name, student.score, student.gpa);
            }
            System.out.println("已將資料輸出至 outputScores.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    private static void createTestFile() {
        try (PrintWriter pw = new PrintWriter("scores.txt")) {
            pw.println("王小明 85 3.5");
            pw.println("李小華 92 4.0");
            pw.println("張小美 78 3.2");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
