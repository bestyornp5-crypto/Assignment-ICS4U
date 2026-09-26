public class StuSys {

  // Global variables from the StuSys class. 
  // (Place your Global Constant variables here)
  //--> This Group configuration
  public static final int  STARTING_ID = 10000;
  public static final int  PASS_LENGTH = 5;
  // Require PASSWORD MUST BE Greater than 5

  //--> Group for subjects
  public static final int  GRADE_TBD = -100;
  public static final int  GRADE_DROPPED = - 200;

  // --> Group uses for method
  // SUCESSFUL GROUP
  public static final int STATUS_SUCESS = 1 ;

  // FAILED GROUPED
  public static final int  ERR_ID_NOT_FOUND = -1;
  public static final int  ERR_WRONG_PASS = -2;
  public static final int  ERR_PASS_MISMATCH =  -3;
  public static final int  ERR_DB_FULL = -4;
  public static final int  ERR_INVALID_POS = -5;
  public static final int  ERR_INVALID_GRADE = -6;
  public static final int  ERR_PASS_LENGTH = -7;
  public static final int  ERROR_NO_COMPLETED_COURSE = -8;


  
  // Instance Variables (DO NOT MODIFY)
  private Database db;
  String loginAcctId;             /* Keeps track of the currently logged User ID. It will be null whe
                                     no one is logged in. */
  int currNewId;                  // The next new user's ID. 
  
  //========== CONSTRUCTOR ==========//
  public StuSys() {
    db = new Database();
    currNewId = STARTING_ID;
    loginAcctId =  null;

    
  }
  
  //========= PRIVATE METHOD =========//
  // (Write your private methods here)
  private String BoundExceptionChecking(String id, int pos){
    String courseStr = db.GetAllCourseInfo(id);
    if (courseStr == null)
      return null;
    String[] courses = courseStr.split(":");
    if ( pos < 0 || pos >= courses.length)
      return null;
    return courses[pos];
  } // This method is using for helping checking GetCourseInfo at specific location
  
  //========= PUBLIC METHOD =========//
  /* PROVIDED METHOD 
   * Used to import dummy data into the database, so there will 
   * be pre-existing accounts in the database to be used for testing.
   * @param filename - The filename of the textfile that contains all 
   *                   dummy data. */
  public void ImportAcct( String filename ) {

    currNewId += db.ImportAcct( filename, currNewId );

  }
  
  /* Create a new account with given info. It will also info checking
   * @param name       - The name for the account 
   * @param pass       - The password for the account
   * @param retypePass - The retype password 
   * @return           - The status of the account creation process 
   *                     Returns  1, if account is created successfully 
   *                     Returns -3, if password and retype password don't match 
   *                     Returns -4, if number of accounts have reached its limit
   *                     Returns -7, if password is not 5 characters. */
  public int CreateNewAcct(String name, String pass, String retypePass ) {
    if (pass.length() != PASS_LENGTH) {
      return ERR_PASS_LENGTH;
    }
    if(!pass.equals(retypePass)){
      return ERR_PASS_MISMATCH;
    }
    boolean created = db.AddAcct(String.valueOf(currNewId),name,pass);
    if (!created){
      return ERR_DB_FULL;
    }else {
      currNewId++;
      return STATUS_SUCESS;
    } // Dummy return value
  }
  
  /* Login a user with the given student ID and password
   * @param id   - The account ID
   * @param pass - The account password 
   * @return     - Returns  1, if login successfully - the ID and password both 
   *                           matches the info in the database.
   *               Returns -1, if account ID not found in the database.
   *               Returns -2, if password associated with the ID is not correct. */
  public int Login(String id, String pass) {
      // Dummy return value
    if (!db.IsAcctExist(id)){
      return ERR_ID_NOT_FOUND;
    }
    String acctualPass = db.GetAcctPass(id);
    if(acctualPass != pass){
      return ERR_WRONG_PASS;
    }
    loginAcctId = id; // this is id will use until user want to log out
    return STATUS_SUCESS;

  }
  
  /* Logout the currently logged in student from the system. */
  public void Logout() {
    loginAcctId = null;

  }
  
  /* Get the student's name with the given account ID from 
   * the database. 
   * @param id - The account ID
   * @return   - Returns The student name of the account if the ID is found.
   *             Returns null if the ID is not found. */
  public String GetStudentName(String id) {
    return db.GetAcctName(id);  // Dummy return value
  }
  
  /* Get the number of courses the specified account has 
   * @param id - The account ID
   * @return   - Returns the number of courses the account has. 
   *             Returns -1, if account ID not found in the database. */     
  public int NumCourse(String id) {
    if (!db.IsAcctExist(id)) {
      return ERR_ID_NOT_FOUND;
    }
    String courseInfo = db.GetAllCourseInfo(id);
    if (courseInfo == null || courseInfo.trim().isEmpty()) {
      return 0;

    }
    String[] courses = courseInfo.split(":");
    return courses.length;
  }
  
  /* Get the course's name stored at the specified position in the database
   * @param id  - The account ID
   * @param pos - The pos where the course is stored in the database. 
   *              First course is at pos 0, second is at pos 1, and etc.
   * @return    - Returns the name of the course at pos.
   *              Returns null if account ID not found or course pos is out of range. */   
  public String GetCourseNameAt(String id, int pos) {
    if (!db.IsAcctExist(id)) {
      return null;  // Dummy return value
    }
    String Cinformation = BoundExceptionChecking(id, pos);
    if (Cinformation == null) return null;

    String[] partsCourseInfo = Cinformation.split("_");
    return partsCourseInfo[0];
  }
  
  /* Get the course's grade stored at the specified position in the database
   * @param id  - The account ID
   * @param pos - The pos where the course is stored in the database. 
   *              First course is at pos 0, second is at pos 1, etc.
   * @return    - Returns the grade of the course at pos.
   *              Returns -1, if account ID not found in the database.
   *              Returns -5, if pos specified is beyond the range of number of courses */
  public int GetCourseGradeAt(String id, int pos) {
    if(!db.IsAcctExist(id)){
      return ERR_ID_NOT_FOUND;
    }
    int totalCourses = NumCourse(id);
    if (pos < 0 || pos >= totalCourses){
      return ERR_INVALID_POS;
    }

    String  courseInfo = BoundExceptionChecking(id, pos);

    if (courseInfo == null)
      return ERR_INVALID_POS;
    String[] parts = courseInfo.split("_");

    if (parts.length <= Database.ARRAY_COURSE_GRADE_POS || parts[Database.ARRAY_COURSE_GRADE_POS].isEmpty()){
      return GRADE_TBD;
    }
    try {
      return Integer.parseInt(parts[1]); // Solved for Ex: "96"
    }
    catch(NumberFormatException e){
      return GRADE_TBD;
    }

  }
  
  /* Get the student's GPA, the overall average of all completed courses.
   * @param id - The account ID
   * @return   - Returns the GPA of the student. 
   *             Returns -1, if account ID not found in the database. 
   *             Returns -8, if there's no courses completed */
  public double GetGPA(String id) {
      if(!db.IsAcctExist(id))
        return ERR_ID_NOT_FOUND;
      int totalCourse = NumCourse(id);
      if (totalCourse < 0)
        return ERROR_NO_COMPLETED_COURSE;
      double sum = 0;
      int count = 0; // We dont know how many course completed , so cannot take totalCourse to use
      for(int i = 0; i < totalCourse; i++){
        int grade = GetCourseGradeAt(id,i);
        if (grade >= 0 || grade <= 100){
          sum += grade;
          count++;
        }
      }
      if (count == 0){
        return ERROR_NO_COMPLETED_COURSE;
      }
      return sum/count;
  }
  
  /* Add a course to the account
   * @param id         - The account ID
   * @param courseName - The course name.
   * @return           - Returns TRUE if successful added the course 
   *                     Returns FALSE if account ID is not found. */
  public boolean AddCourse( String id, String courseName ) {
    if (!db.IsAcctExist(id))
      return false;
    return db.AddCourse(id,courseName);
  }      
  
  /* Drop a course in an account 
   * @param id  - The account ID
   * @param pos - The pos where the course is stored in the database. 
   *              First course is at pos 0, second is at pos 1, etc. 
   * @return    - Returns  1, if successfully dropped the course. 
   *              Returns -1, if account ID not found in the database
   *              Returns -5, if pos specified is beyond the range of number of courses. */
  public int DropCourse( String id, int pos ) {
    if (!db.IsAcctExist(id))
      return ERR_ID_NOT_FOUND;
    int totalCourse = NumCourse(id);
    if (pos >= totalCourse || pos < 0)
      return ERR_INVALID_POS;
    boolean yah = db.UpdateCourseGradeAt(id, pos, GRADE_DROPPED);
    return yah ? STATUS_SUCESS : ERR_INVALID_POS;
    // If 1 return yah, if not return -5

  }   
  
  /* Edit a course's grade in an account 
   * @param id      - The account ID
   * @param pos     - The position of the course that is in the database. 
   *                  First course is at pos 0, second is at pos 1, etc. 
   * @param grade   - The new grade for the course
   * @return        - Returns  1, if successfully edited the course 
   *                  Returns -1, if account ID not found in the database
   *                  Returns -5, if pos specified is beyond the range of number of courses
   *                  Returns -6, if grade is not valid (not between 0-100) */   
  public int EditCourse( String id, int pos, int grade ) {
    if (!db.IsAcctExist(id))
      return ERR_ID_NOT_FOUND;
    int totalCourse = NumCourse(id);
    if (pos >= totalCourse || pos < 0)
      return ERR_INVALID_POS;
    if (grade < 0 || grade > 100)
      return ERR_INVALID_GRADE;
    boolean yah = db.UpdateCourseGradeAt(id, pos, grade);
    return yah ? STATUS_SUCESS : ERR_INVALID_POS;
    // If 1 return yah, if not return -5

  }
  
  /* Change the account's password
   * @param id   - The account ID
   * @param pass - The new password for the account
   * @return     - Returns  1, if successfully changes the password.
   *               Returns -1, if account ID not found in the database.
   *               Returns -2, if password is incorrect.
   *               Returns -3, if password and retype password don't match 
   *               Returns -7, if new password is not 5 characters. */
  public int ChangePassword( String id, String oldPass, String newPass, String retypePass ) {
    if (!db.IsAcctExist(id))
      return ERR_ID_NOT_FOUND;
    String currentPass =db.GetAcctPass(id);
    if (!currentPass.equals(oldPass))
      return ERR_WRONG_PASS;
    if (newPass.length() != PASS_LENGTH)
      return ERR_WRONG_PASS;
    if (!newPass.equals(retypePass))
      return ERR_PASS_MISMATCH;

    boolean yahPass = db.UpdateAcctPass(id,newPass);
    return yahPass ? STATUS_SUCESS : ERR_ID_NOT_FOUND;
  }   
  
  /* PROVIDED METHOD
   * For testing purposes. You may use this method to 
   * see what is inside the database. */
  public void DisplayDatabase() {
    db.DisplayDatabase();
  }
}