package prog_notebook;

public enum Command {
    ADD("ADD","добавить"),
    DEL("DEL","удалить"),
    PRINT("PRINT","распечатать"),
    END("END","выход"),
    ;

    private String key;
    private String nameRus;

    Command(String key, String nameRus) {
        this.key = key;
        this.nameRus = nameRus;
    }

    public String getKey() {
        return key;
    }

    public String getNameRus() {
        return nameRus;
    }
}
