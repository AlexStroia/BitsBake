package co.alexdev.bitsbake.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import co.alexdev.bitsbake.databinding.ItemRecipeDescriptionLayoutBinding;
import co.alexdev.bitsbake.model.model.Step;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepsViewHolder> {

    List<Step> steps;

    public StepsAdapter(List<Step> steps) {
        this.steps = steps;
    }

    @NonNull
    @Override
    public StepsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemRecipeDescriptionLayoutBinding binding = ItemRecipeDescriptionLayoutBinding.inflate(inflater, parent, false);
        return new StepsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull StepsViewHolder holder, int position) {
        holder.bind(steps.get(position));
    }

    @Override
    public int getItemCount() {
        if(steps == null) return 0;
        return steps.size();
    }

    public void setList(List<Step> steps) {
        this.steps = steps;
        notifyDataSetChanged();
    }

    static class StepsViewHolder extends RecyclerView.ViewHolder {

        private ItemRecipeDescriptionLayoutBinding mBinding;

        public StepsViewHolder(ItemRecipeDescriptionLayoutBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bind(Step step) {
            mBinding.tvDescription.setText(step.getDescription());
            mBinding.tvShortDesc.setText(step.getShortDescription());
            //TODO EXOPLAYER
        }
    }
}
