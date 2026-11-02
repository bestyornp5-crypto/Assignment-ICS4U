public class Player {
   int discType; // Store the type of player O or X
   int score; // store the total score
  
  //======================= CONSTRUCTOR =======================//
  /* @discTpe - The type of disc that is being created (Either O or X) */
  public Player(int discType) {
    this.discType = discType;
    this.score = 0;
    // When we begin, no score
  }
  
  //====================== PUBLIC METHOD =======================//
  /* Gets the player's disc type (0 or 1)
   * @return - The value of the disc type */
  public int GetDiscType() {
    return this.discType;   // Dummy return value, needs to be changed
  }
  
  /* Gets the players score 
   * @return - The player's score */
  public int GetScore() {
    return this.score;   // Dummy return value, needs to be changed
  }
  
  /* Add scores to the player's exiting score
   * @param score - The score that needs to be added
   *                to the player's score */
  public void AddScore(int score) {
    this.score += score;
  }
}