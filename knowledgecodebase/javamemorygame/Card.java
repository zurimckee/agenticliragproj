/**
 *  @author Zuri McKee
 *  Mason section 4
 *  4/22/2025
 *  Represents a single card in the Concentration game.
 *  Each card displays either its face image or a back image.
 *  Cards are visually represented using an ImageView which updates when the card is flipped.
 *
 **/


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import java.io.FileNotFoundException;


public class Card extends StackPane {
    private ImageView iv;
    private boolean flipped; //indicates whether the symbol is displayed (true) or the back of the card is displayed (false).
    private boolean matched; // indicates if the card has been matched.
    private String path; // contains the file path of the image.
    private Image image; //stores the actual image object.  image is displayed using an ImageView object
    private int row;
    private int col;
    private int numRows;
    private int numCols;
    private static final Image backPic = new Image(Card.class.getResourceAsStream("/projimages/backPic.jpg")); //image shown on the back of the card


    /**
     * Default constructor. Initializes the card with the back image.
     * initializes all other fields to java defaults
     */
    public Card(){
        flipped = false;
        matched = false;
        this.image = null;
        this.iv = new ImageView(backPic);
        this.row = 0;
        this.col = 0;
        this.numRows = 0;
        this.numCols = 0;
        this.getChildren().add(this.iv);
//        this.setDisable(true);
//        this.setVisible(false);
    }

    /**
     * Constructs a card with a specific image path for the front.
     * @param path The path to the image to display on the front of the card.
     */
    public Card(String path){
        this.path = path;
        flipped = false;
        matched = false;
        this.image = null;
        this.iv = new ImageView(backPic);
        this.row = 0;
        this.col = 0;
        this.numRows = 0;
        this.numCols = 0;
        this.getChildren().add(this.iv);
    }


    /**
     * Returns whether the card is currently flipped to show the front image.
     * @return true if flipped, false if showing back.
     */
    public boolean isFlipped() {
        return flipped;
    }

    /**
     * Sets the flipped status of the card.
     * @param flipped true to show the front image, false to show back.
     */
    public void setFlipped(boolean flipped) {
        this.flipped = flipped;
        flipCard();
    }


    /**
     * Flips the card by toggling between front and back image.
     */
    public void flipCard() {
        flipped = !flipped;
        if (flipped) {
            iv.setImage(image);
        } else {
            iv.setImage(backPic);
        }
    }


    /**
     * Returns whether the card has been matched.
     * @return true if matched.
     */
    public Boolean getMatched(){
        return matched;
    }

    /**
     * Sets the matched status of the card.
     * @param matched true if the card has been matched.
     */
    public void setMatched(boolean matched) {
        this.matched = matched;
    }

    /**
     * Returns the file path of the front image.
     * @return the image path.
     */
    public String getPath() {
        return path;
    }

    /**
     * Returns the Image object used on the front of the card.
     * @return the front image.
     */
    public Image getImage() {
        return image;
    }

    /**
     * Sets the Image object for the card's front face.
     * @param image the Image to set.
     */
    public void setImage(Image image) {
        this.image = image;
    }

    /**
     * Gets the number of rows.
     * @return number of rows.
     */
    public int getNumRows() {
        return numRows;
    }

    /**
     * Sets the number of rows in the full grid
     * @param numRows total rows.
     */
    public void setNumRows(int numRows) {
        this.numRows = numRows;
    }

    /**
     * Gets the number of columns.
     * @return number of columns.
     */
    public int getNumCols() {
        return numCols;
    }

    /**
     * Sets the number of columns in the full grid (optional feature support).
     * @param numCols total columns.
     */
    public void setNumCols(int numCols) {
        this.numCols = numCols;
    }

    /**
     * Gets the row index of the card in the grid.
     * @return grid row index.
     */
    public int getRow() {
        return row;
    }

    /**
     * Sets the row index of the card in the grid.
     * @param row row index.
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * Gets the column index of the card in the grid.
     * @return grid column index.
     */
    public int getCol() {
        return col;
    }

    /**
     * Sets the column index of the card in the grid.
     * @param col column index.
     */
    public void setCol(int col) {
        this.col = col;
    }



    /**
     * Sets both the card's visual size and its ImageView dimensions.
     * @param width desired card width.
     * @param height desired card height.
     */
    public void setCardAndImageSize(int width, int height){
        this.setPrefWidth(width);
        this.setPrefHeight(height);
    }

    /**
     * Sets the file path for the card's front image and loads the image.
     * Also ensures the ImageView is initialized with the back image.
     * @param path path to the front image file.
     * @throws FileNotFoundException if the image file is not found.
     */
    public void setPath(String path) throws FileNotFoundException {
        //InputStream stream = getClass().getResourceAsStream(path);
        this.path = path;
        this.image = new Image(path);
        if(iv == null){
            iv = new ImageView(this.image);
            this.getChildren().add(iv);
        }
        iv.setImage(backPic);
        this.setCardAndImageSize((int) this.getWidth(), (int) this.getHeight());
    }

    /**
     * Sets the card as matched.
     */
    public void setMatched(){
        this.matched = true;
    }

    /**
     * Sets the card's row and column in the grid.
     * @param r row index.
     * @param c column index.
     */
    public void setGridPos(int r, int c){
        this.setRow(r);
        this.setCol(c);
    }

    /**
     * Sets the total rows and columns of the grid this card belongs to.
     * @param nr number of rows.
     * @param nc number of columns.
     */
    public void setGridSize(int nr, int nc){
        this.setNumRows(nr);
        this.setNumCols(nc);
    }


}
