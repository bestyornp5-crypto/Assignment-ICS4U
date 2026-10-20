import java.util.InputMismatchException;
import java.util.Scanner;
public class StuSysMain {
  // Use a methods private to transform the data from the Back-end(grade --> could be: TBD (-100)
  // or Dropped(- 200 ) and normal score
  private static String Transformer(int grade){
    if(grade == StuSys.GRADE_TBD){
      return "TBD";
    } else if (grade == StuSys.GRADE_DROPPED){
      return "DROPPED";
    } else {
      // Remember return data is String
      return String.valueOf(grade); // Support to transform the Strange Number of Back-End -
    } // --> to Front End
  }
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    StuSys sys = new StuSys();

    sys.ImportAcct("AcctInfo.txt");
    boolean oper = true;
    while(oper){
      // DESIGN THE MENU MAIN SCREEN
      System.out.println("====================================");
      System.out.println("|  WELCOME TO CIMP STUDENT SYSTEM  |");
      System.out.println("====================================");
      System.out.println("1. New Student");
      System.out.println("2. Returning Student");
      System.out.println("3. Exit System");
      System.out.print("Pick an option: ");
      // Maybe user will input something is funny
      int firstChoice = 0;
      try {
        firstChoice = scanner.nextInt();
        scanner.nextLine(); // clear enter
      } catch (InputMismatchException e){
        System.out.println("Error: Invalid Choice, please follow the instruction !");
        scanner.nextLine();
        continue;
      }
      if (firstChoice == 1){
        // --> SCREEN WHEN WE CREATE AN ACCOUNT
        System.out.println("------------------------");
        System.out.println("|     CREAT ACCOUNT    |");
        System.out.println("------------------------");
        // In the textfile has 5 (10000 - 10004) data so , next is 10005
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
        // Process to checking the account can create or not
        if (status == StuSys.STATUS_SUCESS)
          System.out.printf("Successfully created new account \'%d\'\n",created );
        else if (status == StuSys.ERR_PASS_LENGTH)
          System.out.println("Error: Password must be 5 characters !");
        else if (status == StuSys.ERR_PASS_MISMATCH)
          System.out.println("Error: Passwords do not match !");
        else {
          System.out.println("Error: Could not create account.");
        }

      }
      else if(firstChoice == 2 ){
        System.out.println("----------------------");
        System.out.println("|     LOGIN SCREEN   |");
        System.out.println("----------------------");
        System.out.print("Student ID : ");
        String loginID = scanner.nextLine();
        System.out.print("Password : ");
        String password = scanner.nextLine();
        int loginStatus = sys.Login(loginID,password);
        if (loginStatus == StuSys.STATUS_SUCESS) { // That means == 1 is successful
          System.out.println("Login Sucess.");
          boolean loggedIn = true; // Condition if choose 6 it will become false and program wi stop
          while (loggedIn){
            // CIMP STUDENT SCREEN
            System.out.println("========================================================");
            System.out.println("|                 CIMP STUDENT SYSTEM                  |");
            System.out.println("|                    OPTION SCREEN                     |");
            System.out.println("========================================================");
            System.out.println("|    1. STUDENT DETAIL     |    4. EDIT COURSE GRADE   |");
            System.out.println("|--------------------------|---------------------------|");
            System.out.println("|    2. ADD COURSE         |    5. CHANGE PASSWORD     |");
            System.out.println("|--------------------------|---------------------------|");
            System.out.println("|    3. DROP COURSES       |    6. LOG OUT             |");
            System.out.println("========================================================");
            System.out.print("Pick an option: ");
            // Maybe they will press sth is funny
            int subChoice = 0;
            try {
              subChoice = scanner.nextInt();
              scanner.nextLine();
            } catch (InputMismatchException e) {
              System.out.println("Error: Invalid Choice, please follow the instruction !");
              scanner.nextLine();
              continue;
            }
            if (subChoice == 1){
              // STUDENT DETAIL
              System.out.println("-----------------------------------------");
              System.out.println("|             STUDENT DETAIL            |");
              System.out.println("-----------------------------------------");
              System.out.printf("|Student ID    :%23s |\n",sys.loginAcctId);
              // loginAcctId was assigned when u logged in selection 2 in the Main Menu
              System.out.printf("|Student Name  :%23s |\n",sys.GetStudentName(sys.loginAcctId));
              double gpa = sys.GetGPA(sys.loginAcctId);
              // Get the gpa from backend
              if (gpa < 0 ){ // After that we check
                System.out.printf("|GPA           :%23s |\n","N/A");
              } else{ // if GPA < 0 (-8) that means no course has been completed --> Don't have GPA
                System.out.printf("|GPA           :%23.2f%s |\n",gpa,"%");
              }
              System.out.println("-----------------------------------------");
              System.out.println();
              System.out.println("-----------------------------------------");
              System.out.println("|      COURSE       |       GRADES      |");
              System.out.println("-----------------------------------------");
              int numCourses  = sys.NumCourse(sys.loginAcctId);
              // I take the number course to use loop to get each name of course and grade of course
              if (numCourses <= 0){
                System.out.println("No course at the moment!");
              // loginAcctId
              }else {
                for (int i = 0; i < numCourses; i++){
                  String courseName = sys.GetCourseNameAt(sys.loginAcctId,i); // This one
                  int courseGrade = sys.GetCourseGradeAt(sys.loginAcctId,i); // This one
                  System.out.printf("| %2d %-15s| %17s |\n",(i + 1),courseName,Transformer(courseGrade));
                }
                System.out.println("-----------------------------------------");
              }
            } else if (subChoice == 2){
              // ADD COURSES
              System.out.println("-----------------------------------------");
              System.out.println("|             ADD COURSES               |");
              System.out.println("-----------------------------------------");
              System.out.print("Course to add: ");
              String courseAdd = scanner.nextLine();

              boolean added = sys.AddCourse(sys.loginAcctId, courseAdd); // Call Back-end to push new course
              // added will received true or false from StuSys hoặc Database (result)
              if (added){
                System.out.printf("Successfully added \'%s\'\n " , courseAdd);
              } else {
                System.out.println("Error: Unable to add course!");
              }

            } else if (subChoice == 3){
              // DROP COURSES
              System.out.println("-----------------------------------------");
              System.out.println("|             DROP COURSES              |");
              System.out.println("-----------------------------------------");
              int numCourses = sys.NumCourse(sys.loginAcctId);
              for (int i = 0; i < numCourses; i++){
                String courseName = sys.GetCourseNameAt(sys.loginAcctId,i);
                int courseGrade = sys.GetCourseGradeAt(sys.loginAcctId,i);
                System.out.printf("| %2d %-15s| %17s |\n",(i + 1),courseName,Transformer(courseGrade));
              }
              System.out.println("-----------------------------------------");
              System.out.print("Select course number to drop: ");
              int dropNum = 0;
              try {
                dropNum = scanner.nextInt();
                scanner.nextLine();
              } catch (InputMismatchException e) {
                System.out.println("Error: Invalid choice for dropped course !");
                scanner.nextLine();
                continue;
              }
              if (dropNum < 1 || dropNum > numCourses){
                System.out.println("Error: Invalid option !");
                continue;
              }
              int statusDrop = sys.DropCourse(sys.loginAcctId, dropNum - 1); // Choose the course to Drop
              // Remember - 1 because array index
              if (statusDrop == StuSys.STATUS_SUCESS){ // Drop successfully
                System.out.println("Successfully dropped the course");
              } else {
                System.out.println("Error: Invalid course selection.");
              }

            } else if (subChoice == 4){
              // EDIT COURSE GRADE
              System.out.println("-----------------------------------------");
              System.out.println("|            EDIT COURSE GRADE          |");
              System.out.println("-----------------------------------------");
              int numCourses = sys.NumCourse(sys.loginAcctId);
              System.out.println("| YOUR CURRENT COURSES:                 |");
              for (int i = 0; i < numCourses; i++){
                String courseName = sys.GetCourseNameAt(sys.loginAcctId,i); // i (pos) and follow index array
                int courseGrade = sys.GetCourseGradeAt(sys.loginAcctId,i);
                System.out.printf("| %2d %-15s| %17s |\n",(i + 1),courseName,Transformer(courseGrade));
              } // We need + 1 for i to make everything from 1
              System.out.println("-----------------------------------------");
              int courseSelected = 0;
              try {
                System.out.print("Select course number to edit: ");
                courseSelected = scanner.nextInt();
                scanner.nextLine();
              } catch (InputMismatchException e) {
                System.out.println("Error: Must enter a number! Returning to option screen...");
                scanner.nextLine();
                continue;
              }

              // Checking the course is in the range or not
              if (courseSelected < 1 || courseSelected > numCourses) {
                System.out.println("Error: Invalid course selection!");
                continue;
              }
              // Checking newGrade is from 0 to 100 or not
              int newGrade = 0;
              try {
                System.out.print("Enter the new grade: ");
                newGrade = scanner.nextInt();
                scanner.nextLine();
              } catch (InputMismatchException e) {
                System.out.println("Error: Invalide Grade !");
                scanner.nextLine();
                continue;
              }

              int statusEdit = sys.EditCourse(sys.loginAcctId, courseSelected - 1,newGrade);
              if (statusEdit == StuSys.STATUS_SUCESS)
                System.out.println("Successfully edited the course.");
              else if (statusEdit == StuSys.ERR_INVALID_GRADE)
                System.out.println("Error: Grade must be between 0 and 100.");
              else
                System.out.println("Error: Invalid Selection.");

            } else if (subChoice == 5){
              // CHANGE PASSWORD
              System.out.println("-----------------------------------------");
              System.out.println("|            CHANGE PASSWORD            |");
              System.out.println("-----------------------------------------");
              System.out.print("Enter old password  : ");
              String oldPassword = scanner.nextLine();
              System.out.print("Enter new password  : ");
              String newPassword = scanner.nextLine();
              System.out.print("Retype password     : ");
              String retypePassword = scanner.nextLine();

              int statusCPassword = sys.ChangePassword(sys.loginAcctId, oldPassword, newPassword,retypePassword);
              if (statusCPassword == StuSys.STATUS_SUCESS) // Success
                System.out.println("Sucessfully changed the account password");
              else if (statusCPassword == StuSys.ERR_ID_NOT_FOUND) // Wrong ID
                System.out.println("Error: The ID cannot find");
              else if (statusCPassword == StuSys.ERR_PASS_LENGTH) // More than or less than 5 characters
                System.out.println("Error: The password must be 5 characters");
              else if (statusCPassword == StuSys.ERR_WRONG_PASS) // old pass is wrong
                System.out.println("Error: The old password is not correctly");
              else if (statusCPassword == StuSys.ERR_PASS_MISMATCH) { // new pass and retype is not matching
                System.out.println("Error: The retype password does not match");
              }

            } else if (subChoice == 6){ // Stop the Student Screen;
              loggedIn = false;
            } else{
              System.out.println("Error: Invalid option, must be between 1 to 6 !");
            }

          }
        } else if(loginStatus == StuSys.ERR_ID_NOT_FOUND) { // Login failed by wrong ID
          System.out.println("Error: The ID cannot find");
        } else if (loginStatus == StuSys.ERR_WRONG_PASS){ // Login failed by wrong password
          System.out.println("Error: Wrong Password");

        }

      } else if (firstChoice == 3){
        System.out.println("System is shutting down.");
        oper = false;
      } else {
        System.out.println("Error: Invalid option, must be between 1 to 3 !");
      }
    }
  }
}