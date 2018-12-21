package co.alexdev.bitsbake.events;

public class OnRecipeClickEvent {

    int position;

    public OnRecipeClickEvent(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }
}
