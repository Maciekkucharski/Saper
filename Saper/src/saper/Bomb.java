package saper;

import java.util.Objects;

public class Bomb{
    private int bombX;
    private int bombY;

    public int getBombX() {
        return bombX;
    }

    public int getBombY() {
        return bombY;
    }

    public Bomb(int bombX, int bombY) {
        this.bombX = bombX;
        this.bombY = bombY;
    }

    @Override
    public boolean equals(Object obj) {
        Bomb bomb = (Bomb)obj;
        if (bombX==bomb.bombX&&bombY==bomb.bombY){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(bombX,bombY);
    }
}
