/* The game that keeps track of all the information, such as
 * the grid and which player's turn */
public class ConnectFour {
  public final static int DEFAULT_HEIGHT =  6;    // The default height of the grid (DO NOT CHANGE)
  public final static int DEFAULT_WIDTH  =  7;    // The default width of the grid (DO NOT CHANGE)
  
  
  private Disc[][] grid;                          // The grid of the game. A 2D array of Disc objects
  private Player[] allPlayers;                    // An array that stores all the player's object
  private Player currPlayer;                      // Keeps track of the current player
  private int numRounds;                          // Keep track of number of rounds to play
  private int currRound;                          // Keep track of the current round
  
  //======================= CONSTRUCTOR =======================//
  /* Initialize the game's instance variables. 
   * @param numRounds - The number of rounds the user wants to play. */
  public ConnectFour(int numRounds) {
    this(numRounds, DEFAULT_HEIGHT, DEFAULT_WIDTH);
  }
  
  /* Initialize the game's instance variables. 
   * @param numRounds - The number of rounds the user wants to play.
   * @param height    - The grid height. 
   * @param width     - The grid width. */
  public ConnectFour(int numRounds, int height, int width) {
    this.numRounds = numRounds;
    this.currRound = 1;
    this.grid = new Disc[height][width];
    this.allPlayers = new Player[2];
    this.allPlayers[0] = new Player(Disc.O_DISC);
    this.allPlayers[1] = new Player(Disc.X_DISC);

    this.currPlayer = this.allPlayers[0];
    // assume start at player 1

  }
  
  //====================== PRIVATE METHOD =======================//
  /* (Place your Private Methods here) */
  
  
  //====================== PUBLIC METHOD =======================//
  /* Get the current round number. 
   * First round will be 1, second round will be 2, etc...
   * @return - The current round number. */
  public int GetCurrRound() {
    return this.currRound;   // Dummy return value, needs to be changed
  }
  
  /* Set the current round number.
   * @param newCurrRound - The current new round number */
  public void SetCurrRound(int newCurrRound) {
    this.currRound = newCurrRound;
  }
  
  /* Get the total rounds
   * @return - The total number of rounds. */
  public int GetNumRounds() {
    return this.numRounds;   // Dummy return value, needs to be changed
  }
  
  /* Get the 2D array Disc array
   * @return - The 2D grid containing Disc objects */
  public Disc[][] GetGrid() {
    return this.grid;   // Dummy return value, needs to be changed
  }
  
  /* Get the grid's height.
   * @return - The grid's height */
  public int GetGridHeight() {
    return this.grid.length;   // Dummy return value, needs to be changed
  }
  
  /* Get the grid's width.
   * @return - The grid's width */  
  public int GetGridWidth() {
    return this.grid[0].length;   // Dummy return value, needs to be changed
  }
  
  /* Resets the grid so it contains no objects */
  public void ResetGrid() {
    int height = GetGridHeight();
    int width = GetGridWidth();
    this.grid = new Disc[height][width];
  }
  
  /* Get the player object array  
   * @return - The array that contains all the players' object */
  public Player[] GetAllPlayers() {
    return this.allPlayers;   // Dummy return value, needs to be changed
  }
  
  /* Returns the current player object
   * @return - The current player's object */
  public Player GetCurrPlayer() {
    return this.currPlayer;  // Dummy return value, needs to be changed
  }
  
  /* Set who will be the current player
   * @param index - The value that represents the player. This
   *                is based on the array position of where the 
   *                player is stored. */
  public void SetCurrPlayer(int index) {
    if (index >= 0 && index < allPlayers.length){
      this.currPlayer = this.allPlayers[index];
    }
  }  
  
  /* Check if the grid is full of objects
   * @return - Returns true, if the grid is full.
   *           Returns false, if the grid is not full. */    
  public boolean IsGridFull() {

    return NumAvailBlock() == 0;   // Dummy return value, needs to be changed
  }   
  
  /* Inserts a disc object in the specified column.
   * @param col - The column that the player wants to insert the disc
   *              Note: col = 0 is the 1st column 
   *                    col = 1 is the 2nd column
   *                    etc...
   * @return    - Returns 1, if the disc is inserted successfully into the column.
   *              Returns -1, if the column is full. 
   *              Returns -2, if the column is invalid. */    
  public int Insert(int col) {
    return 0;   // Dummy return value, needs to be changed
  }   
  
  /* Get the number of available (empty) blocks in the grid
   * @return - The number of empty blocks */
  public int NumAvailBlock() {
    int count = 0;
    int height = GetGridHeight();
    int width = GetGridWidth();
    for(int row =0; row < height; row++){
      for(int col = 0; col < width; col++){
        if (this.grid[row][col] == null){
          count++;
        }
      }
    }
    return count;   // Dummy return value, needs to be changed
  }   
  
  /* Get the current player's name (either X or O) as a char
   * @return - The player's name, either 'X' or 'O' */   
  public char GetCurrPlayerChar() {
    if (this.currPlayer.GetDiscType() == Disc.O_DISC){
      return 'O';
    }
    else{
      return 'X';
    }   // Dummy return value, needs to be changed
  }
  
  /* Switch player's turn to the next player (If current player is X, then it 
   * switches to O. If current player is O, then it switches to X) */   
  public void SwitchPlayer() {
    if (this.currPlayer == this.allPlayers[0]){
      this.currPlayer = this.allPlayers[1];
    } else{
      this.currPlayer = this.allPlayers[0];
    }
  }
  
  /* Check if there is a winner for the current round 
   * @return - Returns true, if there is a winner.
   *           Returns false, if there is no winner */      
  public boolean HasRoundWinner() {
    return false;   // Dummy return value, needs to be changed
  }
  
  /* Determine the final winner by checking the player's score. 
   * @return - Returns the array position value that represents the player who wins.
   *           Returns -3,if there's a tie (same score) */
  public int GetGameWinner() {
    int scoreO = this.allPlayers[0].GetScore();
    int scoreX = this.allPlayers[1].GetScore();// Dummy return value, needs to be changed

    if (scoreO > scoreX){
      return 0;
    } else if (scoreX > scoreO){
      return 1;
    } else {
      return -3; // Same score
    }
  }
}