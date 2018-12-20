package co.alexdev.bitsbake.ui.fragment;


import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import co.alexdev.bitsbake.R;
import co.alexdev.bitsbake.databinding.FragmentBaseBinding;
import co.alexdev.bitsbake.events.NetworkConnectionEvent;
import co.alexdev.bitsbake.networking.NetworkResponse;
import co.alexdev.bitsbake.utils.BitsBakeUtils;
import co.alexdev.bitsbake.utils.Constants;
import co.alexdev.bitsbake.viewmodel.MainViewModel;
import timber.log.Timber;


/**
 * A simple {@link Fragment} subclass.
 */
public class BaseFragment extends Fragment {

    MainViewModel vm;
    private FragmentBaseBinding mBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_base, container, false);
        final View rootView = mBinding.getRoot();

        initView();

        mBinding.srLayout.setOnRefreshListener(() -> vm.loadData());

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    private void initView() {
        vm = ViewModelProviders.of(this.getActivity()).get(MainViewModel.class);
        vm.getNetworkResponse().observe(this, networkResponse -> processResponse(networkResponse));
    }

    private void processResponse(NetworkResponse networkResponse) {
        switch (networkResponse.status) {
            case Constants.RESPONSE_ERROR:
                BitsBakeUtils.showAlert(this.getActivity(), networkResponse.error.getMessage());
                if (isRefreshing()) mBinding.srLayout.setRefreshing(false);
                break;

            case Constants.RESPONSE_LOADING:
                mBinding.progressBar.setVisibility(View.VISIBLE);
                Timber.d("Data loading");
                break;

            case Constants.RESPONSE_SUCCES:
                vm.insertToDatabase(networkResponse.data);
                if (isRefreshing()) mBinding.srLayout.setRefreshing(false);
                mBinding.progressBar.setVisibility(View.GONE);
                Timber.d("Data received");
                break;
        }
    }

    private boolean isRefreshing() {
        return mBinding.srLayout.isRefreshing();
    }

    @Subscribe
    public void onNetworkStateChanged(NetworkConnectionEvent event) {
        if (vm != null) {
            vm.loadData();
        }
    }
}

