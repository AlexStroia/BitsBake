package co.alexdev.bitsbake.events;

import java.util.List;

import co.alexdev.bitsbake.model.Step;


/*Event which triggers when a recipe step is clicked*/

public class OnRecipeStepClickEvent {

    private Step step;
    private List<Step> steps;
    private int position;

    public OnRecipeStepClickEvent(Step step, List<Step> steps, int position) {
        this.step = step;
        this.position = position;
        this.steps = steps;
    }

    public Step getStep() {
        return step;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public int getPosition() {
        return position;
    }
}
