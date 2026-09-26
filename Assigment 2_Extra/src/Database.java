/* PROVIDED DATABASE CLASS - DO NO MODIFY.
 * Version     : 1.1
 * Revised Date: Sept 08, 2026 */
import java.util.Scanner;
import java.io.File;
import java.io.IOException;

public class Database {
  // Global variables to be used throughout the program
  public final static int DEFAULT_NUM_ACCOUNT       = 100;  // Default maximum account the database can store.

  public final static int ARRAY_ID_POS              =   0;
  public final static int ARRAY_NAME_POS            =   1;
  public final static int ARRAY_PASS_POS            =   2;
  public final static int ARRAY_START_OF_COURSE_POS =   3;

  public final static int ARRAY_COURSE_NAME_POS     =   0;
  public final static int ARRAY_COURSE_GRADE_POS    =   1;

  private String[] allAcct;   // The array that stores all the student's information
  private int numAcct;        // Keeps track of how many accounts are currently in the database

  //=================== CONSTRUCTOR ===================//
  /* The default constructor. Initializes the database. */
  public Database() {
    allAcct = new String[DEFAULT_NUM_ACCOUNT];
    numAcct = 0;
  }

  /* The constructor that allows the caller to
   * specify the maximum number of accounts it
   * can store
   * @param maxNumAcct - The maximum number of accounts the
   *                     database can store */
  public Database( int maxNumAcct ) {
    allAcct = new String[ maxNumAcct ];
    numAcct = 0;
  }

  //=================== PUBLIC METHODS ===================//
  /* Used for testing purposes: Import account information from a textfile
   * @param filename  - The txt file that contains the data. Make sure the txt file is in the
   *                    same folder as the program. The currNewId is the 1st ID that the program
   *                    will assign to the newly imported account. The subsequent accounts and
   *                    their ID will automatically increment as it is importing.
   * @param currNewId - The account ID that the imported accounts will start with
   * @return          - The number of accounts that has been added to the
   *                    database */
  public int ImportAcct( String filename, int currNewId ) {
    int numAcctAdded = 0;
    try {
      // pass the path to the file as a parameter 
      File file = new File(filename);
      Scanner sc = new Scanner(file);
      String infoStr;

      while (sc.hasNextLine()) {
        infoStr = sc.nextLine();
        if( !infoStr.equals("") ) {
          allAcct[numAcct] = currNewId + ":" + infoStr;
          numAcct++;
          currNewId++;
          numAcctAdded++;
        }
      }

      sc.close();
    }
    catch (IOException ex) {
      System.out.println("Import Error: Unable to complete the task");
    }

    return numAcctAdded;
  }

  /* Create and add a new account into the database
   * @param currNewId - The account ID that the new account will use
   * @param name      - The name of the account
   * @param pass      - The password of the account
   * @return          - The account creation status.
   *                    Returns TRUE if the account is created successful
   *                    Returns FALSE if the database has reached max account */
  public boolean AddAcct(String currNewId, String name, String pass) {
    if( numAcct < allAcct.length ) {
      allAcct[numAcct] = currNewId + ":" + name + ":" + pass;
      numAcct++;
      return true;
    }
    else {
      return false;
    }
  }

  /* Checks if the database has the specified account ID
   * @param id - The account ID
   * @return   - The status of the search.
   *             Returns TRUE, if it is found
   *             Returns FALSE, otherwise */
  public boolean IsAcctExist(String id) {
    boolean result = false;
    for( int i = 0; i < numAcct; i++ ) {
      String acctInfoStr = allAcct[i];
      String[] acctInfoArr = acctInfoStr.split(":");

      if( acctInfoArr[ARRAY_ID_POS].equals(id) ) {
        // When the ID is found in the system
        result = true;
        break;
      }
    }
    return result;
  }

  /* Search for the account with the specified ID, and returns the account name
   * @param id - The account ID
   * @return   - Returns the account name, if it is found.
   *             Returns null otherwise. */
  public String GetAcctName( String id ) {
    String name = null;
    for( int i = 0; i < numAcct; i++ ) {
      String acctInfoStr = allAcct[i];
      String[] acctInfoArr = acctInfoStr.split(":");

      if( acctInfoArr[ARRAY_ID_POS].equals(id) ) {
        // When the ID is found in the system 
        name = acctInfoArr[ARRAY_NAME_POS];
        break;
      }
    }
    return name;
  }

  /* Search for the account with the ID, and get the account password
   * @param id - The account ID
   * @return   - Returns the account password, if it's found.
   *             Returns null, otherwise. */
  public String GetAcctPass( String id ) {
    String pass = null;
    for( int i = 0; i < numAcct; i++ ) {
      String acctInfoStr = allAcct[i];
      String[] acctInfoArr = acctInfoStr.split(":");

      if( acctInfoArr[ARRAY_ID_POS].equals(id) ) {
        // When the ID is found in the system
        pass = acctInfoArr[ARRAY_PASS_POS];
        break;
      }
    }
    return pass;
  }

