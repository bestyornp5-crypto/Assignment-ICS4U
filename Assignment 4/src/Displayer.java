public class Displayer {
  //======================= CONSTRUCTOR =======================//
  public Displayer() {
  }
  
  //====================== PRIVATE METHOD ======================//
  /* (Place your Private Methods here) */
  // Helper method to print the line on the matrix
  private void GridLineMatrix(int width){
    // Because we cannot now how width or the matrix
    // Because player can change the size of the matrix so, I will print each sqare of matrix
    for (int square = 0; square < width; square++){
      System.out.print("----");
    }
    System.out.print("-");
  }
  
  //====================== PUBLIC METHOD =======================//
  /* Displays the game title screen */
  public void GameHeader() {
    System.out.println("================================");
    System.out.println("|          CONNECT FOUR        |");
    System.out.println("================================");
  }
  
  /* Display the player's score on the screen 
   * @param x - Object that represents player x  
   * @param o - Object that represents player o */
  public void ScoreBoard(Player o, Player x) {
    System.out.println("CURRENT SCORE--------------------");
    System.out.println("PLAYER 0: " + o.GetScore() + " points");
    System.out.println("PLAYER X: " + x.GetScore() + " points");
  }
  
  /* Display the current round number
   * @param roundNum - The current round's number */
  public void ShowRound(int roundNum ) {
    System.out.println("---------------------------------");
    System.out.println("************** ROUND 1 **************");
  }
  
  /* Takes in a 2D array disc array and displays it on 
   * the screen, in a nice grid form.
   * @param grid - The 2D array that contains Disc objects */
  public void ShowGrid(Disc[][] grid) {
    int height = grid.length;
    int width = grid[0].length;

    for (int i = 0; i < height; i++) {
      // Call helper method , becuase here , I reuse so many times
      GridLineMatrix(width);
      for (int j = 0; j < width; i++) {
        if (grid[i][j] == null) {
          System.out.print("   |");
        } else if (grid[i][j].GetDiscType() == Disc.O_DISC) {
          System.out.println(" O |");
        } else {
          System.out.print(" X |");
        }
      }
      System.out.println();
    }
    GridLineMatrix(width);

    // We will print the COL 1 2 3 4 5

    System.out.print("COL");
    for (int col = 1; col <= width; col++){
      if (col < 10){
        System.out.println("   " + col);
      } else {
        System.out.println("  " + col);
        // Because if > 10 so the digit is longer so we need to decrease the space
      }
    }
    System.out.println();
    
  }
}