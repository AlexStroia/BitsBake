package co.alexdev.bitsbake.ui.fragment;

import android.os.Bundle;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import co.alexdev.bitsbake.R;
import co.alexdev.bitsbake.databinding.FragmentVideoDialogBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeVideoDialogFragment extends DialogFragment {

    private View rootView;
    private FragmentVideoDialogBinding mBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        initView(container);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void initView(ViewGroup container) {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_video_dialog, container, false);

        rootView = mBinding.getRoot();
    }
}
