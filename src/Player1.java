import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * {@code Player} represented as a set of integer values and a boolean
 * describing score, roundNumber, wins, and game inProgress.
 *
 * @convention Score may never exceed 11 except when players are tied.
 * @convention Round number starts at 0 but must be 1 <= round <= 5 (best of 5).
 * @correspondence [0 <= score <= 11, 1 <= roundNumber <= 5, 0 <= wins,
 *                 inProgress = true | false]
 * @author H. Trowbridge
 */
public class Player1 extends PlayerSecondary {
    /*
     * Private members --------------------------------------------------------
     */

    /** The last applicable game score. */
    private int[] score;
    /** The last applicable round number. */
    private int roundNumber;
    /** The total number of wins. */
    private int wins;
    /** Game in progress or not. */
    private boolean inProgress;

    /*
     * Standard methods -------------------------------------------------------
     */

    /** Create initial representation of the date in our four variables. */
    private void createNewRep() {
        this.score = new int[5];
        this.roundNumber = 0;
        this.wins = 0;
        this.inProgress = false;
    }

    /**
     * No-argument constructor.
     */
    public Player1() {
        this.createNewRep();
    }

    @Override
    public final Player newInstance() {
        try {
            return this.getClass().getConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new AssertionError(
                    "Cannot construct object of type " + this.getClass());
        }
    }

    @Override
    public final void clear() {
        this.createNewRep();
    }

    @Override
    public final void transferFrom(Player source) {
        Player1 localSource = (Player1) source;

        this.score = localSource.score;
        this.roundNumber = localSource.roundNumber;
        this.wins = localSource.wins;
        this.inProgress = localSource.inProgress;
        localSource.createNewRep();
    }

    /*
     * Kernel methods ---------------------------------------------------------
     */

    @Override
    public boolean inProgress() {
        return this.inProgress;
    }

    @Override
    public void startGame() {
        this.inProgress = true;
    }

    @Override
    public void endGame() {
        this.inProgress = false;
    }

    @Override
    public int getScore(int round) {
        return this.score[round - 1];
    }

    @Override
    public int[] getScoreArr() {
        return this.score;
    }

    @Override
    public void setScore(int round, int content) {
        this.score[round - 1] = content;
    }

    @Override
    public int getRoundNumber() {
        return this.roundNumber;
    }

    @Override
    public int getWins() {
        return this.wins;
    }

    @Override
    public void setWins(int changed) {
        this.wins = changed;
    }

    @Override
    public void setRound(int changed) {
        this.roundNumber = changed;
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleWriter out = new SimpleWriter1L();
        SimpleReader in = new SimpleReader1L();
        SimpleWriter fileOut;

        String fileName;
        out.println(
                "Before we start, please provide the path of where we will report on "
                        + "the status of our 'Players' :D");
        fileName = in.nextLine();
        fileOut = new SimpleWriter1L(fileName);

        out.println("New round started for each player :)...");
        Player one = new Player1();
        Player two = new Player1();

        out.println("Let's simulate game 1 between the two. Started!");
        one.startGame();
        two.startGame();

        one.simulateGame(two);

        one.endGame();
        two.endGame();

        int roundVal = 1;
        boolean finished = false;
        String input;

        while (roundVal < 5 && !finished) {
            // TODO - cleanup and fix data
            out.println("Would you like to simulate another? Type 'y or n'");
            input = in.nextLine();
            if (input.toLowerCase().trim().equals("y")) {
                one.startGame();
                two.startGame();

                one.simulateGame(two);

                one.endGame();
                two.endGame();
                roundVal++;
                out.println("Game " + roundVal + " done!");
            } else {
                finished = true;
            }
        }

        one.updateClientView(fileOut, two);
        out.println("Game is over!");

        out.close();
        in.close();
        fileOut.close();
    }
}
