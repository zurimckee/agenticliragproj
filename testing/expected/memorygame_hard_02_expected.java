// GamePane.java — additions needed. Only new/changed lines are shown, with
// enough context to place them. Apply constructor changes in BOTH
// GamePane() and GamePane(int cardSize).

// ----- New import -----
import java.util.prefs.Preferences;


// ----- New fields -----
private Preferences prefs;
private int attempts;           // self-contained attempt counter for this feature
private Label bestScoreLabel;


// ----- In BOTH constructors, after turnBox/turns are set up -----
prefs = Preferences.userNodeForPackage(GamePane.class);
bestScoreLabel = new Label("best: --");
turnBox.getChildren().add(bestScoreLabel);


// ----- Helper for a safe Preferences key, since `level` can be null
//       (e.g. if "new game" is pressed before any level was ever picked) -----
private String currentLevelKey() {
    return (level != null) ? level : "default";
}


// ----- In BOTH constructors' menu.setOnAction(...) handler, after rows/cols
//       are determined for the selected level -----
attempts = 0;                                          // new
String saved = prefs.get(currentLevelKey(), null);      // new
bestScoreLabel.setText(saved == null ? "best: --" : "best: " + saved); // new
try {
    numClicks = 0;
    turns.setText("turns: " + numMatched);
    cgp.initCards(rows, cols);
} catch (FileNotFoundException ex) {
    throw new RuntimeException(ex);
}


// ----- In handleCardClicked(), count an attempt each time the second card
//       of a pair is flipped -----
public void handleCardClicked(Card clickedCard){
    if(numClicks == 0){
        clickedCardOne = clickedCard;
        clickedCard.flipCard();
        numClicks += 1;
    } else if (numClicks == 1) {
        clickedCardTwo = clickedCard;
        clickedCard.flipCard();
        numClicks = 2;
        attempts += 1;                  // new
        startTime = System.nanoTime();
        timer.start();
    }
}


// ----- In checkMatch(), on a win, compare against and possibly update the
//       saved best for the level just played -----
if (numMatched == (cgp.getCurrentRows() * cgp.getCurrentCols() / 2)){
    String key = currentLevelKey();                       // new
    String savedBest = prefs.get(key, null);               // new
    if (savedBest == null || attempts < Integer.parseInt(savedBest)) { // new
        prefs.put(key, String.valueOf(attempts));           // new
        bestScoreLabel.setText("best: " + attempts);        // new
    }
    gameOver();
}


// ----- In newGame(), reset the attempt counter and refresh the best-score
//       display for whatever level is (or isn't) currently selected -----
numClicks = 0;
numMatched = 0;
attempts = 0;                                              // new
String savedForRestart = prefs.get(currentLevelKey(), null); // new
bestScoreLabel.setText(savedForRestart == null ? "best: --" : "best: " + savedForRestart); // new
turns.setText("turns: " + numMatched);

/* GRADING NOTE
   Accept any solution where:
   1. Best scores are persisted via java.util.prefs.Preferences (or an
      equally durable, no-external-dependency mechanism) keyed per level,
      not a single shared value across all levels.
   2. A new best is only ever saved on an actual win (all pairs matched),
      and only when it's strictly better than (or there is no) existing
      saved value -- a solution that overwrites unconditionally is incorrect.
   3. Attempts are counted once per pair-check (not per single card flip).
   4. The level==null edge case (newGame() called before any level was ever
      picked from the dropdown) doesn't crash -- some fallback key or guard
      is used instead of passing null into Preferences.
   5. Both constructors are updated consistently.
   Exact label text/formatting and where in the status bar it's placed are
   flexible.
*/
