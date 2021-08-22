package game_snake_ii;


//вспомогательные методы
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

    public static int random(int max) {
        return random(0, max);
    }
}
