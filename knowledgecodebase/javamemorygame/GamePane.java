/**
 *  @author Zuri McKee
 *  Mason section 4
 *  4/22/2025
 *  GamePane is the main controller class for the game. It extends JavaFX's BorderPane and manages the layout and game logic, including:
 * - The CardGridPane containing the cards
 * - A status bar for game info like the turn count
 * - A command pane with controls to start a new game, select difficulty, and exit
 * - Game logic such as handling card clicks, checking matches, and managing the animation timer
 **/

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import java.io.FileNotFoundException;

import static javafx.scene.paint.Color.rgb;

public class GamePane extends BorderPane {
    private CardGridPane cgp;
    private HBox statusPane; //e game status information (e.g. the number turns and the game timer
    private HBox commandPane; //with controls for setting the level, starting a new game and exiting the application.
    private Button exitButton; //closes the window
    private Button newGame; //starts a new game
    private ComboBox<String> menu; //provides a drop down to choose a new level
    private Label turns; //shows the number of matches achieved so far in the game
    private Card clickedCardOne; //provides functionality for matches
    private Card clickedCardTwo; //provides functionality for matches
    private AnimationTimer timer; //controls how long the faces of the cards are shown
    private int rows;
    private int cols;
    private int numClicks; //controls matching functionality
    private int numMatched; //number of matches achieved in game, increases
    private String level; //variable to hold combobox level, so the newgame functionality works
    private long startTime; //works with animation timer to make it so the card faces are shown for 0.8 secs
    private AudioClip matchSound; //plays when a match
    private AudioClip notMatch; //plays when no match
    private AudioClip victory; //plays win all matches found


    /**
     * no-arg constructor for GamePane
     * initializes all the fields and adds some of them to the borderpane
     * also holds the handle() method for the animation timer
     * and the event handlers for the combobox, newGame button, and exitButton button
     * @throws FileNotFoundException, RuntimeException
     */
    public GamePane() throws FileNotFoundException, RuntimeException {
        matchSound = new AudioClip(getClass().getResource("/beep.mp3").toString());
        notMatch = new AudioClip(getClass().getResource("/chime.mp3").toString());
        victory = new AudioClip(getClass().getResource("/bvictory.mp3").toString());
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                long elapsedMillis = (now - startTime) / 1_000_000;
                if (elapsedMillis >= 800) {
                    try {
                        checkMatch();
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    timer.stop();
                }

            }
        };
        cgp = new CardGridPane(this);
        cgp.setCardSize(100);
        cgp.createCardImageList(64);
        cgp.setCardImages();
        HBox turnBox = new HBox();
        turns = new Label("turns: " + numMatched);
        turns.setPrefSize(50, 50);
        turnBox.setAlignment(Pos.CENTER);
        turnBox.getChildren().add(turns);
        statusPane = new HBox();
        commandPane = new HBox();
        ObservableList<String> levels = FXCollections.observableArrayList("level one", "level two", "level three", "level four", "level five", "level six");
        menu = new ComboBox(levels);
        menu.setBackground(Background.fill(rgb(196, 195, 178)));
        menu.setPromptText("levels");
        menu.setOnAction(e-> {
             level = menu.valueProperty().get();
            if (level.equals("level one")) {
                rows = 2;
                cols = 3;
            } else if (level.equals("level two")){
                rows = 2;
                cols = 4;

            } else if (level.equals("level three")){
                rows = 4;
                cols = 4;
            } else if (level.equals("level four")){
                rows = 4;
                cols = 6;

            } else if (level.equals("level five")){
                rows = 6;
                cols = 6;
            } else {
                rows = 8;
                cols = 8;
            }
            try {
                numClicks = 0;
                turns.setText("turns: " + numMatched);
                cgp.initCards(rows, cols);
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }

        });

