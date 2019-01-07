package co.alexdev.bitsbake.ui.fragment;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import co.alexdev.bitsbake.R;
import co.alexdev.bitsbake.databinding.FragmentVideoDialogBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeVideoDialogFragment extends DialogFragment {

    private View rootView;
    private ExoPlayer mPlayer;
    private FragmentVideoDialogBinding mBinding;
    private Bundle arguments;
    private String recipe_key;
    private String recipe_url;

    @Override
    public void onStop() {
        releasePlayer();
        super.onStop();
    }

    @Override
    public void onPause() {
        releasePlayer();
        super.onPause();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        getArgs();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        initView(container);
        loadData();
        return rootView;
    }

    private void initPlayer(Uri mediaUri) {
        if (mPlayer == null) {
            mPlayer = ExoPlayerFactory.newSimpleInstance(
                    new DefaultRenderersFactory(this.getActivity()),
                    new DefaultTrackSelector(),
                    new DefaultLoadControl());
            mBinding.exoplayer.setPlayer(mPlayer);
            mPlayer.prepare(buildMediaSource(mediaUri));
            mPlayer.setPlayWhenReady(true);
        }
    }

    private void releasePlayer() {
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }
    }

    private MediaSource buildMediaSource(Uri uri) {
        String userAgent = Util.getUserAgent(this.getActivity(), getString(R.string.app_name));
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory(userAgent)).createMediaSource(uri);
    }

    private void initView(ViewGroup container) {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_video_dialog, container, false);
        rootView = mBinding.getRoot();
    }

    private void loadData() {
        initPlayer(Uri.parse(recipe_url));
    }

    private void getArgs() {
        arguments = getArguments();
        recipe_key = getString(R.string.recipe_id);
        if (arguments != null && arguments.containsKey(recipe_key)) {
            recipe_url = arguments.getString(recipe_key);
        }
    }
}
