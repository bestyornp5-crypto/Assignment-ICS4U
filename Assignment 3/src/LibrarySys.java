public class LibrarySys {
  // (Put your Global Variables here)
  // These are the constant variable to check the error or successfully
  final static int SUCCESS = 1 ;
  final static int INVALID_BOOKTYPE = 0 ;
  final static int INVALID_NUMPAGES= -1 ;
  final static int INVALID_EXTRA_INFO= -2;
  final static int INVALID_ID= -3;
  final static int INVALID_BORROW_DAY= -4 ;
  final static int BORROWED= -5;
  final static int NEVER_BOR = -6;

  // Why ?
  /* Returns 1 if the book is successfully added.
   * Returns 0 if the bookType value is incorrect.
   * Returns -1 if page number is invalid (Less than 1).
   * Returns -2 if ExtraInfo is invalid with the specified book type.
   * Returns -3 if the book ID doesn't exist in the system.
   * Returns -4 if the number of days doesn't satisfy the book borrowing rules.
   * Returns -5 if someone else borrowed the book already.
   * Returns -6 if the book was never borrowed.
    */
  
  private BookDatabase bookDb;      // BookShelfDB object (DO NOT MODIFY)   
  
  //=============== Constructor =================
  public LibrarySys() {
    this.bookDb = new BookDatabase();
  }
  
  //============= Private Methods ===============
  // (Put your Private methods here)

  private boolean checkExtraInfo(int bookType,String extraInfo){
    if (extraInfo == null){
      return false;
    }
    if (bookType == Book.HISTORY){
      // This is the book of history but the attribute or the extra infor of novel is will be wrong
      String getExtraInfo = extraInfo.toLowerCase();
      // Both Y/N y/n true false True False will be accepted
      if( getExtraInfo.equals("true") || getExtraInfo.equals("false") || getExtraInfo.equals("y") || getExtraInfo.equals("n")) {
        // It will return True the extraInfo is correct require of history book
        return true;
      }

      }
    else if (bookType == Book.NON_FICTION){
      // No restriction
      // -> But can be Empty() of null
      if (extraInfo.equals("") || extraInfo == null){
        return false;
      }
      return true;
    }
    else if (bookType == Book.NOVEL){
      if (extraInfo.equals("1") || extraInfo.equals("2") || extraInfo.equals("3")){
        return true;
      } else {
        return false;
      }
    }
    return false; // It is not Novel not History not Non_Fiction
    // Maybe user inputed funny number or funny things

  }
  private Book bookWhere(int bookId) {
    for ( int i = 0; i < bookDb.NumBooks(); i++){
      // I will loops through the books are in the Library System first
      // Take it and compare this book is null or not and  compare the id to make sure this book is exits
      Book book = bookDb.GetBookAt(i);
      if (book != null && book.GetBookId() ==  bookId){
        // Because in the BookDB class it will return null or book
        // So I will check that the book is returned is null or real book
        return book;
      }
    }
    return null; // This book doesnt exist or exist
  }
  
  
  //============= Public Methods ===============
  /* Import books from textfile to the BookDatabase object.
   * When importing, the program expects the data and format are all correct.
   * @param filename - The textfile containing all the book info.
   * @return         - The number of books added */
  public int ImportBooks(String filename) {
    return this.bookDb.AddBook(filename);   // Dummy return value
  }
  
  /* To add an individual book.
   * @param bookType    - The booktype that you are adding (refer back to the Book class for the value of each type
   * @param title       - The title of the book
   * @param numPages    - Number of pages for the book to be added
   * @param extraInfo   - Special information. Speciific to book type. Refer to
   *                      the Assignment PDF.
   * @return            - Returns 1 if the book is successfully added.
   *                      Returns 0 if the bookType value is incorrect.
   *                      Returns -1 if page number is invalid (Less than 1).
   *                      Returns -2 if ExtraInfo is invalid with the specified book type. 
   *                                 Refer to each book type's ExtraInfo to determine 
   *                                 what values are invalid. */
  public int AddBook(int bookType, String title, int numPages, String extraInfo) {
    if (bookType != Book.HISTORY && bookType != Book.NOVEL && bookType != Book.NON_FICTION) {   // Dummy return value
      return INVALID_BOOKTYPE; //Returns 0 if the bookType value is incorrect.
    }
    if (numPages < 1) {
      return INVALID_NUMPAGES; //Returns -1 if page number is invalid (Less than 1).
    }

    if (!checkExtraInfo(bookType, extraInfo)) {
      // This method is in private above
      return INVALID_EXTRA_INFO;
      //returns -2 if ExtraInfo is invalid with the specified book type.
      //   *                                 Refer to each book type's ExtraInfo to determine
      //   *                                 what values are invalid. */
    }
    // If extraInfo is correct --> put to the system
    int newBookId = this.bookDb.GenNewBookId();
    Book newBook = null;

    if (bookType == Book.HISTORY) {
      String getInfo = extraInfo.toLowerCase();
      boolean isBCE = getInfo.equals("true") || getInfo.equals("y");
      newBook = new History(newBookId, title, numPages, isBCE);

    } else if(bookType == Book.NON_FICTION){
      newBook = new NonFiction(newBookId,title,numPages,extraInfo);
    } else if(bookType == Book.NOVEL){
      int level = Integer.parseInt(extraInfo);
      newBook = new Novel(newBookId, title, numPages, level);
    }
    return this.bookDb.AddBook(newBook);
  }
  
  /* Displays all the books.
   * Note: This is the only except for you to print in the backend here */
  public void DisplayAllBooks() {
    if(this.bookDb.NumBooks() == 0){
      System.out.println("No books in the system.");
      return;
    }
    System.out.printf("%8s%14s%35s%11s%12s%25s%14s\n","Book ID","Book Type","Title","Num Page","Extra Info","Borrower","Due Date"); //--> Design later
    System.out.println("================================================" +
            "============================================================" +
            "=============");
    for (int i = 0; i < this.bookDb.NumBooks(); i++){
      Book book = this.bookDb.GetBookAt(i);
      if (book != null){
        String typeBook = "";
        if(book instanceof History){
          typeBook ="History";
        } else if (book instanceof NonFiction){
          typeBook = "Non-Fiction";
        } else if (book instanceof Novel){
          typeBook = "Novel";
        }
        String borrower = book.GetBorrower();
        if(borrower == null){
          borrower = "";
        }
        String dueDate = "";
        if (book.GetDueDate() != null){
          java.text.SimpleDateFormat newDueDate = new java.text.SimpleDateFormat("yyyy-MM-dd");
          dueDate = newDueDate.format(book.GetDueDate());
        }
        System.out.printf("%8d%14s%35s%11d%12s%25s%14s\n",book.GetBookId(), typeBook,
                book.GetTitle(), book.GetNumPages(),
                book.ExtraInfo(), borrower, dueDate);
      }
    }
  }
  
  /* Displays all the books of the specified book type.
   * NOTE: This is the only except for you to print in the backend here.
   * @param bookType - The book type. Info can be found in
   *                   the Book class. */
  public void DisplayAllBooks(int bookType) {
    if ( this.bookDb.NumBooks() == 0){
      System.out.println("No books in the system.");
      return;
    }
    // How ---> get the extra info
    String extraInfoFinding = "Extra Info";
    if (bookType == Book.HISTORY){
      extraInfoFinding = "Is BCE";
      // I see the picture No.e

    }
    System.out.printf("%8s%14s%35s%11s%12s%25s%14s\n","Book ID","Book Type","Title","Num Page",extraInfoFinding,"Borrower","Due Date"); //--> Design later
    System.out.println("================================================" +
            "============================================================" +
            "=============");
    boolean found = false; // This variablle to check if the type book is fining exists at least one time
    for (int i = 0; i < this.bookDb.NumBooks(); i++){
      Book book = this.bookDb.GetBookAt(i);
      if (book != null){
        boolean got = false;
        String typeBookNow = "";
        if (bookType == Book.HISTORY && book instanceof History) {
          got = true;
          typeBookNow = "History";
        } else if (bookType == Book.NON_FICTION && book instanceof NonFiction){
          typeBookNow ="Non-Fiction";
          got = true;
        } else if (bookType == Book.NOVEL && book instanceof Novel){
          typeBookNow = "Novel";
          got = true;
        }
        if (got) {
          found = true;
          String borrowerNow = book.GetBorrower();
          if (borrowerNow == null) {
            borrowerNow = "";
          }

          String dueDateNow = "";
          if (book.GetDueDate() != null) {
            java.text.SimpleDateFormat newDueDate = new java.text.SimpleDateFormat("yyyy-MM-dd");
            dueDateNow = newDueDate.format(book.GetDueDate());

          }
          System.out.printf("%8d%14s%35s%11d%12s%25s%14s\n",book.GetBookId(), typeBookNow,
                  book.GetTitle(), book.GetNumPages(),
                  book.ExtraInfo(), borrowerNow, dueDateNow);
        }
      }
    }
    if (!found){
      System.out.println("The typeBook does not exist in the Library system.");
    }
  }
  
  /* Borrow a book from the library.
   * @param bookId   - The book to borrow
   * @param borrower - The borrower's name
   * @param numDays  - The days to borrow
   * @return         - Returns 1 if borrowing is successful.
   *                   Returns -3 if the book ID doesn't exist in the system.
   *                   Returns -4 if the number of days doesn't satisfy the book borrowing rules. 
   *                   Returns -5 if someone else borrowed the book already. */
  public int BorrowBook(int bookId, String borrower, int numDays) {
    Book book = bookWhere(bookId);
    // Dummy return value
    if (book == null){
      return INVALID_ID; //Returns -3 if the book ID doesn't exist in the system.

    }
    if (book.IsBorrowed()){
      return BORROWED;
      //Returns -5 if someone else borrowed the book already.
    }
     boolean statisfy = book.Borrow(borrower,numDays);
    if(!statisfy){
      return INVALID_BORROW_DAY;
      //Returns -4 if the number of days doesn't satisfy the book borrowing rules.
    } else {
      return SUCCESS; // Borrow successfully
    }
  }
  
  /* Return a book to the library.
   * @param bookId  - The book to return
   * @return        - Returns 1 if returning is succesful.
   *                  Returns -3 if the book ID doesn't exist in the system.
   *                  Returns -6 if the book was never borrowed. */
  public int ReturnBook(int bookId) {
    Book book = bookWhere(bookId);
    // Dummy return value
    if (book == null){
      return INVALID_ID;
    }
    if (!book.IsBorrowed()){
      return NEVER_BOR;
    }
    book.Return();
    return SUCCESS;
  }
}