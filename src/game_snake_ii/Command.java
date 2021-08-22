package game_snake_ii;

public enum Command {
    UP("W","вверх", 0, -1),
    DOWN("S","вниз", 0, 1),
    LEFT("A","влево", -1, 0),
    RIGHT("D","вправо",1,0),
    SAVE("SAVE","сохранить игру"),
    LOAD("LOAD","загрузить игру"),
    END("END","выход"),
    ;

    private final String nameRus;
    private final String key;
    private final int[] arr;

    Command(String key, String nameRus, int... arr) {
        this.nameRus = nameRus;
        this.key = key;
        this.arr = arr;
    }

    Command(String key, String nameRus) {
        this(key, nameRus, 0);
    }

    public String getKey() {
        return key;
    }

    public String getNameRus() {
        return nameRus;
    }

    public int[] getArr() {
        return arr;
    }


}
