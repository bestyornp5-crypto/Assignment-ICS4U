import java.util.Scanner;
public class StuSysMain {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    StuSys sys = new StuSys();

    sys.ImportAcct("Mystudent.txt");
    boolean oper = true;
    while(oper){
      System.out.println("==========================================");
      System.out.println("WELCOME TO CIMP STUDENT SYSTEM");
      System.out.println("1. New Student");
      System.out.println("2. Returning Student");
      System.out.println("3. Exit System");
      System.out.print("Pick an option: ");
      int firstChoice = scanner.nextInt();
      scanner.nextLine();
      if (firstChoice == 1){
        // --> SCREEN WHEN WE CREATE AN ACCOUNT
        System.out.println("------------------------");
        System.out.println("|     CREAT ACCOUNT    |");
        System.out.println("------------------------");
        System.out.print("Student ID     : " + sys.currNewId);
        System.out.println();
        System.out.print("Full Name      : ");
        String name = scanner.nextLine();

        System.out.print("Password       : ");
        String pass = scanner.nextLine();

        System.out.print("Retype Password: ");
        String retype = scanner.nextLine();

        int created = sys.currNewId;
        int status = sys.CreateNewAcct(name, pass, retype);

        if (status == StuSys.STATUS_SUCESS)
          System.out.println("Successfully created new account " + created );
        else if (status == sys.ERR_PASS_LENGTH)
          System.out.println("Error: Password must be 5 characters !");
        else if (status == StuSys.ERR_PASS_MISMATCH)
          System.out.println("Error: Passwords do not match !");
        else {
          System.out.println("Error: Could not create account.");
        }

      }

    }

    
  }
}