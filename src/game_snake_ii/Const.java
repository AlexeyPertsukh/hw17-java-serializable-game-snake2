package game_snake_ii;

public final class Const {

    public static final String NAME_GAME = "SNAKE II ";
    public static final String VERSION = "1.1";
    public static final String COPYRIGHT = "JAVA A01 \"ШАГ\", Запорожье 2021 ";
    public static final String AUTHOR = "Перцух Алексей";

    public final static int CODE_CLEAR = 0;
    public final static int CODE_CRASH = -1;
    public final static int CODE_EAT_MYSELF = -2;
    public final static int CODE_EAT_APPLE = 2;
    public final static int CODE_EAT_STONE = 3;

    public final static int ARR_LENGTH = 12;
    public final static int SNAKE_LENGTH = 5;
    public final static String COLOR_SNAKE = Color.ANSI_GREEN;
    public final static String COLOR_APPLE = Color.ANSI_YELLOW;
    public final static String COLOR_STONE = Color.ANSI_RED;

    public static final int SNAKE = 1;
    public static final int APPLE = 2;
    public static final int STONE = 3;

    private Const() {
    }
}
