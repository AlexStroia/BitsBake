package co.alexdev.bitsbake.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import co.alexdev.bitsbake.databinding.ItemRecipeStepLayoutBinding;
import co.alexdev.bitsbake.model.model.Step;
import timber.log.Timber;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepsViewHolder> {

    private List<Step> steps;

    public StepsAdapter(List<Step> steps) {
        this.steps = steps;
    }

    @NonNull
    @Override
    public StepsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemRecipeStepLayoutBinding binding = ItemRecipeStepLayoutBinding.inflate(inflater, parent, false);
        return new StepsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull StepsViewHolder holder, int position) {
        Step step = steps.get(position);
        Timber.d("Step: " + step.toString());
        holder.bind(step, position);
        if (TextUtils.isEmpty(steps.get(position).getVideoURL())) {
            holder.mBinding.ivVideo.setVisibility(View.GONE);
        } else {
            holder.mBinding.ivVideo.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        if (steps == null) return 0;
        return steps.size();
    }

    public void setList(List<Step> steps) {
        this.steps = steps;
        notifyDataSetChanged();
    }

    public static class StepsViewHolder extends RecyclerView.ViewHolder {

        private ItemRecipeStepLayoutBinding mBinding;

        public StepsViewHolder(ItemRecipeStepLayoutBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bind(Step step, int position) {
            mBinding.tvStepDetail.setText(step.getShortDescription());
            mBinding.tvStepNumber.setText(String.valueOf(position));
        }
    }
}
