package co.alexdev.bitsbake.events;

public class OnRecipeStepClickEvent {

    int position;

    public OnRecipeStepClickEvent(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }
}
