/**
 *  @author Zuri McKee
 *  Mason section 4
 *  4/22/2025
 * CardGridPane represents the grid layout of cards used in the Concentration game.
 * prepares a shuffled image path list for each game, sets image paths on cards, resets the grid for new games,
 * and attaches event listeners to individual cards for gameplay interaction.
 *
 **/
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;

public class CardGridPane extends GridPane{
    private Card [][] cards; //(2d array of Card objects)
    private ArrayList<String> cardsList; // The pathnames of all the available card images.
    private final int MAXROWS = 8; //the number of GridPane rows (8)
    private final int MAXCOLS = 8; //the number of GridPane columns (8).
    private int currentRows; //the actual number of rows and columns used in the current game as set by the user selected level.
    private int currentCols;
    private int cardSize; // the sidelength in pixels of the square card.
    private GamePane gamePane;

    /**
     * no arg constructor that initializes all of the fields and populates the cards arraylist
     * also sets the grid guidelines for the game interface, like the number of rows/cols
     * @throws FileNotFoundException
     */
    public CardGridPane() throws FileNotFoundException {
        this.setGridLinesVisible(true);
        cardsList = new ArrayList<>();
        cards = new Card[MAXROWS][MAXCOLS];

        setCurrentRows(MAXROWS);
        setCurrentCols(MAXCOLS);
        for(int i = 0; i< MAXROWS; i++){
            for(int j = 0; j< MAXCOLS; j++){
                Card card = new Card();
                cards[i][j] = card;
                cards[i][j].setCardAndImageSize(64, 64);
                //card.setFlipped(false);
                card.setOnMousePressed(event -> {
                    card.flipCard();
                    //gp.handleCardClicked(card);
                });
                this.add(card, i, j);

            }
        }
        createCardImageList(MAXROWS * MAXCOLS);
        setCardImages();
    }

    /**
     * constructor takes gp as a parameter because I needed to do that so that the matching functionality
     * would work, if not it would have been recursive.
     * @param gp
     * @throws FileNotFoundException
     */
    public CardGridPane(GamePane gp) throws FileNotFoundException {
        this.gamePane = gp;
        this.setGridLinesVisible(true);
        cardsList = new ArrayList<>();
        cards = new Card[MAXROWS][MAXCOLS];

        setCurrentRows(MAXROWS);
        setCurrentCols(MAXCOLS);
        for(int i = 0; i< MAXROWS; i++){
            for(int j = 0; j< MAXCOLS; j++){
                Card card = new Card();
                cards[i][j] = card;
                cards[i][j].setCardAndImageSize(64, 64);
                //card.setFlipped(false);
                card.setOnMousePressed(event -> {
                    //card.flipCard();
                    gp.handleCardClicked(card);
                });
                this.add(card, i, j);

            }
        }
        createCardImageList(MAXROWS * MAXCOLS);
        setCardImages();
    }


    /**
     * same as all of the other constructors but takes cardSize as a param too so that the user can dictate the
     * size of the cards on the screen
     * @param cardSize
     * @param gp
     * @throws FileNotFoundException
     */
    public CardGridPane(int cardSize, GamePane gp) throws FileNotFoundException {
        this.setGridLinesVisible(true);
        this.gamePane = gp;
        cardsList = new ArrayList<>();
        cards = new Card[MAXROWS][MAXCOLS];
        setCurrentRows(MAXROWS);
        setCurrentCols(MAXCOLS);
        for (int i = 0; i < MAXROWS; i++) {
            for (int j = 0; j < MAXCOLS; j++) {
                Card card = new Card();
                cards[i][j] = card;
                cards[i][j].setCardAndImageSize(cardSize, cardSize);
                //card.setFlipped(false);
                card.setOnMousePressed(event -> {
                    //card.flipCard();
                    gp.handleCardClicked(card);


                });
                this.add(card, i, j);
            }
        }
        createCardImageList(MAXROWS * MAXCOLS);
        setCardImages();


    }


    /***
     * sets the card images from the paths in the cardsList arraylist so that they'll show up accordingly
     * on the screen
     * @throws FileNotFoundException
     */
    public void setCardImages() throws FileNotFoundException {
        createCardImageList(MAXROWS * MAXCOLS);
        int index = 0;
        for (int i = 0; i < currentRows; i++) {
            for (int j = 0; j < currentCols; j++) {
                if (index < cardsList.size()) {
                    cards[i][j].setPath(cardsList.get(index));
                    index++;
                }
            }
        }

    }

    /**
     * shuffles the cardsList arraylist so that all of the pairs aren't just next to each other
     */
    public void shuffleImages(){
        Collections.shuffle(cardsList);
    }

    /**
     * returns the card at that index in the cards array
     * @param r row index
     * @param c cols index
     * @return Card
     */
    public Card getCard(int r, int c){
        return cards[r][c];
    }


    /**
     * takes the rows and cols params and creates a new grid with those dimensions
     * clears the board and creates a new grid of cards with the requested dimensions
     * @param rows
     * @param cols
     * @throws FileNotFoundException
     */
    public void initCards(int rows, int cols) throws FileNotFoundException {

        this.getChildren().clear();
        setCurrentRows(rows);
        setCurrentCols(cols);

        createCardImageList(rows * cols);
        shuffleImages();

        cards = new Card[rows][cols];
        for(int i=0;i<rows;i++){
            for(int j=0;j<cols;j++){
                Card card = new Card();
                cards[i][j] = card;
                card.setCardAndImageSize(cardSize, cardSize);
                //card.setFlipped(false);
                card.setVisible(true);
                card.setDisable(false);
                card.setOnMousePressed(event -> {
                    //card.flipCard();
                    this.gamePane.handleCardClicked(card);
                });
                this.add(card, j, i);
            }
        }

        setCardImages();

    }

    /**
     * creates the arraylist of image paths and then shuffles them
     * @param size
     */
    public void createCardImageList(int size){
        int counter = 0;
        cardsList = new ArrayList<>();
        //cardsList.add("/resources/fish.png");
        for(int i=0;i < (size / 2); i++){
            cardsList.add("/projimages/image_" + counter + ".jpg");
            cardsList.add("/projimages/image_" + counter + ".jpg");
            counter++;
        }
        shuffleImages();

    }


    //getters and setters to get the number of rows/cols of the current grid, and also to get the card size
    public int getMAXROWS() {
        return MAXROWS;
    }

    public int getMAXCOLS() {
        return MAXCOLS;
    }

    public int getCurrentRows() {
        return currentRows;
    }

    public void setCurrentRows(int currentRows) {
        this.currentRows = currentRows;
    }

    public int getCurrentCols() {
        return currentCols;
    }

    public void setCurrentCols(int currentCols) {
        this.currentCols = currentCols;
    }

    public void setCardSize(int cs){
        this.cardSize = cs;
    }

    public int getCardSize(){
        return cardSize;
    }

}
