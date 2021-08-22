package game_guess;

import java.io.*;
import java.util.Scanner;

public class Game {

    public final static String LOCAL_PATCH = "/src/game_guess/";
    public final static String FILE_NAME = "game.sav";

    private static final String END = "END";
    private static final String SAVE = "S";
    private static final String LOAD = "L";
    private static final int MAX = 10;

    private final Scanner sc;
    private int valueForGuess;
    private boolean endGame;
    private String cmd;
    private int cnt;
    private int[] arr;

    public Game() {
        sc = new Scanner(System.in);
        valueForGuess = My.random(1, 100);
        arr = new int[MAX];
    }

    public void go() {
        System.out.println("Игра \"Угадай число\"");
        System.out.println("-------------------");
        printHead();

        if(isFileExists()) {
            char ch = My.nextCharLowerCase(sc,"Загрузить сохраненную игру? (Y - да, N - нет): ",'Y','N');
            System.out.println();
            if(ch == 'y') {
                loadFromFile();
            }
        }

        while (!endGame) {
            //максимальное количество попыток
            if(cnt >= MAX) {
                System.out.printf("Вы проиграли! Вы не смогли угадать число за %d попыток \n", cnt);
                endGame = true;
                continue;
            }
            inputCommand();
            processCommand();
        }
    }

    private void printHead() {
        System.out.println();
        System.out.printf("%s сохранить игру, %s загрузить игру, %s выход \n", SAVE, LOAD, END);
        System.out.println("..............................................");
        System.out.println();
    }

    private void inputCommand()  {
//        System.out.println(valueForGuess);
        System.out.printf("Попытка #%d. Угадайте число (1-100): ", cnt + 1);
        cmd = sc.next();
    }

    private void processCommand() {
        //сохранить
        if(My.cmpStr(cmd, SAVE)) {
            saveToFile();
            return;
        }

        //загрузить
        if(My.cmpStr(cmd, LOAD)) {
            loadFromFile();
            return;
        }

        //выход
        if(My.cmpStr(cmd, END)) {
            endGame = true;
            char ch = My.nextCharLowerCase(sc,"Сохранить игру? (Y - да, N - нет): ",'Y','N');
            if(ch == 'y') {
                saveToFile();
            }
            return;
        }

        //ввод числа
        if(!My.isInteger(cmd)) {
            System.out.println("Недопустимая команда");
            return;
        }

        int val = Integer.parseInt(cmd);
        arr[cnt] = val;
        cnt++;

        if(val == valueForGuess) {
            System.out.println();
            System.out.println("Поздравляем! Вы угадали число");
            endGame = true;
        }
        else {
            if(val > valueForGuess) {
                System.out.println("Нужно меньше");
            }
            else {
                System.out.println("Нужно больше");
            }
        }
    }

    private String getFilename() {
        String path = new java.io.File(".").getAbsolutePath();
        return path + LOCAL_PATCH + FILE_NAME;
    }

    private void saveToFile() {
        String fileName = getFilename();
        try {
            //Whatever the file path is.
            File file = new File(fileName);
            FileOutputStream is = new FileOutputStream(file);
            OutputStreamWriter osw = new OutputStreamWriter(is);
            Writer writer = new BufferedWriter(osw);
            writer.write(Integer.toString(valueForGuess));
            for (int i = 0; i < cnt; i++) {
                writer.write(",");
                writer.write(Integer.toString(arr[i]));
            }
            writer.close();
            System.out.println("[Игра сохранена]");
            System.out.println();
        } catch (IOException e) {
            System.err.println("Ошибка создания файла " + fileName);
        }
    }

    private void loadFromFile() {
        String filename = getFilename();
        try{
            FileReader fr= new FileReader(filename);
            Scanner scan = new Scanner(fr);
            String str = scan.next();
            String[] strArr = str.split(",");
            if(strArr.length > 0) {
                valueForGuess = Integer.parseInt(strArr[0]);
                cnt = 0;
                for (int i = 1; i < strArr.length; i++) {
                    cmd = strArr[i];
                    System.out.printf("Попытка #%d. Угадайте число (1-100): ", cnt + 1);
                    System.out.println(cmd);
                    processCommand();
                }
                System.out.println("[Игра загружена]");
                System.out.println();
            }
        }
        catch(IOException ex) {
            System.err.println("Файл не найден: " + filename);
        }

    }

    //файл существует?
    private boolean isFileExists() {
        File file = new File(getFilename());
        return file.exists();
    }




}
