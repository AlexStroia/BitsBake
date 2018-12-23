package co.alexdev.bitsbake.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import co.alexdev.bitsbake.R;
import co.alexdev.bitsbake.databinding.ActivityBaseBinding;
import co.alexdev.bitsbake.events.NetworkConnectionEvent;
import co.alexdev.bitsbake.events.OnRecipeClickEvent;
import co.alexdev.bitsbake.networking.NetworkResponse;
import co.alexdev.bitsbake.receiver.NetworkReceiver;
import co.alexdev.bitsbake.ui.fragment.RecipesDetailFragment;
import co.alexdev.bitsbake.ui.fragment.RecipesFragment;
import co.alexdev.bitsbake.utils.BitsBakeUtils;
import co.alexdev.bitsbake.utils.Constants;
import co.alexdev.bitsbake.viewmodel.MainViewModel;
import timber.log.Timber;

public class BaseActivity extends AppCompatActivity {

    public static final String INTENT_FILTER_STRING = "android.net.conn.CONNECTIVITY_CHANGE";
    private Toolbar toolbar;
    private NetworkReceiver mNetworkReceiver;
    private IntentFilter mIntentFilter;
    private ActivityBaseBinding mBinding;
    public MainViewModel vm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_base);
        vm = ViewModelProviders.of(this).get(MainViewModel.class);

        mBinding.srLayout.setOnRefreshListener(() -> vm.loadData());

        initView();
    }

    private void initView() {
        setupBroadcastReceiver();
        setupToolbar();
        loadRecipesFragment();
        vm.getNetworkResponse().observe(this, networkResponse -> processResponse(networkResponse));
    }

    private void loadRecipesFragment() {
        Fragment fragment = new RecipesFragment();
        changeFragment(fragment);
    }

    private void processResponse(NetworkResponse networkResponse) {
        switch (networkResponse.status) {
            case Constants.RESPONSE_ERROR:
                BitsBakeUtils.showAlert(this, networkResponse.error.getMessage());
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

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mNetworkReceiver, mIntentFilter);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mNetworkReceiver);
    }

    private void setupToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    public void changeFragment(Fragment fragment) {


        getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragment_container, fragment)
                .commit();
        Timber.d("Backstack size: " + getSupportFragmentManager().getBackStackEntryCount());
    }

    private void setupBroadcastReceiver() {
        mNetworkReceiver = new NetworkReceiver();
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(INTENT_FILTER_STRING);
    }

    private boolean isRefreshing() {
        return mBinding.srLayout.isRefreshing();
    }

    @Subscribe
    public void onRecipeClickEvent(OnRecipeClickEvent event) {
        Bundle args = new Bundle();
        String recipeName = event.getRecipeName();
        args.putString(getString(R.string.recipe_name), recipeName);
        RecipesDetailFragment recipesDetailFragment = new RecipesDetailFragment();
        recipesDetailFragment.setArguments(args);
        changeFragment(recipesDetailFragment);
    }

    @Subscribe
    public void onNetworkStateChanged(NetworkConnectionEvent event) {
        if (vm != null) {
            vm.loadData();
        }
    }
}

