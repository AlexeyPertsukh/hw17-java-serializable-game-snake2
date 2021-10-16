package game_snake_ii;

import java.io.*;
import java.util.Scanner;

public class Game {
    public final static String LOCAL_PATCH = "/src/game_snake_ii/";
    public final static String FILE_NAME = "data.sav";

    private GameField gameField;
    private Snake snake;
    private final Scanner sc;
    private  String command;

    private boolean endGame;
    private boolean commandOk;

    public Game() {
        gameField = new GameField();
        snake = new Snake();
        sc = new Scanner(System.in);
    }

    //====== ОСНОВНОЙ МЕТОД ==================================================================
    public void go(){
        printOnStart();
        do {
            gameField.action(snake);
            printCommand();
            do {
                inputCommand();
                processCommand();
            }while (!commandOk);

        }while(!endGame);

        printOnEnd();
    }
    //======================================================================================

    //список команд
    private void printCommand() {
        System.out.printf("%s,%s,%s,%s - направление  %s - выход \n",  Command.UP.getKey(),
                                                                        Command.DOWN.getKey(),
                                                                        Command.LEFT.getKey(),
                                                                        Command.RIGHT.getKey(),
                                                                        Command.END.getKey()
                                                                    );
        System.out.printf("%s - сохранить  %s - загрузить \n",  Command.SAVE.getKey(),
                                                                Command.LOAD.getKey()
        );

        System.out.println();
    }

    //ввод команд
    private void inputCommand() {
        System.out.print("Введите команду: ");
        command = sc.next();
    }

    //обработка введенных команд
    private void processCommand()
    {
        commandOk = true;
        if(command.equalsIgnoreCase(Command.END.getKey())) {
            endGame = true;
        }
        else if(command.equalsIgnoreCase(Command.UP.getKey())) {
            int[] coordinate = Command.UP.getArr();
            moveSnake(coordinate[0], coordinate[1]);
        }
        else if(command.equalsIgnoreCase(Command.DOWN.getKey())) {
            int[] coordinate = Command.DOWN.getArr();
            moveSnake(coordinate[0], coordinate[1]);

        }
        else if(command.equalsIgnoreCase(Command.LEFT.getKey())) {
            int[] coordinate = Command.LEFT.getArr();
            moveSnake(coordinate[0], coordinate[1]);

        }
        else if(command.equalsIgnoreCase(Command.RIGHT.getKey())) {
            int[] coordinate = Command.RIGHT.getArr();
            moveSnake(coordinate[0], coordinate[1]);
        }
        else if(command.equalsIgnoreCase(Command.SAVE.getKey())) {
            saveToFile();
        }
        else if(command.equalsIgnoreCase(Command.LOAD.getKey())) {
            loadFromFile();
        }
        else {
            System.out.println("неизвестная команда");
            System.out.println();
            commandOk = false;
        }

    }

    //двигаем змейку
    private void moveSnake(int addX, int addY)
    {
        int newX = snake.getLastPoint().getX() + addX;
        int newY = snake.getLastPoint().getY() + addY;

        if(snake.isBitMyself(newX, newY)) {
            checkDeath(Const.CODE_EAT_MYSELF);
            return;
        }

        int code = gameField.checkCell(newX, newY);

        if(code == Const.CODE_CRASH || code == Const.CODE_EAT_STONE) {
            checkDeath(code);
            return;
        }

        snake.addPoint(new Point(newX,newY));

        if(code != Const.CODE_EAT_APPLE) {
            snake.delFirstPoint();
        }
    }

    //змея скончалась
    private void checkDeath(int code){
        String message = "";
        if(code == Const.CODE_CRASH) {
            message = "Змейка врезалась в стену!";
        }
        else if(code == Const.CODE_EAT_STONE) {
            message = "Змейка съела камень и подавилась!";
        }
        else if(code == Const.CODE_EAT_MYSELF) {
            message = "Змейка укусила сама себя!";
        }
        Color.printlnColorRed(message);
        endGame = true;
    }


    private void printOnStart() {
        System.out.printf("%s %s \n",Const.NAME_GAME, Const.VERSION);
        System.out.println("~~~~o< >o~~~~");
        Color.setTextColor(Color.ANSI_YELLOW);
        System.out.println("Доработка задания #15");
        System.out.println("Добавлено:");
        System.out.println("• Iterator и интерфейс Iterable в классе Snake");
        System.out.println("• Обработка змейки циклом for each в классе Map");
        System.out.println("• Сохранение и загрузка текущей игры в файл с использованием сериализации");
        Color.resetTextColor();
        System.out.println();
    }

    private void printOnEnd() {
        Color.printlnColorYellow("GAME OVER");
        System.out.println();
        System.out.println(Const.COPYRIGHT);
        System.out.println(Const.AUTHOR);
        System.out.println();
        System.out.println(Const.GIT_URL);
    }

    private void saveToFile()  {
        String filename = getFilename();
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(filename)))
        {
            objectOutputStream.writeObject(gameField);
            objectOutputStream.writeObject(snake);
            System.out.println("Игра сохранена в файл");

        }
        catch (IOException ex) {
            System.err.println("Ошибка сохранения игры в файл " + filename);
        }
    }

    private void loadFromFile() {
        String filename = getFilename();
        try(ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(filename)))
        {
            gameField = (GameField) objectInputStream.readObject();
            snake = (Snake) objectInputStream.readObject();
            System.out.println("Игра загружена из файла");
        }
        catch (Exception ex) {
            System.err.println("Не удалось загрузить игру из файла " + filename);
        }
    }

    private String getFilename() {
        String path = new java.io.File(".").getAbsolutePath();
        return path + LOCAL_PATCH + FILE_NAME;
    }

    //файл существует?
    private boolean isFileExists() {
        File file = new File(getFilename());
        return file.exists();
    }


}
