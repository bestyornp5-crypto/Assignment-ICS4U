import java.util.Calendar;

public class History extends Book {
  // Priavte asstribute of history beacause history has Before Common Era
  private boolean isBCE;
  //=============== Constructor =================
  public History(int bookId, String title, int numPages, boolean isBCE) {
    super(bookId,title,numPages);
    this.isBCE = isBCE;
  }
  
  //============= Private Methods ===============
  /* (Put your private methods here) */   
  
  
  //============= Public Methods ===============


  @Override
  public boolean Borrow(String borrowerName, int numDays) {
    int maxDays = 0;
    if (this.isBCE == true){
      // History of BCE --> can borrow <= 14 days
      maxDays = 14;
    }
    else{
      // History of not BCE --> only <= 7 days
      maxDays = 7;
    }
    if (numDays < 1 || numDays > maxDays){
      // Check total to borrow , the student want is in the limitation of not
      return false;
    } else {
      // Set up the information of user
      SetBorrower(borrowerName);

      // Set the dueDate for borrower
      Calendar calendar = Calendar.getInstance();
      calendar.add(Calendar.DAY_OF_MONTH, numDays);
      SetDueDate(calendar.getTime());
      return true;
    }
  }

  @Override
  public String ExtraInfo() {
    return String.valueOf(this.isBCE);
  }
}