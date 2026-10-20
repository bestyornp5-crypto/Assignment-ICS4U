import java.util.Calendar;

public class NonFiction extends Book {
  private String genre;
  
  //=============== Constructor =================
  public NonFiction(int bookId, String title, int numPages, String genre) {
    super(bookId,title,numPages);
    this.genre = genre;
    
  }
  
  //============= Private Methods ===============
  /* (Put your private methods here) */    
  
  
  //============= Public Methods ===============
  public boolean Borrow(String borrowerName, int numDays){
    int maxDays = 0;
    if (this.GetNumPages() < 500){
      maxDays = 15;
    } else {
      maxDays = 25;
    }
    if (numDays < 1 || numDays > maxDays){
      return false;
    } else{
      SetBorrower(borrowerName);

      Calendar calendar = Calendar.getInstance();
      calendar.add(Calendar.DAY_OF_MONTH,numDays);
      SetDueDate(calendar.getTime());
      return true;
    }
  }

  public String ExtraInfo(){
    return this.genre;
  }
  
}