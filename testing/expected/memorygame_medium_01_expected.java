// GamePane.java — only the changed/added lines are shown, with context.
// The same change needs to be applied in BOTH constructors, since
// GamePane() and GamePane(int cardSize) duplicate this setup.

// 1. New field, alongside the other counters:
private int numTurns; // total attempts taken (regardless of match outcome)


// 2. In BOTH constructors, update the initial label text for consistency:
turns = new Label("turns: " + numTurns + " | matches: " + numMatched);


// 3. In handleCardClicked(), increment numTurns when the second card of a
//    pair is flipped -- this is the moment an "attempt" actually happens:
public void handleCardClicked(Card clickedCard){
    if(numClicks == 0){
        clickedCardOne = clickedCard;
        clickedCard.flipCard();
        numClicks += 1;
    } else if (numClicks == 1) {
        clickedCardTwo = clickedCard;
        clickedCard.flipCard();
        numClicks = 2;
        numTurns += 1;                 // new
        startTime = System.nanoTime();
        timer.start();
    }
}


// 4. In checkMatch(), update the label to show both values:
turns.setText("turns: " + numTurns + " | matches: " + numMatched);


// 5. In newGame(), reset the new counter alongside the existing ones:
numClicks = 0;
numMatched = 0;
numTurns = 0;                          // new
turns.setText("turns: " + numTurns + " | matches: " + numMatched);


// 6. In BOTH constructors' menu.setOnAction(...) handler, reset it there too
//    (this handler resets numClicks/turns independently of newGame()):
numClicks = 0;
numTurns = 0;                          // new
turns.setText("turns: " + numTurns + " | matches: " + numMatched);

/* GRADING NOTE
   Accept any solution where:
   1. A turn/attempt counter increments exactly once per pair of cards
      flipped, regardless of match outcome (not once per single card click).
   2. The label displays both the turn count and the match count.
   3. The counter resets to 0 in every place a new game can start: the
      "new game" button (newGame()), the level dropdown's onAction handler,
      and via the win-dialog's "new game" option (which calls newGame()
      internally, so fixing newGame() covers that path too).
   4. Both constructors are updated consistently, since either one could be
      the one actually instantiated by Main.java.
   Exact label text/formatting is flexible as long as both numbers are shown
   and semantically correct.
*/
