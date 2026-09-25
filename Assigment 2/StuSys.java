ublic class StuSys {
  // Global variables from the StuSys class. 
  // (Place your Global Constant variables here)
  
  
  // Instance Variables (DO NOT MODIFY)
  private Database db;
  String loginAcctId;             /* Keeps track of the currently logged User ID. It will be null whe
                                     no one is logged in. */
  int currNewId;                  // The next new user's ID. 
  
  //========== CONSTRUCTOR ==========//
  public StuSys() {
    
  }
  
  //========= PRIVATE METHOD =========//
  // (Write your private methods here)
  
  
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
    return 0;  // Dummy return value
  }
  
  /* Login a user with the given student ID and password
   * @param id   - The account ID
   * @param pass - The account password 
   * @return     - Returns  1, if login successfully - the ID and password both 
   *                           matches the info in the database.
   *               Returns -1, if account ID not found in the database.
   *               Returns -2, if password associated with the ID is not correct. */
  public int Login(String id, String pass) {
    return 0;  // Dummy return value
  }
  
  /* Logout the currently logged in student from the system. */
  public void Logout() {
    
  }
  
  /* Get the student's name with the given account ID from 
   * the database. 
   * @param id - The account ID
   * @return   - Returns The student name of the account if the ID is found.
   *             Returns null if the ID is not found. */
  public String GetStudentName(String id) {
    return null;  // Dummy return value
  }
  
  /* Get the number of courses the specified account has 
   * @param id - The account ID
   * @return   - Returns the number of courses the account has. 
   *             Returns -1, if account ID not found in the database. */     
  public int NumCourse(String id) {
    return 0;  // Dummy return value
  }   
  
  /* Get the course's name stored at the specified position in the database
   * @param id  - The account ID
   * @param pos - The pos where the course is stored in the database. 
   *              First course is at pos 0, second is at pos 1, and etc.
   * @return    - Returns the name of the course at pos.
   *              Returns null if account ID not found or course pos is out of range. */   
  public String GetCourseNameAt(String id, int pos) {
    return null;  // Dummy return value
  }
  
  /* Get the course's grade stored at the specified position in the database
   * @param id  - The account ID
   * @param pos - The pos where the course is stored in the database. 
   *              First course is at pos 0, second is at pos 1, etc.
   * @return    - Returns the grade of the course at pos.
   *              Returns -1, if account ID not found in the database.
   *              Returns -5, if pos specified is beyond the range of number of courses */
  public int GetCourseGradeAt(String id, int pos) {
    return 0;  // Dummy return value
  }
  
  /* Get the student's GPA, the overall average of all completed courses.
   * @param id - The account ID
   * @return   - Returns the GPA of the student. 
   *             Returns -1, if account ID not found in the database. 
   *             Returns -8, if there's no courses completed */
  public double GetGPA(String id) {
    return 0;  // Dummy return value
  }
  
  /* Add a course to the account
   * @param id         - The account ID
   * @param courseName - The course name.
   * @return           - Returns TRUE if successful added the course 
   *                     Returns FALSE if account ID is not found. */
  public boolean AddCourse( String id, String courseName ) {
    return true;  // Dummy return value
  }      
  
  /* Drop a course in an account 
   * @param id  - The account ID
   * @param pos - The pos where the course is stored in the database. 
   *              First course is at pos 0, second is at pos 1, etc. 
   * @return    - Returns  1, if successfully dropped the course. 
   *              Returns -1, if account ID not found in the database
   *              Returns -5, if pos specified is beyond the range of number of courses. */
  public int DropCourse( String id, int pos ) {
    return 0;  // Dummy return value
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
    return 0;  // Dummy return value
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
    return 0;  // Dummy return value
  }   
  
  /* PROVIDED METHOD
   * For testing purposes. You may use this method to 
   * see what is inside the database. */
  public void DisplayDatabase() {
    db.DisplayDatabase();
  }
}