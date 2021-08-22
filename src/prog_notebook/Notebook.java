package prog_notebook;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class Notebook {
    public final static String LOCAL_PATCH = "/src/prog_notebook/";
    public final static String FILE_NAME = "users.sav";

    private ArrayList<User> users;
    private final Scanner sc;
    private String cmd;
    private boolean  endProg;

    public Notebook() {
        sc = new Scanner(System.in);
        users = new ArrayList<>();
    }

    //========= ОСНОВНОЙ МЕТОД ============================
    public void go() {
        System.out.println("Записная книжка");
        System.out.println("***************");

        //загрузить данные в записную книжку
        if(isFileExists()) {
            loadFromFile();
        }
        while(!endProg) {
            printHead();
            inputCommand();
            processCommand();
        }
        saveToFile();
    }
    //===================================================

    private void printHead() {
        System.out.println();
        String str = "";
        Command[] command = Command.values();

        for (int i = 0; i < command.length; i++) {
            str += command[i].getKey() + " " + command[i].getNameRus();
            if(i < command.length -1 ) {
                str += ",  ";
            }
        }

        System.out.println(str);
        System.out.println("...........................................................");
    }

    private void inputCommand() {
        System.out.print("Ввведите команду: ");
        cmd = sc.next();
    }

    private void processCommand() {
        String key;

        //
        key = Command.END.getKey();
        if(My.cmpStr(cmd, key)) {
            endProg = true;
            return;
        }

        //add
        key = Command.ADD.getKey();
        if(My.cmpStr(cmd, key)) {
            add();
            return;
        }

        //del
        key = Command.DEL.getKey();
        if(My.cmpStr(cmd, key)) {
            del();
            return;
        }

        //print
        key = Command.PRINT.getKey();
        if(My.cmpStr(cmd, key)) {
            printUsers();
            return;
        }

        if(My.cmpStr(cmd, "save")) {
            saveToFile();
        }

        if(My.cmpStr(cmd, "load")) {
            loadFromFile();
        }

    }

    private void add() {
        System.out.print("Фамилия: ");
        String surname = sc.next();
        System.out.print("Имя: ");
        String name = sc.next();
        int yearOfBirth = My.nextInt(sc,"Год рождения: ",0,2200);
        System.out.print("email: ");
        String email = sc.next();
        System.out.print("телефон: ");
        String tel= sc.next();

        users.add(new User(surname, name, yearOfBirth, email, tel));
        System.out.println("Информация введена в записную книжку");
    }


    private void del() {
        int index = My.nextInt(sc,"Введите номер записи для удаления: ");
        index--;

        if(index < 0 || index >= users.size()) {
            System.out.println("Ошибка: такого номера не существует");
        }
        else {
            User delUser = users.remove(index);
            System.out.println("Удалено из книжки: " + delUser.smallInfo());
        }
    }

    private void printUsers() {
        for (EnumSort enumSort: EnumSort.values()) {
            System.out.println(enumSort.getKey() + " - " + enumSort.getName());
        }
        char typeSort = My.nextCharLowerCase(sc,"Выберите тип сортировки: ", EnumSort.NAME.getKey(),
                                                                                       EnumSort.SURNAME.getKey(),
                                                                                       EnumSort.NAME_SURNAME.getKey(),
                                                                                       EnumSort.NAME_AGE.getKey()
                                                                                            );

        if(typeSort == EnumSort.NAME.getKey()) {
            users.sort(new UserComparatorName());
        }
        else if(typeSort == EnumSort.SURNAME.getKey()) {
            users.sort(new UserComparatorSurname());
        }
        else if(typeSort == EnumSort.NAME_SURNAME.getKey()) {
            users.sort(new UserComparatorName().thenComparing(new UserComparatorSurname()));
        }
        else if(typeSort == EnumSort.NAME_AGE.getKey()) {
            users.sort(new UserComparatorName().thenComparing(new UserComparatorAge()));
        }

        System.out.println();
        String str = "";
        int i = 1;
        for (User user: users) {
            str = i + ".";
            str = String.format("%-5s", str);
            System.out.println(str + user);
            i++;
        }
    }

    private void saveToFile()  {
        String filename = getFilename();
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(filename)))
        {
            objectOutputStream.writeObject(users);
            System.out.println("Данные сохранены в файл");

        }
        catch (IOException ex) {
            System.err.println("Ошибка сохранения файла " + filename);
        }
    }

    @SuppressWarnings("unchecked")
    private void loadFromFile() {
        String filename = getFilename();
        try(ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(filename)))
        {
            users = (ArrayList<User>) objectInputStream.readObject();
            System.out.println("Данные загружены в записную книжку");
            System.out.println("Записей в книжке: " + users.size());
        }
        catch (Exception ex) {
            System.err.println("Не удалось загрузить информацию в записную книжку из файла " + filename);
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
