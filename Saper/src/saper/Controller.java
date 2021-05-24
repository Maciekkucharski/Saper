package saper;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Controller implements Initializable {

    private int tempGameID;

    private int gameID;

    private String nickName;

    private int time = 0;

    private String diff = "";

    private int hits = 0;

    private Random rand = new Random();

    private Set<Bomb> bombMap = new HashSet<Bomb>();

    private List<ButtonV2> listaButtonV2 = new ArrayList<ButtonV2>();

    private void addScore(int scoreToAdd, String nickToAdd) {
        nick.setCellValueFactory(new PropertyValueFactory<tabFiller, String>("nick"));
        czas.setCellValueFactory(new PropertyValueFactory<tabFiller, Integer>("czas"));
        tab.getItems().add(new tabFiller(scoreToAdd, nickToAdd));
    }

    private void startGame(int height, int width, int bombs, String diff) {
        stop();
        do {
            gameID = rand.nextInt(100000);
        } while (gameID == tempGameID);
        this.diff = diff;
        Label timer = new Label();
        timerPane.getChildren().add(timer);
        timer.setFont(Font.font(20));
        long startTime = System.currentTimeMillis();
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                long elapsedMillis = System.currentTimeMillis() - startTime;
                time = (int) elapsedMillis / 1000;
                timer.setText(Integer.toString(time));
            }
        }.start();


        while (bombMap.size() < bombs) {
            Bomb bomb = new Bomb(rand.nextInt(width), rand.nextInt(height));
            bombMap.add(bomb);
        }

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                ButtonV2 buttonV2 = new ButtonV2(j, i);
                listaButtonV2.add(buttonV2);
                gridPane.add(buttonV2, j, i);
            }
        }

        for (ButtonV2 guzik : listaButtonV2) {
            guzik.setOnAction(event -> {
                clicked(guzik.getBombX(), guzik.getBombY(), height, width, bombs, gameID);
            });
        }
    }

    private void clicked(int x, int y, int height, int width, int bombs, int recursivegameID) {
        if (recursivegameID != gameID) {
            return;
        }


        ButtonV2 g1 = new ButtonV2(x, y);
        for (ButtonV2 g : listaButtonV2) {
            if (g.equals(g1)) {
                if (g.getIsClicked()) {
                    return;
                }
            }
        }

        Bomb choice = new Bomb(x, y);
        int surrounding = 0;
        boolean hit = false;
        for (Bomb tempBomb : bombMap) {
            if (tempBomb.equals(choice)) {
                hit = true;
            }
        }
        if (hit) {
            AlertBox lossAlert = new AlertBox();
            lossAlert.display("Przegrałeś", "");
            restart();
            return;
        } else {
            ButtonV2 g = new ButtonV2(x, y);
            for (ButtonV2 guzikTemp : listaButtonV2) {
                if (guzikTemp.equals(g)) {
                    guzikTemp.setDisable(true);
                    guzikTemp.setClicked(true);
                    hits++;
                }
            }
        }
        if (hits == height * width - bombs) {
            addScore();
            AlertBox resetAlert = new AlertBox();
            resetAlert.display("WYGRAŁEŚ", "");
            tempGameID = gameID;
            restart();
            return;
        }


        for (Bomb bomb : bombMap) {
            if (bomb.equals(new Bomb(x - 1, y - 1)) || bomb.equals(new Bomb(x - 1, y)) || bomb.equals(new Bomb(x - 1, y + 1)) || bomb.equals(new Bomb(x, y + 1)) || bomb.equals(new Bomb(x + 1, y + 1)) || bomb.equals(new Bomb(x + 1, y)) || bomb.equals(new Bomb(x + 1, y - 1)) || bomb.equals(new Bomb(x, y - 1))) {
                surrounding++;
            }
        }
        if (surrounding == 0) {

            int tempX = x, tempY = y;
            if (gameID == tempGameID) return;


            if (tempX >= 0 && tempX < width && tempY >= 0 && tempY < height) {


                if (tempX < width - 1 && tempY < height + 1) {
                    clicked(tempX + 1, tempY + 1, height, width, bombs, recursivegameID);
                }
                if (tempX < width - 1) {
                    clicked(tempX + 1, tempY, height, width, bombs, recursivegameID);
                }
                if (tempX < width - 1 && tempY - 1 > -1) {
                    clicked(tempX + 1, tempY - 1, height, width, bombs, recursivegameID);
                }
                if (tempY - 1 > -1) {
                    clicked(tempX, tempY - 1, height, width, bombs, recursivegameID);
                }
                if (tempX - 1 > -1 && tempY - 1 > -1) {
                    clicked(tempX - 1, tempY - 1, height, width, bombs, recursivegameID);
                }
                if (tempX - 1 > -1) {
                    clicked(tempX - 1, tempY, height, width, bombs, recursivegameID);
                }
                if (tempY < height - 1 && tempX - 1 > -1) {
                    clicked(tempX - 1, tempY + 1, height, width, bombs, recursivegameID);
                }
                if (tempY < height - 1) {
                    clicked(tempX, tempY + 1, height, width, bombs, recursivegameID);
                }
            }
        } else {
            ButtonV2 buttonV2 = new ButtonV2(x, y);
            for (ButtonV2 guzikTemp : listaButtonV2) {
                if (guzikTemp.equals(buttonV2)) {
                    String temp = String.valueOf(surrounding);
                    guzikTemp.setText(temp);
                }
            }
        }

    }

    public static void sprawdzDane(String nick) throws Exception {
        Pattern p = Pattern.compile("\\W");
        Matcher matcher = p.matcher(nick);

        if (matcher.find()) {
            throw new Exception("Letters or dogits allowed only");
        }

    }

    @FXML
    TableView<tabFiller> tab;

    @FXML
    TableColumn<tabFiller, String> nick;

    @FXML
    TableColumn<tabFiller, Integer> czas;

    @FXML
    Pane timerPane;

    @FXML
    private GridPane gridPane;

    @FXML
    private void setEasy() {
        startGame(9, 9, 1, "easy");
    }

    @FXML
    private void setMedium() {
        startGame(16, 16, 40, "medium");
    }

    @FXML
    private void setHard() {
        startGame(16, 30, 99, "hard");
    }

    @FXML
    private void restart() {
        if (diff != "") {


            stop();
            switch (diff) {
                case "easy":
                    setEasy();
                    break;
                case "medium":
                    setMedium();
                    break;
                case "hard":
                    setHard();
                    break;
            }
        }
    }

    @FXML
    private void addScore() {
        int tempTime = time;
        AlertName nameAlert = new AlertName();
        try {
            nickName = nameAlert.getName();
        } catch (Exception e) {
            System.out.println("Wrong input!");
        }

        nick.setCellValueFactory(new PropertyValueFactory<tabFiller, String>("nick"));
        czas.setCellValueFactory(new PropertyValueFactory<tabFiller, Integer>("czas"));
        tab.getItems().add(new tabFiller(tempTime, nickName));

        try {
            FileWriter saveScore = new FileWriter("saves.txt", true);
            saveScore.write(nickName.concat(" "));
            saveScore.write(Integer.toString(tempTime).concat("\n"));
            saveScore.flush();
            saveScore.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return;
    }

    @FXML
    private void stop() {
        bombMap.clear();
        gridPane.getChildren().clear();
        listaButtonV2.clear();
        hits = 0;
        timerPane.getChildren().clear();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File saves = new File("./saves.txt");
        Scanner scanner;
        ArrayList<String> nick = new ArrayList<>();
        ArrayList<Integer> score = new ArrayList<>();
        try {
            saves.createNewFile();
            scanner = new Scanner(saves);
            while (scanner.hasNext()) {
                System.out.println();
                Pattern p = Pattern.compile("(\\w+)\\s(\\d+)");
                Matcher matcher = p.matcher(scanner.nextLine());
                if (matcher.find()) {
                    nick.add(matcher.group(1));
                    score.add(Integer.parseInt(matcher.group(2)));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < score.size(); i++) {
            addScore(score.get(i), nick.get(i));
        }
    }


}
