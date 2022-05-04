package yorkpirates.game;

public class ScoreManager {

    private int score;

    /**
     * Initialises a ScoreManager with a default score of 0.
     */
    public ScoreManager(){
        this(0);
    }

    /**
     * Initialises a ScoreManager.
     * @param defaultScore  the default score value.
     */
    public ScoreManager(int defaultScore){
        score = defaultScore;
    }

    /**
     *  Adds an integer value to the score.
     * @param amount    the value to be added.
     */
    public void Add(int amount){
        score += amount;

    }
    /**
     * Subtracts an integer amount from the score
     * @param amount    The value to be subtracted
     * @return          Return true if the value remains greater than 0
     */
    public boolean Sub(int amount){
        if(score - amount >= 0){
            score -= amount;
            return true;
        }
        else{
            return false;
        }
    }

    /**
     *  Gets the score value in integer form.
     * @return  the score.
     */
    public int Get(){
        return score;
    }

    /**
     *  Gets the score value in string form.
     * @return  the score.
     */
    public String GetString() {
        return Integer.toString(score);
    }
}
