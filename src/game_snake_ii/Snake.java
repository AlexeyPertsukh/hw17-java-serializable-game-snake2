//        Линейный однонаправленный список — это структура данных, состоящая из элементов одного типа,
//        связанных между собой последовательно посредством указателей.
//        Каждый элемент списка имеет указатель на следующий элемент.
//        Последний элемент списка указывает на NULL.

package game_snake_ii;

import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;

//змейка, построенная по принципу однонаправленного списка списка
public class Snake implements Iterable<Point>, Serializable {

    private  Point firstPoint;
    private  Point lastPoint;

    public Snake() {
        initSnake();
    }

    private void initSnake() {
        //начальные координаты змейки
        int x = Const.ARR_LENGTH / 3;
        int y = Const.ARR_LENGTH / 2;

        for (int i = 0; i < Const.SNAKE_LENGTH; i++) {
            addPoint(new Point(x,y));
            x++;
        }
    }

    public void addPoint(Point point){
        if(firstPoint == null) {
            firstPoint = point;
            lastPoint = firstPoint;
        }
        else{
            lastPoint.setNextPoint(point);
            lastPoint = point;
        }
    }

    //удалить начальный узел(хвост змеи)
    public void delFirstPoint() {
        if(firstPoint != null)
        {
            firstPoint = firstPoint.getNextPoint();
        }
    }

    public Point getFirstPoint() {
        return firstPoint;
    }

    public Point getLastPoint() {
        return lastPoint;
    }

    //змейка укусила сама себя? проход по связному списку от хвоста до головы
    public boolean  isBitMyself(int x, int y) {
        Point point = firstPoint;
        while(point.getNextPoint() != null) {
            if(x == point.getX() && y == point.getY()) {
                return true;
            }
            point = point.getNextPoint();
        }

        return false;
    }


    @Override
    public Iterator<Point> iterator() {
        return new SnakeIterator(firstPoint);
    }

    //делаем наш внутренний итератор
    private static class SnakeIterator implements Iterator<Point> {
        private Point point;

        public SnakeIterator(Point firstPoint) {
            this.point = firstPoint;
        }

        public boolean hasNext() {
            return (point != null);
        }

        @SuppressWarnings("unchecked")
        public Point next() throws NoSuchElementException {
            if (hasNext()) {
                Point res = point;
                point = point.getNextPoint();
                return res;
            }
            else {
                throw new NoSuchElementException();
            }
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

}
