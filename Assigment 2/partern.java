public class StuSys {
  // Global Constant variables (ít nhất 5 hằng số theo yêu cầu)
  public static final int STARTING_ID = 10000;
  public static final int PASS_LENGTH = 5;
  public static final int GRADE_TBD = -100;
  public static final int GRADE_DROPPED = -200;
  
  public static final int STATUS_SUCCESS = 1;
  public static final int ERR_ID_NOT_FOUND = -1;
  public static final int ERR_WRONG_PASS = -2;
  public static final int ERR_PASS_MISMATCH = -3;
  public static final int ERR_DB_FULL = -4;
  public static final int ERR_INVALID_POS = -5;
  public static final int ERR_INVALID_GRADE = -6;
  public static final int ERR_PASS_LENGTH = -7;
  public static final int ERR_NO_COMPLETED_COURSE = -8;
  
  // Instance Variables (DO NOT MODIFY)
  private Database db;
  String loginAcctId; /* Keeps track of the currently logged User ID. It will be null when no one is logged in. */
  int currNewId;      // The next new user's ID.
  
  //========== CONSTRUCTOR ==========//
  public StuSys() {
    db = new Database();
    loginAcctId = null;
    currNewId = STARTING_ID;
  }
  
  //========= PRIVATE HELPER METHODS =========//
  // Hàm bổ trợ lấy thông tin môn học ở vị trí pos
  private String getSingleCourseInfoAt(String id, int pos) {
    String courseStr = db.GetAllCourseInfo(id);
    if (courseStr == null || courseStr.trim().isEmpty()) {
      return null;
    }
    String[] courses = courseStr.split(":");
    if (pos < 0 || pos >= courses.length) {
      return null;
    }
    return courses[pos];
  }
  
  //========= PUBLIC METHODS =========//
  public void ImportAcct(String filename) {
    currNewId += db.ImportAcct(filename, currNewId);
  }
  
  public int CreateNewAcct(String name, String pass, String retypePass) {
    if (pass.length() != PASS_LENGTH) {
      return ERR_PASS_LENGTH;
    }
    if (!pass.equals(retypePass)) {
      return ERR_PASS_MISMATCH;
    }
    boolean created = db.AddAcct(String.valueOf(currNewId), name, pass);
    if (!created) {
      return ERR_DB_FULL;
    }
    currNewId++;
    return STATUS_SUCCESS;
  }
  
  public int Login(String id, String pass) {
    if (!db.IsAcctExist(id)) {
      return ERR_ID_NOT_FOUND;
    }
    String actualPass = db.GetAcctPass(id);
    if (!actualPass.equals(pass)) {
      return ERR_WRONG_PASS;
    }
    loginAcctId = id;
    return STATUS_SUCCESS;
  }
  
  public void Logout() {
    loginAcctId = null;
  }
  
  public String GetStudentName(String id) {
    return db.GetAcctName(id);
  }
  
  public int NumCourse(String id) {
    if (!db.IsAcctExist(id)) {
      return ERR_ID_NOT_FOUND;
    }
    String courseStr = db.GetAllCourseInfo(id);
    if (courseStr == null || courseStr.trim().isEmpty()) {
      return 0;
    }
    String[] courses = courseStr.split(":");
    return courses.length;
  }
  
  public String GetCourseNameAt(String id, int pos) {
    if (!db.IsAcctExist(id)) return null;
    String courseInfo = getSingleCourseInfoAt(id, pos);
    if (courseInfo == null) return null;
    
    String[] parts = courseInfo.split("_");
    return parts[Database.ARRAY_COURSE_NAME_POS];
  }
  
  public int GetCourseGradeAt(String id, int pos) {
    if (!db.IsAcctExist(id)) {
      return ERR_ID_NOT_FOUND;
    }
    int totalCourses = NumCourse(id);
    if (pos < 0 || pos >= totalCourses) {
      return ERR_INVALID_POS;
    }
    
    String courseInfo = getSingleCourseInfoAt(id, pos);
    if (courseInfo == null) return ERR_INVALID_POS;
    
    String[] parts = courseInfo.split("_");
    if (parts.length <= Database.ARRAY_COURSE_GRADE_POS || parts[Database.ARRAY_COURSE_GRADE_POS].isEmpty()) {
      return GRADE_TBD;
    }
    
    try {
      return Integer.parseInt(parts[Database.ARRAY_COURSE_GRADE_POS]);
    } catch (NumberFormatException e) {
      return GRADE_TBD;
    }
  }
  
  public double GetGPA(String id) {
    if (!db.IsAcctExist(id)) {
      return ERR_ID_NOT_FOUND;
    }
    int totalCourses = NumCourse(id);
    if (totalCourses <= 0) {
      return ERR_NO_COMPLETED_COURSE;
    }
    
    double sum = 0;
    int count = 0;
    for (int i = 0; i < totalCourses; i++) {
      int grade = GetCourseGradeAt(id, i);
      // Chỉ tính GPA các môn có điểm hợp lệ (không phải TBD -100 hay DROPPED -200)
      if (grade >= 0 && grade <= 100) {
        sum += grade;
        count++;
      }
    }
    
    if (count == 0) {
      return ERR_NO_COMPLETED_COURSE;
    }
    return sum / count;
  }
  
  public boolean AddCourse(String id, String courseName) {
    if (!db.IsAcctExist(id)) {
      return false;
    }
    return db.AddCourse(id, courseName);
  }
  
  public int DropCourse(String id, int pos) {
    if (!db.IsAcctExist(id)) {
      return ERR_ID_NOT_FOUND;
    }
    int totalCourses = NumCourse(id);
    if (pos < 0 || pos >= totalCourses) {
      return ERR_INVALID_POS;
    }
    
    boolean ok = db.UpdateCourseGradeAt(id, pos, GRADE_DROPPED);
    return ok ? STATUS_SUCCESS : ERR_INVALID_POS;
  }
  
  public int EditCourse(String id, int pos, int grade) {
    if (!db.IsAcctExist(id)) {
      return ERR_ID_NOT_FOUND;
    }
    int totalCourses = NumCourse(id);
    if (pos < 0 || pos >= totalCourses) {
      return ERR_INVALID_POS;
    }
    if (grade < 0 || grade > 100) {
      return ERR_INVALID_GRADE;
    }
    
    boolean ok = db.UpdateCourseGradeAt(id, pos, grade);
    return ok ? STATUS_SUCCESS : ERR_INVALID_POS;
  }
  
  public int ChangePassword(String id, String oldPass, String newPass, String retypePass) {
    if (!db.IsAcctExist(id)) {
      return ERR_ID_NOT_FOUND;
    }
    String currentPass = db.GetAcctPass(id);
    if (!currentPass.equals(oldPass)) {
      return ERR_WRONG_PASS;
    }
    if (newPass.length() != PASS_LENGTH) {
      return ERR_PASS_LENGTH;
    }
    if (!newPass.equals(retypePass)) {
      return ERR_PASS_MISMATCH;
    }
    
    boolean ok = db.UpdateAcctPass(id, newPass);
    return ok ? STATUS_SUCCESS : ERR_ID_NOT_FOUND;
  }
  
  public void DisplayDatabase() {
    db.DisplayDatabase();
  }
}