        exitButton = new Button("exit");
        exitButton.setBackground(Background.fill(rgb(196, 195, 178)));
        exitButton.setOnAction(e -> {
            Stage stage = (Stage) exitButton.getScene().getWindow();
            stage.close();
        });
        newGame = new Button("new game");
        newGame.setBackground(Background.fill(rgb(196, 195, 178)));
        newGame.setOnMousePressed( event -> {
            try {
                newGame();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        commandPane.getChildren().addAll(menu, newGame, exitButton);
        commandPane.setAlignment(Pos.CENTER);
        commandPane.setSpacing(10);
        this.setTop(turnBox);
        this.setCenter(cgp);
        this.setBottom(commandPane);
        this.setBackground(Background.fill(rgb(223, 222, 209)));

    }


    /**
     * constructor for GamePane
     * initializes all the fields and adds some of them to the borderpane
     * also holds the handle() method for the animation timer
     * and the event handlers for the combobox, newGame button, and exitButton button
     * puts a user defined cardSize into the arguments of the CardGridPane constructor
     * @param cardSize
     * @throws FileNotFoundException, RuntimeException
     */
    public GamePane(int cardSize) throws FileNotFoundException, RuntimeException {
        matchSound = new AudioClip(getClass().getResource("/beep.mp3").toString());
        notMatch = new AudioClip(getClass().getResource("/chime.mp3").toString());
        victory = new AudioClip(getClass().getResource("/bvictory.mp3").toString());
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                long elapsedMillis = (now - startTime) / 1_000_000;
                if (elapsedMillis >= 800) {
                    try {
                        checkMatch();
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    timer.stop();
                }

            }
        };
        cgp = new CardGridPane(this);
        cgp.setCardSize(cardSize);
        cgp.createCardImageList(64);
        cgp.setCardImages();
        HBox turnBox = new HBox();
        turns = new Label("turns: " + numMatched);
        turns.setPrefSize(50, 50);
        turnBox.setAlignment(Pos.CENTER);
        turnBox.getChildren().add(turns);
        statusPane = new HBox();
        commandPane = new HBox();
        ObservableList<String> levels = FXCollections.observableArrayList("level one", "level two", "level three", "level four", "level five", "level six");
        menu = new ComboBox(levels);
        menu.setPromptText("levels");
        menu.setOnAction(e-> {
            level = menu.valueProperty().get();
            if (level.equals("level one")) {
                rows = 2;
                cols = 3;
            } else if (level.equals("level two")){
                rows = 2;
                cols = 4;

            } else if (level.equals("level three")){
                rows = 4;
                cols = 4;
            } else if (level.equals("level four")){
                rows = 4;
                cols = 6;

            } else if (level.equals("level five")){
                rows = 6;
                cols = 6;
            } else {
                rows = 8;
                cols = 8;
            }
            try {
                numClicks = 0;
                turns.setText("turns: " + numMatched);
                cgp.initCards(rows, cols);
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }

        });

        exitButton = new Button("exit");
        exitButton.setOnAction(e -> {
            Stage stage = (Stage) exitButton.getScene().getWindow();
            stage.close();
        });
        newGame = new Button("new game");
        newGame.setOnMousePressed( event -> {
            try {
                newGame();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        commandPane.getChildren().addAll(menu, newGame, exitButton);
        commandPane.setAlignment(Pos.CENTER);
        this.setTop(turnBox);
        this.setCenter(cgp);
        this.setBottom(commandPane);
        this.setBackground(Background.fill(rgb(223, 222, 209)));
    }


    /**
     * uses same logic from combobox level choosing to generate a new game at the same level
     * uses the initGame method from CardGridPane to reset the grid and update the game state
     * also sets the numClicked and numMatched back to zero for the next game
     * @throws FileNotFoundException
     */
    public void newGame() throws FileNotFoundException {
        level = menu.valueProperty().get();
        if (level == null){
            rows = 8;
            cols = 8;
        }
        else if (level.equals("level one")) {
            rows = 2;
            cols = 3;

        } else if (level.equals("level two")){
            rows = 2;
            cols = 4;

        } else if (level.equals("level three")){
            rows = 4;
            cols = 4;

        } else if (level.equals("level four")){
            rows = 4;
            cols = 6;

        } else if (level.equals("level five")){
            rows = 6;
            cols = 6;

        } else if (level.equals("level six")){
            rows = 8;
            cols = 8;
        }

        try {
            cgp.initCards(rows, cols);
        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        }

        numClicks = 0;
        numMatched = 0;
        turns.setText("turns: " + numMatched);

    }


    /**
     * handles logic when a card is clicked by the user.
     * prevents clicks during a match check and makes sure that only unmatched cards are clickable.
     * @param clickedCard
     */
    public void handleCardClicked(Card clickedCard){
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

    /**
     * compares the two most recently clicked cards to see if they match.
     *  If they match, they're disabled and marked as matched.
     *  If not, they're flipped back, according to the animationtimers handle method
     *  Resets the click counter and updates the turn label.
     * @throws FileNotFoundException
     */
    public void checkMatch() throws FileNotFoundException {
        if (clickedCardOne.getPath().equals(clickedCardTwo.getPath())) {
            clickedCardOne.setMatched(true);
            clickedCardTwo.setMatched(true);
            clickedCardOne.setVisible(false);
            clickedCardTwo.setVisible(false);
            numMatched += 1;
            matchSound.play();
        } else {
            clickedCardOne.setFlipped(false);
            clickedCardOne.flipCard();
            clickedCardTwo.setFlipped(false);
            clickedCardTwo.flipCard();
            notMatch.play();

        }
        if (numMatched == (cgp.getCurrentRows() * cgp.getCurrentCols() / 2)){
            gameOver();

        }
        numClicks = 0;
        turns.setText("turns: " + numMatched);
    }

    /**
     * uses the alert javafx class to create a popup message that shows when all of the cards have been matched
     * provides options to start a new game at the same level or exit the game
     * also plays victory music
     */
    public void gameOver() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "all cards matched! ");
            alert.setTitle("winner");
            ButtonType newGameBT = new ButtonType("new game");
            ButtonType exitGameBT = new ButtonType("exit");
            alert.getButtonTypes().setAll(newGameBT, exitGameBT);
            victory.play();
            alert.showAndWait().ifPresent(response -> {
                if (response == newGameBT) {
                    try {
                        newGame();
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                } else if (response == exitGameBT){
                    Stage stageBig = (Stage) exitButton.getScene().getWindow();
                    stageBig.close();
                }
            });
            victory.stop();
        });
    }


}

