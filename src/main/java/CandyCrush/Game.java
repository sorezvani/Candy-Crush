package CandyCrush;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static javafx.geometry.Pos.CENTER;

public class Game extends Application {
    GridPane gridPane = new GridPane();
    GridPane heartGrid = new GridPane();
    Group scoreGroup = new Group();
    Group homeGroup = new Group();
    Group endGroup = new Group();
    Group scoreEnd = new Group();
    Group group = new Group();
    HBox logo;
    private int previousHeart;
    private int numRows;
    private int numCols;
    private int selectedRow1 = -1;
    private int selectedCol1 = -1;
    private int selectedRow2 = -1;
    private int selectedCol2 = -1;
    Board board;
    Button[][] boardBtn;
    public void set (int numRows, int numCols , String name) {
        this.numRows = numRows;
        this.numCols = numCols;
        boardBtn = new Button[numRows][numCols];
        board = new Board(name,numRows,numCols);
        generateBoard();
        group.getChildren().addAll(gridPane,heartGrid,scoreGroup,homeGroup ,endGroup);

        check();
    }
    public ImageView imageView(int number) {
        switch (number) {
            case (1): {
                try {
                    return new ImageView(new Image(new FileInputStream("src/main/resources/image/Candy1.png")));
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
            case (2): {
                try {
                    return new ImageView(new Image(new FileInputStream("src/main/resources/image/Candy2.png")));
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
            case (3): {
                try {
                    return new ImageView(new Image(new FileInputStream("src/main/resources/image/Candy3.png")));
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
            case (4): {
                try {
                    return new ImageView(new Image(new FileInputStream("src/main/resources/image/Candy4.png")));
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
            case (5): {
                try {
                    return new ImageView(new Image(new FileInputStream("src/main/resources/image/Candy5.png")));
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return null;
    }
    public void generateBoard () {
        if (numCols == 6 && numRows == 6) {
            gridPane.setLayoutX(325);
            gridPane.setLayoutY(70);
        } else if (numCols == 7 && numRows == 7) {
            gridPane.setLayoutX(295);
            gridPane.setLayoutY(40);
        } else {
            gridPane.setLayoutX(265);
            gridPane.setLayoutY(10);
        }

        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                Button button = new Button();
                button.setGraphic(imageView(board.getBoardElement(row,col)));
                button.setBackground(null);
                gridPane.add(button,col,row);
                boardBtn[row][col] = button;
            }
        }
    }
    public void heart () {
        heartGrid.getChildren().clear();
        int hearts = board.player.getHeart();
        ImageView imageView;
        if(previousHeart > hearts) {
            ImageView flyImage;
            if (hearts < 5) {
                try {
                    flyImage = new ImageView(new Image(new FileInputStream("src/main/resources/image/Heart Flying.png")));
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                for (int i = 0; i < hearts; i++) {
                    try {
                        imageView = new ImageView(new Image(new FileInputStream("src/main/resources/image/Heart.png")));
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    Label label = new Label("", imageView);
                    heartGrid.add(label, i, 0);
                }
                Label fly = new Label("",flyImage);
                TranslateTransition translate = new TranslateTransition();
                translate.setByY(-50);
                translate.setDuration(Duration.millis(1000));
                translate.setNode(fly);
                translate.play();

                heartGrid.add(fly,hearts,0);
            } else {
                try {
                    flyImage = new ImageView(new Image(new FileInputStream("src/main/resources/image/Heart Flying.png")));
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                try {
                    imageView = new ImageView(new Image(new FileInputStream("src/main/resources/image/Heart.png")));
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                Label label = new Label("", imageView);
                Text text = new Text(" X " + hearts);
                text.setFill(Color.rgb(219, 52, 51));
                text.setFont(Font.font("Lucid Sans Unicode", FontPosture.REGULAR, 40));
                heartGrid.add(label, 0, 0);
                heartGrid.add(text, 1, 0);

                Label fly = new Label("",flyImage);
                TranslateTransition translate = new TranslateTransition();
                translate.setByY(-50);
                translate.setDuration(Duration.millis(1000));
                translate.setNode(fly);
                translate.play();

                heartGrid.add(fly,0,0);
            }
        } else {
            if (hearts < 5) {
                for (int i = 0; i < hearts; i++) {
                    try {
                        imageView = new ImageView(new Image(new FileInputStream("src/main/resources/image/Heart.png")));
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    Label label = new Label("", imageView);
                    heartGrid.add(label, i, 0);
                }
            } else {
                try {
                    imageView = new ImageView(new Image(new FileInputStream("src/main/resources/image/Heart.png")));
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                Label label = new Label("", imageView);
                Text text = new Text(" X " + hearts);
                text.setFill(Color.rgb(219, 52, 51));
                text.setFont(Font.font("Lucid Sans Unicode", FontPosture.REGULAR, 40));
                heartGrid.add(label, 0, 0);
                heartGrid.add(text, 1, 0);
            }
        }
        previousHeart = hearts;
    }
    public void all () {
        resetNumber();
        home();
        endButton();
        heart();
        score();
    }
    public void check () {
        if (checkEndGame()) makeGameEnd();
        all();
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                Button btn = boardBtn[row][col];
                btn.setGraphic(imageView(board.getBoardElement(row,col)));
                int currRow = row;
                int currCol = col;
                btn.setOnMouseClicked(e -> {
                    if (selectedRow1 == -1 && selectedCol1 == -1) {
                        selectedRow1 = currRow;
                        selectedCol1 = currCol;
                    } else {
                        if (selectedRow2 == -1 && selectedCol2 == -1) {
                            if(selectedRow1 == currRow && selectedCol1 == currCol) {
                                resetNumber();
                            }else {
                                selectedRow2 = currRow;
                                selectedCol2 = currCol;
                                board.playerMove(selectedRow1, selectedCol1, selectedRow2, selectedCol2);
                                check();
                            }
                        } else {
                            selectedRow1 = currRow;
                            selectedCol1 = currCol;
                            selectedRow2 = -1;
                            selectedCol2 = -1;
                        }
                    }
                });
            }
        }
    }
    public void setBackGround() {
        HBox backGround;
        try {
            backGround = new HBox(new ImageView(new Image(new FileInputStream("src/main/resources/image/BackGround.png"))));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        group.getChildren().add(backGround);
    }
    public void setLogo() {
        try {
            logo = new HBox(new ImageView(new Image(new FileInputStream("src/main/resources/image/Candy Crush Logo.png"))));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        logo.setLayoutX(315);
        group.getChildren().add(logo);
    }
    public void resetNumber() {
        selectedRow1 = -1;
        selectedCol1 = -1;
        selectedRow2 = -1;
        selectedCol2 = -1;
    }
    public void startOfTheGame()  {
        setLogo();
        Button startButton = new Button("Start");
        startButton.setFont(Font.font("Comic Sans MS", FontPosture.REGULAR, 15));
        startButton.setPrefSize(140 , 30);

        Button topScore = new Button("Top Score");
        topScore.setFont(Font.font("Comic Sans MS", FontPosture.REGULAR, 15));
        topScore.setPrefSize(140 , 30);

        Button credit = new Button("Credit");
        credit.setFont(Font.font("Comic Sans MS", FontPosture.REGULAR, 15));
        credit.setPrefSize(140 , 30);

        Button exit = new Button("Exit");
        exit.setFont(Font.font("Comic Sans MS", FontPosture.REGULAR, 15));
        exit.setPrefSize(140 , 30);

        VBox buttons = new VBox(2 , startButton , topScore , credit ,exit);
        buttons.setLayoutX(160);
        buttons.setLayoutY(230);

        startButton.setOnMouseClicked(event -> {
            group.getChildren().remove(buttons);
            startButton();
        });

        credit.setOnMouseClicked(event -> {
            group.getChildren().removeAll(buttons , logo);
            creditButton();
        });

        exit.setOnAction(event -> {
            group.getChildren().remove(buttons);
            exitButton();
        });
        topScore.setOnMouseClicked(event -> {
            group.getChildren().removeAll(buttons,logo);
            topScoreButton();
        });

        group.getChildren().add(buttons);

    }
    private void topScoreButton() {
        Player.top5();
        HBox backGround;
        try {
            backGround = new HBox(new ImageView(new Image(new FileInputStream("src/main/resources/image/LeaderBoard.png"))));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Group topGroup = new Group();
        VBox vBoxName = new VBox(43);
        VBox vBoxScore = new VBox(43);
        for (int i =0 ; i < 5 ; i++ ){
            Text topNameText = new Text();
            topNameText.setText(Player.topName[i]);
            topNameText.setFont(Font.font("Comic Sans MS", FontPosture.REGULAR, 18));

            Text topScoreText = new Text();
            topScoreText.setText(String.valueOf(Player.topScore[i]));
            topScoreText.setFont(Font.font("Comic Sans MS", FontPosture.REGULAR, 18));

            vBoxName.getChildren().add(topNameText);
            vBoxScore.getChildren().add(topScoreText);
        }
        vBoxName.setLayoutX(120);
        vBoxName.setLayoutY(110);
        vBoxScore.setLayoutX(260);
        vBoxScore.setLayoutY(110);

        Button returnButton = new Button();
        returnButton.setPrefSize(120,52);
        try {
            returnButton.setGraphic(new ImageView(new Image(new FileInputStream("src/main/resources/image/Return.png"))));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        returnButton.setLayoutX(137);
        returnButton.setLayoutY(460);

        returnButton.setOnMouseClicked(event -> {
            topGroup.getChildren().clear();
            gridPane.getChildren().clear();
            heartGrid.getChildren().clear();
            homeGroup.getChildren().clear();
            scoreGroup.getChildren().clear();
            endGroup.getChildren().clear();

            startOfTheGame();
        });

        topGroup.getChildren().addAll(backGround ,vBoxName , vBoxScore , returnButton);
        topGroup.setLayoutX(250);
        topGroup.setLayoutY(20);

        group.getChildren().add(topGroup);
    }
    private void creditButton() {
        Group credit;
        try {
           credit = new Group(new ImageView(new Image(new FileInputStream("src/main/resources/image/Credit.png"))));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        credit.setLayoutX(200);
        credit.setLayoutY(30);

        Button returnButton = new Button();
        returnButton.setPrefSize(120,52);
        try {
            returnButton.setGraphic(new ImageView(new Image(new FileInputStream("src/main/resources/image/Return.png"))));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        returnButton.setLayoutX(170);
        returnButton.setLayoutY(440);

        returnButton.setOnMouseClicked(event -> {
            credit.getChildren().clear();
            startOfTheGame();
        });

        credit.getChildren().add(returnButton);

        group.getChildren().addAll(credit);
    }
    public void startButton() {

        Text nameText = new Text("Name : ");
        nameText.setFont(Font.font("Comic Sans MS", FontPosture.REGULAR, 17));
        TextField nameTextField = new TextField();
        nameTextField.setPromptText("Enter your name");
        nameTextField.setAlignment(CENTER);

        Text labelEmpty = new Text("Please fill everything");
        labelEmpty.setFont(Font.font("Comic Sans MS", FontPosture.REGULAR, 17));
        labelEmpty.setFill(Color.RED);
        labelEmpty.setStroke(Color.DIMGREY);
        labelEmpty.setStrokeWidth(0.7);
        labelEmpty.setVisible(false);
        labelEmpty.setLayoutX(173);
        labelEmpty.setLayoutY(358);

        String[] sizes = {" 6 X 6 "," 7 X 7 "," 8 X 8 "};

        ChoiceBox choiceBox = new ChoiceBox(FXCollections.observableArrayList(sizes));

        Text sizeText = new Text("Size : ");
        sizeText.setFont(Font.font("Comic Sans MS", FontPosture.REGULAR, 17));

        Button start = new Button("Start");
        start.setPrefSize(60,22);

        Button homeButton= new Button();
        homeButton.setBackground(null);

        ImageView view;
        try {
            view = new ImageView(new Image(new FileInputStream("src/main/resources/image/HomeLogo.png")));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        homeButton.setGraphic(view);

        Text home = new Text("Home");
        home.setFont(Font.font("Verdana", FontPosture.REGULAR,13));
        home.setLayoutX(28);
        home.setLayoutY(80);

        Group backButton = new Group(homeButton,home);
        backButton.setLayoutY(470);

        HBox nameBox = new HBox(15 , nameText, nameTextField);
        HBox sizeBox = new HBox(25 , sizeText , choiceBox , start);

        VBox vBox = new VBox(10 , nameBox , sizeBox);
        vBox.setPadding(new Insets(270, 40, 50, 140));


        start.setOnMouseClicked(event -> {
            if(!nameTextField.getText().isEmpty() && choiceBox.getValue() != null){
                labelEmpty.setVisible(false);
                group.getChildren().removeAll(labelEmpty,vBox,logo,backButton);
                int number = choiceBox.getSelectionModel().getSelectedIndex()+6;
                set(number,number,nameTextField.getText());

            }else {
                labelEmpty.setVisible(true);
            }
        });

        homeButton.setOnMouseClicked(event -> {
            group.getChildren().removeAll(vBox , labelEmpty , backButton ,logo);
            startOfTheGame();
        });

        group.getChildren().addAll(vBox , labelEmpty , backButton);
    }
    public void score() {
        scoreGroup.getChildren().clear();
        scoreGroup.setLayoutY(80);
        scoreGroup.setLayoutX(15);
        int score = board.player.getScore();

        Text scoreText = new Text("Score : ");
        scoreText.setLayoutY(20);
        scoreText.setFont(Font.font("Comic Sans MS", FontPosture.REGULAR, 25));

        Text scoreNumber = new Text();
        scoreNumber.setText(String.valueOf(score));
        scoreNumber.setFont(Font.font("Comic Sans MS", FontPosture.REGULAR, 25));

        Rectangle rectangle = new Rectangle(132,40);
        rectangle.setFill(null);
        rectangle.setStroke(Color.RED);
        rectangle.setStrokeWidth(2);
        rectangle.setArcHeight(20);
        rectangle.setArcWidth(20);

        StackPane stackPane = new StackPane(rectangle , scoreNumber);
        stackPane.setLayoutY(35);
        stackPane.setLayoutX(20);

        scoreGroup.getChildren().addAll(scoreText , stackPane);
    }
    public void home () {
        homeGroup.getChildren().clear();

        Button homeButton= new Button();
        homeButton.setBackground(null);

        ImageView view;
        try {
            view = new ImageView(new Image(new FileInputStream("src/main/resources/image/HomeLogo.png")));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        homeButton.setGraphic(view);

        Text home = new Text("Home");
        home.setFont(Font.font("Verdana", FontPosture.REGULAR,13));
        home.setLayoutX(28);
        home.setLayoutY(80);

        homeGroup.getChildren().addAll(homeButton,home);

        homeButton.setOnMouseClicked(event -> {
            group.getChildren().removeAll(gridPane,scoreGroup,heartGrid , homeGroup,scoreEnd ,endGroup);
            homeButton();
        });

        homeGroup.setLayoutY(470);

    }
    public void exitButton() {
        Text areYouSure = new Text("are You Sure?");
        areYouSure.setFont(Font.font("Impact", FontPosture.REGULAR, 28));
        Button yes = new Button("Yes");
        yes.setPrefSize(60 , 30);
        yes.setFont(Font.font("Comic Sans MS", FontPosture.REGULAR, 17));
        Button no = new Button("No");
        no.setPrefSize(60 , 30);
        no.setFont(Font.font("Comic Sans MS", FontPosture.REGULAR, 17));

        HBox yesAndNo = new HBox(32 , yes , no);
        VBox full = new VBox(20 , areYouSure , yesAndNo);

        full.setLayoutX(370);
        full.setLayoutY(260);

        yes.setOnAction(eventAction -> {
            group.getChildren().removeAll(full,logo);
            Text ty = new Text("Thanks For Playing");
            ty.setFont(Font.font("Comic Sans MS", FontPosture.REGULAR, 30));
            ty.setLayoutX(320);
            ty.setLayoutY(270);
            group.getChildren().add(ty);
            ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
            executorService.schedule(this::exx , 1, TimeUnit.SECONDS);
        });
        no.setOnAction(eventAction -> {
            group.getChildren().removeAll(full,logo);
            startOfTheGame();
        });
        group.getChildren().add(full);
    }
    public void endButton () {
        endGroup.getChildren().clear();

        Button endButton = new Button();
        endButton.setBackground(null);

        ImageView view;
        try {
            view = new ImageView(new Image(new FileInputStream("src/main/resources/image/Back.png")));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        endButton.setGraphic(view);

        Text end = new Text("End");
        end.setFont(Font.font("Verdana", FontPosture.REGULAR,14));
        end.setLayoutX(35);
        end.setLayoutY(80);

        endGroup.getChildren().addAll(endButton,end);

        endButton.setOnMouseClicked(event -> {
            makeGameEnd();
        });

        endGroup.setLayoutY(470);
        endGroup.setLayoutX(80);
    }
    public void homeButton () {

        Group home = new Group();

        Text areYouSure = new Text("Do you want to Go back to main menu ?");
        areYouSure.setFont(Font.font("Impact", FontPosture.REGULAR, 25));

        Text a = new Text("All your progress will be lost");
        a.setFont(Font.font("Verdana", FontPosture.REGULAR, 14));
        a.setStroke(Color.RED);
        a.setStrokeWidth(0.5);
        a.setLayoutY(23);
        a.setLayoutX(90);

        Button yes = new Button("Yes");
        yes.setPrefSize(70 , 20);
        yes.setFont(Font.font("Comic Sans MS", FontPosture.REGULAR, 15));

        Button no = new Button("No");
        no.setPrefSize(70 , 20);
        no.setFont(Font.font("Comic Sans MS", FontPosture.REGULAR, 15));

        HBox buttons = new HBox(30 , yes , no);
        buttons.setLayoutX(104);
        buttons.setLayoutY(33);

        home.getChildren().addAll(areYouSure,a,buttons);
        home.setLayoutX(100);
        home.setLayoutY(100);

        yes.setOnAction(eventAction -> {
            group.getChildren().remove(home);
            gridPane.getChildren().clear();
            startOfTheGame();
        });
        no.setOnAction(eventAction -> {
            group.getChildren().remove(home);
            group.getChildren().addAll(gridPane,scoreGroup,heartGrid,homeGroup,endGroup);
            check();
        });
        home.setLayoutX(240);
        home.setLayoutY(240);
        group.getChildren().add(home);
    }
    public boolean checkEndGame () {
        return board.hasNotPossibleMove() || board.player.getHeart() == 0;
    }
    public void makeGameEnd() {
        group.getChildren().removeAll(gridPane,heartGrid,scoreGroup ,homeGroup, scoreEnd , endGroup);
        board.player.endGame();
        finalScore();
    }
    public void finalScore() {
        scoreEnd.getChildren().clear();
        Group endBox;
        try {
            endBox = new Group(new ImageView(new Image(new FileInputStream("src/main/resources/image/Final Score Template.png"))));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Button score = new Button(String.valueOf(board.player.getScore()));
        score.setFont(Font.font("Impact", FontPosture.REGULAR, 30));
        score.setTextFill(Color.rgb(68,0,100));
        score.setMinSize(201,57);
        score.setLayoutX(127);
        score.setLayoutY(190);
        score.setBackground(null);

        Button returnButton = new Button();
        returnButton.setPrefSize(120,52);
        try {
            returnButton.setGraphic(new ImageView(new Image(new FileInputStream("src/main/resources/image/Return.png"))));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        returnButton.setLayoutX(170);
        returnButton.setLayoutY(440);

        returnButton.setOnMouseClicked(event -> {
            endBox.getChildren().clear();
            gridPane.getChildren().clear();
            heartGrid.getChildren().clear();
            homeGroup.getChildren().clear();
            scoreGroup.getChildren().clear();
            endGroup.getChildren().clear();

            startOfTheGame();
        });

        endBox.getChildren().addAll(score , returnButton);
        endBox.setLayoutX(215);

        scoreEnd.getChildren().add(endBox);

        group.getChildren().add(scoreEnd);
    }
    public void exx () {
        Platform.exit();
    }
    @Override
    public void start(Stage stage) {
        setBackGround();
        startOfTheGame();
        stage.setTitle("Candy Crush");
        Scene scene = new Scene(group);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();

    }
}