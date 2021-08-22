package prog_notebook;

public enum EnumSort {
    NAME('1', "по имени"),
    SURNAME('2', "по фамилии"),
    NAME_SURNAME('3', "по имени и фамилии"),
    NAME_AGE('4', "по имени и возрасту"),
    ;

    private final char key;
    private final String name;

    EnumSort(char key, String name) {
        this.key = key;
        this.name = name;
    }

    public char getKey() {
        return key;
    }

    public String getName() {
        return name;
    }
}
