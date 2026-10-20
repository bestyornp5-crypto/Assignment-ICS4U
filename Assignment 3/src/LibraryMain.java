import java.util.Scanner;

public class LibraryMain {
  public static void main(String[] args) {
    LibrarySys libSys = new LibrarySys();            // The LibarySys object that manages everything
    String bookListFilename = "BookList.txt";        // The filename of the booklist textfile
    Scanner scanner = new Scanner(System.in);
    int choice = 0;
    do {
      System.out.println("==================================");
      System.out.println("|       CIMP LIBRARY SYSTEM      |");
      System.out.println("==================================");
      System.out.println("1. Add Books from File");
      System.out.println("2. Add Single Book");
      System.out.println("3. Display Books");
      System.out.println("4. Borrow Book");
      System.out.println("5. Return Book");
      System.out.println("6.Exit");
      System.out.print("Choose an option: ");
      if (scanner.hasNextInt()){
        choice = scanner.nextInt();
        scanner.nextLine();
        switch(choice) {
          case 1:
            // Import BookList.text
            int read = libSys.ImportBooks(bookListFilename);
            System.out.println("Added " + read + " to the system from " + bookListFilename);
            break;
          case 2:
            System.out.println("==================================");
            System.out.println("|         Add Single Books       |");
            System.out.println("==================================");
            System.out.println("Book Type:");
            System.out.println("1. History");
            System.out.println("2. Non Fiction");
            System.out.println("3. Novel");
            System.out.print("Enter Book Type: ");
            int typeBook = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Enter Book Title: ");
            String title = scanner.nextLine();

            System.out.print("Enter Book Number of Pages: ");
            int pages = scanner.nextInt();
            scanner.nextLine();

            String extraInfo ="";
            if(typeBook == 1){
              System.out.println("Book in BCE Era (Y/N): ");
              extraInfo = scanner.nextLine();
            } else if (typeBook == 2){
              System.out.print("Enter Genre: ");
              extraInfo = scanner.nextLine();
            } else if (typeBook == 3){
              System.out.print("Enter Difficulty of Englis Level: ");
              extraInfo = scanner.nextLine();
            }

            int solveBookType = typeBook - 1;
            int result = libSys.AddBook(solveBookType,title,pages,extraInfo);
            // Because to define book
            // 0 - History, 1-Non-Fic , 2 - Novel, but we start from 1,2,3 so we minus 1
            if (result == libSys.SUCCESS){
              System.out.printf("Successfully added the book \'%s\' ",title);
            } else if (result == libSys.INVALID_BOOKTYPE){ // Mean the type of book error
              System.out.println("Error: Invalid Type of Book !");
            } else if (result == libSys.INVALID_NUMPAGES){ // Number pages < -1
              System.out.println("Error: Invalid number page !");
            } else if (result == libSys.INVALID_EXTRA_INFO){ // Like Novel book but BCE --> Wrong
              System.out.println("Error: Invalid Extra Information !");
            }

            break;
          case 3:
            System.out.println("==================================");
            System.out.println("|           Display Book         |");
            System.out.println("==================================");
            System.out.println("Books to display");
            System.out.println("1. All");
            System.out.println("2. History only");
            System.out.println("3. Non Fiction Only");
            System.out.println("4. Novel Only");
            System.out.print("Enter Display Option: ");
            int displayOption = scanner.nextInt();
            scanner.nextLine();

            if (displayOption == 1){ // If choose displayAll
              libSys.DisplayAllBooks(); //--> Call display all
            } else if (displayOption == 2){ // If choose display History Only
              libSys.DisplayAllBooks(Book.HISTORY);
            } else if (displayOption == 3) { // If choose display Non-Fic Only
              libSys.DisplayAllBooks(Book.NON_FICTION);
            } else if (displayOption == 4){ // If choose display Novel Only
              libSys.DisplayAllBooks(Book.NOVEL);
            } else {
              System.out.println("Error: The type does not exist !");
              // If they choose Romantic , Comendy or sth is funny
            }
            break;
          case 4:
            System.out.println("==================================");
            System.out.println("|          Borrow Books          |");
            System.out.println("==================================");
            System.out.print("Enter book ID to borrow: ");
            int borrowId = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Enter borrower's name: ");
            String nameBorrower = scanner.nextLine();

            System.out.print("Enter number of days to borrow: ");
            int borrowDays = scanner.nextInt();
            scanner.nextLine();

            int resultBorrowed = libSys.BorrowBook(borrowId,nameBorrower,borrowDays);
            // Check
            if (resultBorrowed == libSys.SUCCESS){
              System.out.printf("Successfully borrow book \'%d\'. ", borrowId);
            } else if (resultBorrowed == libSys.INVALID_ID ){ // Invalid id report from my back end
              System.out.println("Error: The id does not exist !");
            } else if (resultBorrowed == libSys.BORROWED){ // Sb borrowed
              System.out.println("Excused: Somebody borrowed this book !");
            } else if (resultBorrowed == libSys.INVALID_BORROW_DAY){ // The number days > limitation
              System.out.println("Error: the number of days doesn't satisfy the book borrowing rules !");
            }
            break;
          case 5:
            System.out.println("==================================");
            System.out.println("|          Return Books          |");
            System.out.println("==================================");
            System.out.print("Enter book ID to return: ");
            int returnId = scanner.nextInt();
            scanner.nextLine();
            int resultReturn = libSys.ReturnBook(returnId);
            if(resultReturn == libSys.SUCCESS){
              System.out.printf("Successfully return book \'%d\'",returnId);

            } else if (resultReturn == libSys.INVALID_ID){
              System.out.println("Error: Book ID doesn't exist in the system.");
            } else if (resultReturn == libSys.NEVER_BOR) {
              System.out.println("Error: The book was never borrowed.");
            }
            break;
          case 6:
            System.out.println("Library system shuts down.");
            break;
          default:
            System.out.println("Invalid option ! ( 1- 6 )");


        }

      }
      else {
        choice = 0;
        System.out.println("Invalid type of input !");
      }
      System.out.println();

    } while(choice != 6);
    scanner.close();



  }
}