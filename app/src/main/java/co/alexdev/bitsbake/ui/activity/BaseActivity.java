package co.alexdev.bitsbake.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import co.alexdev.bitsbake.R;
import co.alexdev.bitsbake.databinding.ActivityBaseBinding;
import co.alexdev.bitsbake.events.NetworkConnectionEvent;
import co.alexdev.bitsbake.events.OnRecipeClickEvent;
import co.alexdev.bitsbake.networking.NetworkResponse;
import co.alexdev.bitsbake.receiver.NetworkReceiver;
import co.alexdev.bitsbake.ui.fragment.BaseFragment;
import co.alexdev.bitsbake.ui.fragment.RecipesDetailFragment;
import co.alexdev.bitsbake.ui.fragment.RecipesFragment;
import co.alexdev.bitsbake.utils.BitsBakeUtils;
import co.alexdev.bitsbake.utils.Constants;
import co.alexdev.bitsbake.viewmodel.SharedVM;
import timber.log.Timber;

public class BaseActivity extends AppCompatActivity {

    public static final String INTENT_FILTER_STRING = "android.net.conn.CONNECTIVITY_CHANGE";
    private Toolbar toolbar;
    private NetworkReceiver mNetworkReceiver;
    private IntentFilter mIntentFilter;
    private ActivityBaseBinding mBinding;
    private FragmentManager mFragmentManager;
    public SharedVM vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_base);
        vm = ViewModelProviders.of(this).get(SharedVM.class);
        mFragmentManager = getSupportFragmentManager();

        initView();
    }

    private void initView() {
        setupBroadcastReceiver();
        setupToolbar();
        checkIfIsClickFromWidget();
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
                Timber.d(" Data Error" + networkResponse.status);
                break;

            case Constants.RESPONSE_LOADING:
                Timber.d("Data loading");
                break;

            case Constants.RESPONSE_SUCCES:
                vm.insertToDatabase(networkResponse.data);
                break;
        }
    }

    @Override
    protected void onResume() {
        registerReceiver(mNetworkReceiver, mIntentFilter);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        unregisterReceiver(mNetworkReceiver);
        super.onPause();
    }

    private void setupToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    public void changeFragment(Fragment fragment) {
        if (fragment instanceof RecipesFragment) {
            mFragmentManager.beginTransaction().
                    replace(R.id.fragment_container, fragment).
                    commit();
        } else {
            mFragmentManager.beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.fragment_container, fragment)
                    .commit();
        }
    }

    private void checkIfIsClickFromWidget() {
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(Constants.RECIPE_INGREDIENT_ID_KEY)) {
            int recipeId = intent.getIntExtra(Constants.RECIPE_INGREDIENT_ID_KEY, 0);
            Bundle args = new Bundle();
            args.putInt(getString(R.string.recipe_id), recipeId);
            RecipesDetailFragment recipesDetailFragment = new RecipesDetailFragment();
            recipesDetailFragment.setArguments(args);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    changeFragment(recipesDetailFragment);
                }
            },250);
        }
    }

    private void setupBroadcastReceiver() {
        mNetworkReceiver = new NetworkReceiver();
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(INTENT_FILTER_STRING);
    }

    @Subscribe
    public void onRecipeClickEvent(OnRecipeClickEvent event) {
        Bundle args = new Bundle();
        int recipeId = event.getRecipeId();
        args.putInt(getString(R.string.recipe_id), recipeId);
        BaseFragment baseFragment = new BaseFragment();
        baseFragment.setArguments(args);
        changeFragment(baseFragment);
    }

    /*Monitor for network connectivity changes*/
    @Subscribe
    public void onNetworkStateChanged(NetworkConnectionEvent event) {

        if (vm != null) {
            if (event.getNetworkState()) {
                vm.loadData();
            }
        }
    }
}

