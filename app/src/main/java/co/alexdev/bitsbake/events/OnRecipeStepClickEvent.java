package co.alexdev.bitsbake.events;

import co.alexdev.bitsbake.model.Step;

public class OnRecipeStepClickEvent {

    Step step;

    public OnRecipeStepClickEvent(Step step) {
        this.step = step;
    }

    public Step getStep() {
        return step;
    }
}
