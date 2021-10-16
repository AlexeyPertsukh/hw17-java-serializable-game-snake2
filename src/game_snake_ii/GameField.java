package game_snake_ii;

import java.io.Serializable;

//игровое поле
public class GameField implements Serializable {

    private static final int CLEAR_CELL = 0;
    private static final String FORMAT_SNAKE_BODY = "[%d]";
    private static final String FORMAT_SNAKE_HEAD = "(%d)";
    private static final String FORMAT = " %d ";

    private final int[][] arr;
    private int step;

    public GameField() {
        arr = new int[Const.ARR_LENGTH][Const.ARR_LENGTH];
    }

    //очищаем клетки от змейки
    private void clear() {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                if(arr[i][j] == Const.SNAKE) {
                    arr[i][j] = CLEAR_CELL;
                }
            }
        }
    }

    //печать игрового поля
    public void print(Snake snake) {
        String str = "";
        for (int i = 0; i < (Const.ARR_LENGTH * 3); i++) {
            str += "-";
        }
        System.out.println(str);

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                String strVal;
                int val = arr[i][j];
                String format = "";
                if(val == Const.SNAKE) {
                    format = getStrFormatSnake(snake.getLastPoint(), j, i);
                }
                else {
                    format = FORMAT;
                }
                String color = getColor(val);
                Color.setTextColor(color);

                strVal = String.format(format,val);
                System.out.print(strVal);

                Color.resetTextColor();
            }
            System.out.println();
        }

        System.out.println(str);
        System.out.printf("%s%d%s змейка, %s%s%s яблоко, %s%s%s камень  \n",  Const.COLOR_SNAKE, Const.SNAKE, Color.ANSI_RESET,
                                                                                    Const.COLOR_APPLE, Const.APPLE, Color.ANSI_RESET,
                                                                                    Const.COLOR_STONE, Const.STONE, Color.ANSI_RESET);
        System.out.println(str);
    }

    //разные элементы в таблице рисуем разными цветами
    private String getColor(int val) {
        switch (val){
            case Const.SNAKE:
                return Const.COLOR_SNAKE;   //цвет змеи
            case Const.APPLE:
                return Const.COLOR_APPLE;   //цвет яблока
            case Const.STONE:
                return Const.COLOR_STONE;   //цвет камня
            default:
                return Color.ANSI_RESET;    //цвет игрового поля
        }
    }


    //копируем местиоположение змейки в игровое поле
    private void fromPointsToArr(Snake snake) {
        int x;
        int y;
        for (Point point : snake) {
           x = point.getX();
           y = point.getY();
           arr[y][x] = Const.SNAKE;
        }
    }

    //
    public void action (Snake snake){
        clear();
        fromPointsToArr(snake);
        addApple(); //иногда подкидываем яблоки
        addStone(); //иногда добавляем камни
        print(snake);
        step++;
    }

    //змейка хочет на клетку- можно?
    public int checkCell(int x, int y) {
        if(x < 0 || y < 0 || x >= arr.length || y >= arr.length) {
            return Const. CODE_CRASH;
        }
        if(arr[y][x] == Const.APPLE) {
            return Const.CODE_EAT_APPLE;
        }
        if(arr[y][x] == Const.STONE) {
            return Const.CODE_EAT_STONE;
        }

        return Const.CODE_CLEAR;
    }


    //добавить яблоко
    public void addApple() {
        addUnit(Const.APPLE, 3);
    }

    //добавить камень
    public void addStone() {
        addUnit(Const.STONE, 4);
    }

    //добавление юнита на карту- яблоко, камень
    private void addUnit(int type, int period) {
        if(!isFreeCell10proc()) {       //добавляем, только если есть не менее 10% свободного места на карте
            return;
        }

        if(step % period == 0) {
            int x;
            int y;
            do {
                x = Util.random(Const.ARR_LENGTH);
                y = Util.random(Const.ARR_LENGTH);
            }
            while(arr[y][x] != CLEAR_CELL);
            arr[y][x] = type;
        }
    }

    private String getStrFormatSnake(Point lastPoint, int x, int y) {
       if (x == lastPoint.getX() && y == lastPoint.getY()) {
            return FORMAT_SNAKE_HEAD;
        }
        else {
            return FORMAT_SNAKE_BODY;
        }
    }

    //свободное место на карте
    private int getFreeCell() {
        int freeCell = 0;
        for (int[] line : arr) {
            for (int val : line) {
                if(val == CLEAR_CELL) {
                    freeCell++;
                }
            }
        }
        return freeCell;
    }

    //есть 10 процентов свободного места на карте?
    private boolean isFreeCell10proc() {
        int proc10 = Const.ARR_LENGTH * Const.ARR_LENGTH / 10;
        int free =  getFreeCell();
        return ( free > proc10);
    }

}
