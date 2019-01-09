package co.alexdev.bitsbake.events;

import co.alexdev.bitsbake.model.Step;


/*Event which triggers when a recipe step is clicked*/


public class OnRecipeStepClickEvent {

    private Step step;
    private int position;

    public OnRecipeStepClickEvent(Step step, int position) {
        this.step = step;
        this.position = position;
    }

    public Step getStep() {
        return step;
    }

    public int getPosition() {
        return position;
    }
}
