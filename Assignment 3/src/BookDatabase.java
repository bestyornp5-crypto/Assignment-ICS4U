/* Developed By: Kevin Wong
 * Revised Date: July 23, 2024
 * Version     : Ver 1.0 */

/* NOTE: PROVIDED CLASS. DO NOT CHANGE ANYTHING HERE.
 *       JUST USE IT AS IS */

import java.util.Scanner;
import java.io.File;    
import java.io.IOException; 

public class BookDatabase {
  public final static int ARRAY_POS_BOOK_TYPE  = 0;
  public final static int ARRAY_POS_BOOK_NAME  = 1;
  public final static int ARRAY_POS_NUM_PAGE   = 2;
  public final static int ARRAY_POS_INFO       = 3;
  
  public final static int ADD_BOOK_SUCCESS     = 1;
  public final static int ADD_BOOK_FAIL        = 0;
  
  private Book[] allBooks;      // It stores all the Book objects. 
  
  //=============== Constructor =================
  /* Initializes the Book array variable */
  public BookDatabase() {
    this.allBooks = null;
  }
  
  //============ PRIVATE METHOD =========//
  /* To increase the size of the Book object array by 1 element. */
  private void growBookArray() {
    if( allBooks != null ) {
      // When there are other rooms in the AllRooms array
      Book[] newArray = new Book[ allBooks.length + 1 ];
      for( int i = 0; i < allBooks.length; i++ ) {
        // Copy all the elements from the old array to the new array.
        newArray[i] = allBooks[i];
      }
      // Assign the newArray reference back to the old array variable.
      allBooks = newArray; 
    }
    else {
      // When the allRooms array is empty
      allBooks = new Book[1];
    }      
  }   
  
  /* Randomize a Book ID
   * @return a random Book ID */
  private int randId() {
    return (int)(Math.random() * ((1999 - 1000) + 1)) + 1000; 
  }
  
  //============ PUBLIC METHOD =========//
  /* Generate a random new Book ID. This new Book ID 
   * is a new number not found in the database 
   * @return - The random ID that is unique 
   *           in the database. */
  public int GenNewBookId() {
    int randID;
    boolean isInDB;
    
    if( allBooks == null ) {
      return this.randId();
    }
    else {
      do {
        isInDB = false;
        randID = this.randId();
        // Check if the random ID exists already. 
        for( int i = 0; i < allBooks.length; i++ ) {
          if( allBooks[i].GetBookId() == randID ) {
            isInDB = true;
            break;
          }
        }
      } while( isInDB );
      
      return randID;
    }
  }
  
  /* Imports multiple Books from a textfile to the database.
   * @param filename    - The filename of the file, including the file extension.
   *                      eg) "BookList.txt"
   *                      Note: textfile must be stored in the same 
   *                            folder as the program              
   * @return            - Number of books it has imported into the database. */
  public int AddBook(String filename)   {
    int numImported = 0;
    try {
      Scanner input = new Scanner(new File(filename)); 
      
      // While the file has a line of record
      while (input.hasNextLine()) {
        // Look for the account number
        String bookInfo = input.nextLine();
        String[] arrayData = bookInfo.split(":");
        
        if( arrayData[0].equals("") ) {
          break;
        }
        else if( arrayData.length != 4 ) {
          throw new IOException("Import Textfile format is invalid");
        }
        
        // Determine the room type and create the appropriate 
        // room object for it.
        Book newBook = null;
        int newBookId = this.GenNewBookId();
        int bookType = Integer.parseInt(arrayData[ARRAY_POS_BOOK_TYPE]);
        /* NOTE: YOU WILL HAVE AN ERROR HERE, WHEN YOU FIRST COMPILE. HOWEVER
         THE ERROR IS NOT FROM HERE. FIX IT SOMEWHERE ELSE, AND THE ERROR
         WILL DISAPPEAR HERE. */
        switch( bookType ) {
          case Book.HISTORY:
            newBook = new History(newBookId,
                                  arrayData[ARRAY_POS_BOOK_NAME], 
                                  Integer.parseInt(arrayData[ARRAY_POS_NUM_PAGE]), 
                                  Boolean.valueOf(arrayData[ARRAY_POS_INFO]) );
            break;
          case Book.NON_FICTION:
            newBook = new NonFiction(newBookId,
                                     arrayData[ARRAY_POS_BOOK_NAME], 
                                     Integer.parseInt(arrayData[ARRAY_POS_NUM_PAGE]), 
                                     arrayData[ARRAY_POS_INFO]);
            break;
          case Book.NOVEL:
            newBook = new Novel(newBookId,
                                arrayData[ARRAY_POS_BOOK_NAME], 
                                Integer.parseInt(arrayData[ARRAY_POS_NUM_PAGE]), 
                                Integer.valueOf(arrayData[ARRAY_POS_INFO]));
            break;
          default:
        }
        
        // Only add a room when the room is valid
        int numAdded = this.AddBook( newBook );
        numImported += numAdded;
      }
      
      input.close();  
    }
    catch( IOException ex ) {
      System.out.println("General I/O exception: " + ex.getMessage());
      numImported = 0;
    }     
    
    return numImported;
  }
  
  /* Add a Book object into the database array
   * @param room - The Book object to be added into the database array
   * @return     - Returns 1 if added successfully.
   *               Returns 0 otherwise. */
  public int AddBook(Book newBook) {
    if( newBook != null ) {
      this.growBookArray();
      this.allBooks[ allBooks.length - 1 ] = newBook;    
      return ADD_BOOK_SUCCESS;
    }
    else {
      return ADD_BOOK_FAIL;
    }
  }   
  
  /* Get the number of books that are in the database 
   * @return - Number of book objects the database currently has */    
  public int NumBooks() {
    if( this.allBooks == null ) {
      return 0;
    }
    else {
      return this.allBooks.length;
    }
  }
  
  /* Delete a Book object in the database array
   * @param roomId - The Book ID of the book that needs to be deleted 
   * @return       - True if successfully deleted the Book object
   *                 False otherwise. */
  public boolean DeleteBook(int pos) {
    boolean status = false;
    if( pos <= allBooks.length - 1 ) {
      Book[] temp = new Book[ allBooks.length - 1 ];
      int index = 0;
      for( int i = 0; i < allBooks.length; i++ ) {
        if( pos == i ) {
          temp[index] = allBooks[i];
          index++;
        }
      }
      allBooks = temp;
      status = true;
    }
    
    return status;
  }  
  
  /* Get a specific Book object at the position in the database array. 
   * It acts like a regular array, where the first Book is at 
   * position 0, second Book at position 1, etc.
   * @param pos - The position in the database array. 
   * @return    - Returns the Book object that is at position pos. 
   *              Returns null if position is out of range. */
  public Book GetBookAt(int pos) {
    if( this.allBooks != null && 0 <= pos && pos <= this.allBooks.length - 1 ) {
      return this.allBooks[pos];
    }
    else {
      return null;
    }
  }   
}