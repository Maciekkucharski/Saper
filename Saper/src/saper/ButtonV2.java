package saper;

import javafx.scene.control.Button;


public class ButtonV2 extends Button {
    private int bombX;
    private int bombY;
    private boolean isClicked=false;

    public boolean getIsClicked() {
        return isClicked;
    }

    public void setClicked(boolean clicked) {
        isClicked = clicked;
    }

    public ButtonV2(int bombX, int bombY) {
        this.isClicked = false;
        this.bombX = bombX;
        this.bombY = bombY;
        super.setMinSize(25,25);
    }

    public int getBombX() {
        return bombX;
    }

    public int getBombY() {
        return bombY;
    }


    @Override
    public boolean equals(Object obj) {
        ButtonV2 buttonV2 = (ButtonV2) obj;
        if (bombX== buttonV2.bombX&&bombY== buttonV2.bombY){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + bombX;
        result = 31 * result + bombY;
        return result;
    }


}
