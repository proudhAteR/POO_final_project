package city_cleaner.Game;

public class ScoreManager {
  private int score;
  private static ScoreManager instance;

  public static ScoreManager getInstance() {
    if (instance == null) {
      instance = new ScoreManager();
    }
    return instance;
  }

  public int getScore() {
    return score;
  }

  public void incrementScore(int value) {
    score += value;
  }
}