package saper;

public class tabFiller {
    private final int czas;
    private final String nick;

    public int getCzas() {
        return czas;
    }

    public String getNick() {
        return nick;
    }

    public tabFiller(int czas, String nick) {
        this.czas = czas;
        this.nick = nick;
    }
}
