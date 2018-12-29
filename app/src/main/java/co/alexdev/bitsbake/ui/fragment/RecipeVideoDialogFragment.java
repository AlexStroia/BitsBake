package co.alexdev.bitsbake.ui.fragment;

import android.net.Uri;
import android.os.Bundle;

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

import androidx.lifecycle.ViewModelProviders;
import co.alexdev.bitsbake.R;
import co.alexdev.bitsbake.databinding.FragmentVideoDialogBinding;
import co.alexdev.bitsbake.repo.BitsBakeRepository;
import co.alexdev.bitsbake.viewmodel.DialogVM;
import co.alexdev.bitsbake.viewmodel.factory.DialogViewModelFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeVideoDialogFragment extends DialogFragment {

    private View rootView;
    private ExoPlayer mPlayer;
    private String TEST_URI = "https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd9a6_2-mix-sugar-crackers-creampie/2-mix-sugar-crackers-creampie.mp4";
    private FragmentVideoDialogBinding mBinding;
    private BitsBakeRepository mRepository;
    private DialogViewModelFactory factory;
    private DialogVM vm;


    @Override
    public void onStart() {
        initPlayer(Uri.parse(TEST_URI));
        super.onStart();
    }

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
    public void onResume() {
        initPlayer(Uri.parse(TEST_URI));
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        Bundle arguments = getArguments();
        String argsKey = getString(R.string.recipe_id);
        if(arguments != null && arguments.containsKey(argsKey)) {
            int recipeID = arguments.getInt(argsKey);
            vm.setRecipeId(recipeID);
            //TODO
        }

        initView(container);
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
        mRepository = BitsBakeRepository.getInstance(this.getActivity());
        factory = new DialogViewModelFactory(mRepository);
        vm = ViewModelProviders.of(this.getActivity(),factory).get(DialogVM.class);
        rootView = mBinding.getRoot();
    }
}