  /* Update the password with a new password in an account
   * @param id   - The account ID
   * @param pass - The new password to be used
   * @return     - Returns TRUE, if successfully updated the password.
   *               Returns FALSE, if ID is not found in the database. */
  public boolean UpdateAcctPass( String id, String pass ) {
    boolean result = false;
    String newAcctInfo = "";
    for( int i = 0; i < numAcct; i++ ) {
      String acctInfoStr = allAcct[i];
      String[] acctInfoArr = acctInfoStr.split(":");

      if( acctInfoArr[ARRAY_ID_POS].equals(id) ) {
        // When the ID is found in the system
        for( int j = 0; j < acctInfoArr.length; j++ ) {
          if( j == 0 ) {
            newAcctInfo += acctInfoArr[j];
          }
          else if( j == ARRAY_PASS_POS ) {
            newAcctInfo += ":" + pass;
          }
          else {
            newAcctInfo += ":" + acctInfoArr[j];
          }
        }
        allAcct[i] = newAcctInfo;
        result = true;
        break;
      }
    }
    return result;
  }

  /* Get all the course information of an account represented by a String
   * @param id - The account ID
   * @return   - Returns the string that contains all the account's course information, in the format:
   *                     "[CourseCode1_Grade1]:[CourseCode2_Grade2]: ... :[CourseCodeN_GradeN]"
   *             Return null, if there's no courses or if ID doesn't exist */
  public String GetAllCourseInfo( String id ) {
    String courseFullInfo = null;

    for( int i = 0; i < numAcct; i++ ) {
      String acctInfoStr = allAcct[i];
      String[] acctInfoArr = acctInfoStr.split(":", 4);
      if( acctInfoArr.length != 3 && acctInfoArr[ARRAY_ID_POS].equals(id) ) {
        courseFullInfo = acctInfoArr[ARRAY_START_OF_COURSE_POS];
        break;
      }
    }
    return courseFullInfo;
  }

  /* Add a course to the account with the given ID. The course will
   * not have any grade after adding it in to the database.
   * @param id         - The account ID
   * @param courseName - The course code (Example: ICS4U)
   * @return           - Returns TRUE if successful added the course
   *                     Returns FALSE if ID is not found. */
  public boolean AddCourse( String id, String courseName ) {
    boolean result = false;
    for( int i = 0; i < numAcct; i++ ) {
      String acctInfoStr = allAcct[i];
      String[] acctInfoArr = acctInfoStr.split(":");
      if( acctInfoArr[Database.ARRAY_ID_POS].equals(id) ) {
        allAcct[i] += ":" + courseName + "_";
        result = true;
        break;
      }
    }
    return result;
  }

  /* Update the course grade at a specified position in the database
   * inside an account
   * @param id      - The account ID
   * @param pos     - The position of the course in the database that the grade needs to be changed
   *                  if pos = 0, then it is the first course from the specified account
   *                  if pos = 1, then it is the second course from the specified account
   *                  ...
   *                  if pos = n, then it is the (n+1)th course from the specified account
   * @param grade   - The new grade for the specified course
   * @return        - Returns TRUE, if successfully updated the course grade.
   *                  Returns FALSE, if ID is not found. */
  public boolean UpdateCourseGradeAt( String id, int pos, int grade) {
    boolean result = false;
    for( int i = 0; i < numAcct; i++ ) {
      String acctInfoStr = allAcct[i];
      String[] acctInfoArr = acctInfoStr.split(":");
      if( acctInfoArr[Database.ARRAY_ID_POS].equals(id) ) {
        // Found the account
        String[] singleCourseInfoArray = acctInfoArr[ARRAY_START_OF_COURSE_POS + pos].split("_");
        String newAcctInfo = "";

        // Generate the new acctInfo string
        for( int j = 0; j < acctInfoArr.length; j++ ) {
          if( j == 0 ) {
            newAcctInfo += acctInfoArr[j];
          }
          else if( j == (ARRAY_START_OF_COURSE_POS + pos) ) {
            newAcctInfo += ":" + singleCourseInfoArray[0] + "_" + grade;
          }
          else {
            newAcctInfo += ":" + acctInfoArr[j];
          }
        }
        allAcct[i] = newAcctInfo;

        result = true;
        break;
      }
    }
    return result;
  }

  /* For testing purposes. You may use this method to
   * see what is inside the database. */
  public void DisplayDatabase() {
    for( int i = 0; i < numAcct; i++ ) {
      if( allAcct[i] != null ) {
        System.out.println( allAcct[i] );
      }
      else {
        break;
      }
    }
  }
}