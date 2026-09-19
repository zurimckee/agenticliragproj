// GamePane.java — additions needed. Only new/changed lines are shown, with
// enough context to place them. Apply the constructor changes in BOTH
// GamePane() and GamePane(int cardSize).

// ----- New imports -----
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.util.Duration;


// ----- New fields, alongside the existing counters -----
private Timeline elapsedTimer;      // separate from the existing `timer`
                                     // AnimationTimer, which only handles
                                     // the brief non-match flip-back delay
private int elapsedSeconds;
private boolean gameTimerStarted;   // ensures we only start once per game
private Label elapsedTimeLabel;


// ----- In BOTH constructors, after `turns` is created and added to turnBox -----
elapsedTimeLabel = new Label("0:00");
turnBox.getChildren().add(elapsedTimeLabel);

elapsedTimer = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
    elapsedSeconds++;
    elapsedTimeLabel.setText(formatElapsed(elapsedSeconds));
}));
elapsedTimer.setCycleCount(Timeline.INDEFINITE);


// ----- New helper method, anywhere in the class -----
private String formatElapsed(int totalSeconds) {
    int m = totalSeconds / 60;
    int s = totalSeconds % 60;
    return m + ":" + String.format("%02d", s);
}


// ----- In handleCardClicked(), start the timer on the first flip of a game -----
public void handleCardClicked(Card clickedCard){
    if (!gameTimerStarted) {           // new -- runs once per game
        gameTimerStarted = true;
        elapsedTimer.play();
    }
    if(numClicks == 0){
        clickedCardOne = clickedCard;
        clickedCard.flipCard();
        numClicks += 1;
    } else if (numClicks == 1) {
        clickedCardTwo = clickedCard;
        clickedCard.flipCard();
        numClicks = 2;
        startTime = System.nanoTime();
        timer.start();
    }
}


// ----- In checkMatch(), stop the timer when the game is won -----
if (numMatched == (cgp.getCurrentRows() * cgp.getCurrentCols() / 2)){
    elapsedTimer.stop();               // new
    gameOver();
}


// ----- In newGame(), reset the timer state -----
numClicks = 0;
numMatched = 0;
elapsedTimer.stop();                   // new
elapsedSeconds = 0;                    // new
elapsedTimeLabel.setText("0:00");      // new
gameTimerStarted = false;              // new
turns.setText("turns: " + numMatched);


// ----- In BOTH constructors' menu.setOnAction(...) handler, reset there too -----
// (this handler starts a fresh grid independently of newGame())
numClicks = 0;
elapsedTimer.stop();                   // new
elapsedSeconds = 0;                    // new
elapsedTimeLabel.setText("0:00");      // new
gameTimerStarted = false;              // new
turns.setText("turns: " + numMatched);
cgp.initCards(rows, cols);

/* GRADING NOTE
   Accept any solution where:
   1. A separate timing mechanism is used from the existing `timer` field
      (reusing/renaming it to serve both purposes is not acceptable --
      the 800ms flip-back logic and a 1-second repeating display timer
      have different lifecycles and would conflict).
   2. The display starts only on the first card flip of a fresh game, not
      immediately when the window opens or a level is picked.
   3. It updates roughly once per second and stops when the game is won.
   4. It resets to 0:00 on every new-game path: the "new game" button, the
      level dropdown, and the win dialog's "new game" option (which routes
      through newGame(), so fixing newGame() covers that path).
   5. Both constructors are updated consistently.
   A Timeline is the natural fit given this codebase already imports from
   javafx.animation, but any correctly-behaving repeating timer mechanism
   (e.g. a second AnimationTimer with its own start-time tracking) is
   equally acceptable.
*/
