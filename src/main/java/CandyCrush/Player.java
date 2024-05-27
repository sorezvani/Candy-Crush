package CandyCrush;

import java.io.*;
import java.util.Random;
import java.util.Scanner;

public class Player {
    private final static int basePoint = 10;
    static int[] topScore = new int[5];
    static String[] topName = new String[5];
    final String name;
    final int id;
    private int heart;
    private int score;
    public Player (String name){
        this.name = name ;
        id = generateID() ;
        heart = 3 ;
        score = 0 ;
    }
    public int getHeart () {
        return heart;
    }
    public int getScore () {
        return score;
    }
    public void loseHeart () {
        heart -= 1;
    }
    public void addHeart () {
        heart += 1 ;
    }
    public void calculateNewScore (int count){
        if (count == 3) score += basePoint;
        else if (count == 4) score += basePoint * 2;
        else if (count >= 5) {
            addHeart();
            score += basePoint * 3;
        }
    }
    public int generateID () {
        Random rand = new Random();
        return rand.nextInt(2147483647)+1;
    }
    public void endGame() {
        numberOfPlayersAdd();
        addNameAndScore();
    }
    public void addNameAndScore(){
        FileWriter fw = null;
        BufferedWriter bw = null;
        PrintWriter pw = null;
        try {
            try {
                fw = new FileWriter("src/main/resources/Files/AllScores.txt", true);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            bw = new BufferedWriter(fw);
            pw = new PrintWriter(bw);

            pw.println(score);
            pw.flush();

        } finally {
            try {
                pw.close();
                bw.close();
                fw.close();
            } catch (IOException io) {
                // can't do anything
            }
        }
        try {
            try {
                fw = new FileWriter("src/main/resources/Files/AllNames.txt", true);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            bw = new BufferedWriter(fw);
            pw = new PrintWriter(bw);

            pw.println(name);
            pw.flush();

        } finally {
            try {
                pw.close();
                bw.close();
                fw.close();
            } catch (IOException io) {
                // can't do anything
            }
        }
    }
    public void numberOfPlayersAdd() {
        File myObj = new File("src/main/resources/Files/numberOfAllPlayers.txt");
        Scanner myReader ;
        try {
            myReader = new Scanner(myObj);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        int number = myReader.nextInt() + 1 ;
        myReader.close();

        FileWriter fw ;
        try {
            fw = new FileWriter("src/main/resources/Files/numberOfAllPlayers.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            fw.write(String.valueOf(number));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static int numberOfPlayers() {
        File myObj = new File("src/main/resources/Files/numberOfAllPlayers.txt");
        Scanner myReader = null;
        try {
            myReader = new Scanner(myObj);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        int number = myReader.nextInt() ;
        myReader.close();
        return number;
    }
    public static void sort(int[] numbers, String[] name , int num){
        for(int j=0; j < num;j++) {
            for (int i = numbers.length-2 ; i >= 0; i--) {
                if (numbers[i] < numbers[i + 1]){
                    int test = numbers[i];
                    numbers[i] = numbers[i + 1];
                    numbers[i + 1] = test;
                    String test1 = name[i];
                    name[i] = name[i+1];
                    name[i+1] = test1;
                }
            }
        }
    }
    public static void top5(){
        int n = numberOfPlayers();
        int[] number = new int[n];
        String[] name = new String[n];

        File myObj = new File("src/main/resources/Files/AllScores.txt");
        Scanner myReader ;
        try {
            myReader = new Scanner(myObj);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        int i =0;
        while (myReader.hasNextInt()){
            number [i] = myReader.nextInt();
            i++;
        }
        myReader.close();

        File myObj1 = new File("src/main/resources/Files/AllNames.txt");
        Scanner myReader1 ;
        try {
            myReader1 = new Scanner(myObj1);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        i =0;
        while (myReader1.hasNextLine()){
            name[i] = myReader1.nextLine();
            i++;
        }
        myReader1.close();

        sort(number , name , 5);

        for(int j=0;j<5;j++) {
            topScore[j] = number[j];
            topName[j] = name[j];
        }
    }
}