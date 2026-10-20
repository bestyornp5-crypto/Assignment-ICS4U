import java.util.Calendar;

public class Novel extends Book {
  // We have a private attribution
  private int level;


  //=============== Constructor =================
  public Novel(int bookId, String title, int numPages, int level) {
    super(bookId, title, numPages);
    this.level = level;
    
  }
  
  //============= Private Methods ===============
  /* (Put your private methods here) */   
  
  
  //============= Public Methods ===============


  @Override
  public boolean Borrow(String borrowerName, int numDays) {
    double page = (double)this.GetNumPages();
    double doubleDays = ((5/this.level) * Math.pow(2.0,page/195.0));
    int maxDays = (int)(doubleDays + 0.999999999999999999999);

    if (numDays < 1 || numDays > maxDays){
      return false;
    } else {
      SetBorrower(borrowerName);

      Calendar calendar = Calendar.getInstance();
      calendar.add(Calendar.DAY_OF_MONTH,numDays);
      SetDueDate(calendar.getTime());
      return true;
    }
  }

  public String ExtraInfo(){
    return String.valueOf(this.level);
  }


}