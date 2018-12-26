package co.alexdev.bitsbake.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import co.alexdev.bitsbake.R;
import co.alexdev.bitsbake.databinding.ItemRecipeDescriptionLayoutBinding;
import co.alexdev.bitsbake.model.model.Step;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepsViewHolder> {

    private List<Step> steps;
    private static Context mContext;

    public StepsAdapter(List<Step> steps, Context context) {
        this.steps = steps;
        this.mContext = context;
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
        if (steps == null) return 0;
        return steps.size();
    }

    public void setList(List<Step> steps) {
        this.steps = steps;
        notifyDataSetChanged();
    }

    public static void releasePlayer() {
        StepsViewHolder.releasePlayer();
    }

    public static class StepsViewHolder extends RecyclerView.ViewHolder {

        private ItemRecipeDescriptionLayoutBinding mBinding;
        public static SimpleExoPlayer mExoPlayer;

        public StepsViewHolder(ItemRecipeDescriptionLayoutBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bind(Step step) {
            mBinding.tvDescription.setText(step.getDescription());
            mBinding.tvShortDesc.setText(step.getShortDescription());
        //    initializePlayer(step);
        }

        private void initializePlayer(Step step) {
            if (mExoPlayer == null) {
                TrackSelector trackSelector = new DefaultTrackSelector();
                LoadControl loadControl = new DefaultLoadControl();

                mExoPlayer = ExoPlayerFactory.newSimpleInstance(mContext, trackSelector, loadControl);
               // mBinding.exoplayer.setPlayer(mExoPlayer);

                //TODO CHECK DEPRECATED CODE
                String userAgent = Util.getUserAgent(mContext.getApplicationContext(), mContext.getString(R.string.app_name));
                MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(step.getVideoURL()),
                        new DefaultDataSourceFactory(mContext, userAgent),
                        new DefaultExtractorsFactory(), null, null);
                mExoPlayer.prepare(mediaSource);
                mExoPlayer.setPlayWhenReady(true);
            }
        }

        public static void releasePlayer() {
            if (mExoPlayer != null) {
                mExoPlayer.release();
                mExoPlayer = null;
            }
        }
    }
}
