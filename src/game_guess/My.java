package game_guess;

import java.util.Scanner;

public class My {
    private My() {
    }

    public static int random(int min, int max) {
        if(min > max) {
            int tmp = min;
            min = max;
            max = tmp;
        }
        return (int) (Math.random() * (max - min)) + min;
    }

    public static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean cmpStr(String str1, String str2) {
        return (str1.compareToIgnoreCase(str2) == 0);
    }

    public static char nextCharLowerCase(Scanner sc, String text, char... arr) {
        while(true) {
            System.out.print(text);
            String cmd = sc.next().toLowerCase();

            if(cmd.length() == 1) {
                char ch = cmd.charAt(0);
                ch = Character.toLowerCase(ch);
                for (char tmp : arr) {
                    tmp = Character.toLowerCase(tmp);
                    if(tmp == ch) {
                        return tmp;
                    }
                }
            }
        }
    }

}
