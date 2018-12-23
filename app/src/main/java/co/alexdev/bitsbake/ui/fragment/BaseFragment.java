package co.alexdev.bitsbake.ui.fragment;


import androidx.fragment.app.Fragment;
import co.alexdev.bitsbake.ui.activity.BaseActivity;
import co.alexdev.bitsbake.viewmodel.MainViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class BaseFragment extends Fragment {

    MainViewModel vm;

    public void changeFragment(Fragment fragment) {
        ((BaseActivity) getActivity()).changeFragment(new DescriptionFragment());
    }
}

