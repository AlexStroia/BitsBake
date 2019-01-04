package co.alexdev.bitsbake.events;

import co.alexdev.bitsbake.model.Step;

/**
 * Event which triggers when a recipe step is clicked
 *
 * @param  Step Step object when a step is clicked
 * @param position position where the step was clicked
 */

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
