import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

class Student_Data{
    int rollno;
    String name;
    String branch;
    double grade;
    boolean ispresent=false;
    Student_Data(int rollno, String name,String branch,double grade){
        this.rollno=rollno;
        this.name=name;
        this.branch=branch;
        this.grade=grade;
    }

    void markAttendance(){
        this.ispresent=true;
    }
    void displayStudent(){
        System.out.println("Roll No: " + rollno);
        System.out.println("Name: " + name);
        System.out.println("Branch: " + branch);
        System.out.println("Grade: " + grade + " CGPA");
        System.out.println("Attendance: " + ((ispresent) ? "Present" : "Absent"));
    }

}

public class Student_Management {
    static ArrayList <Student_Data> students = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    public static void addStudent() {
        System.out.print("Enter Roll No: ");
        int rollno = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Name: ");
        String name = sc.nextLine(); 
        System.out.print("Enter Your Branch: ");
        String branch = sc.nextLine(); 
        System.out.print("Enter Your Grade (CGPA): ");
        double grade = sc.nextDouble();
        students.add(new Student_Data(rollno, name, branch, grade));
        System.out.print("Student added successfully ! \n");
        createExcel();
    }

    public static void markAttendance() {
        System.out.print("Enter Roll No to marks Attendance: ");
        int rollno = sc.nextInt();
        for(int i=0;i< students.size();i++){
            if(students.get(i).rollno==rollno){
                students.get(i).markAttendance();
                System.out.println("Attendance Marked !!");
            }
        }
        createExcel();
    }

    public static void displayStudent(){
        if(students.isEmpty()){
            System.out.println("No Students Found !");
        }else{
            for(int i=0;i<students.size();i++){
                students.get(i).displayStudent();
                System.out.println("-----------------------------------------------------------------");
            }
        }
    }

    public static void removeStudent(){
        System.out.print("Enter Roll No to delete Student: ");
        int rollno = sc.nextInt();
        for(int i=0;i<students.size();i++){
            if(students.get(i).rollno==rollno){
                students.remove(i);
                System.out.println("Successfully Removed!!");
            }
            else{
                System.out.println("Student Not Found");
            }
        }
        createExcel();
    }

    public static void createExcel() {
        try {
            FileWriter writer = new FileWriter("ExcelReport.csv");
            writer.write("Roll No,Name,Branch,Grade,Attendance\n"); // Header row
    
            for (int i = 0; i < students.size(); i++) {
                Student_Data student = students.get(i);
                writer.write(student.rollno + "," + student.name + "," + student.branch + "," 
                            + student.grade + "," + (student.ispresent ? "Present" : "Absent") + "\n");
            }
    
            writer.close();
    
        } catch (IOException e) {
            System.out.println("Error while writing the Excel file.");
            e.printStackTrace();
        }
    }
    
    public static void deleteExcel() {
        File file = new File("ExcelReport.csv");
        if(file.exists() && file.delete()){
            System.out.println("Sucessfully Deleted !!");
        }
        else{
            System.out.println("Error in deleting report");
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Runtime.getRuntime().addShutdownHook(new Thread(Student_Management::deleteExcel));
        while (true) {
            System.out.println("-----------------------------------------------------------------");
            System.out.println("Student Management System -- Kailash Solanki");
            System.out.println("-----------------------------------------------------------------");
            System.out.println("1. Add Student");
            System.out.println("2. Mark Attendance");
            System.out.println("3. Display Students");
            System.out.println("4. Remove Student");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            switch(choice){
                case 1:
                    addStudent();
                    break;
                case 2:
                    markAttendance();
                    break;
                case 3:
                    displayStudent();
                    break;
                case 4:
                    removeStudent();
                    break;
                case  5:
                    System.out.println("Exiting.....");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid Choice! Try Again. \n");
            }
        }
    }
}
