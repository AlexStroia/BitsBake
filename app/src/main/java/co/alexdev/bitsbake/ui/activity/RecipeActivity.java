package co.alexdev.bitsbake.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import co.alexdev.bitsbake.BuildConfig;
import co.alexdev.bitsbake.R;
import co.alexdev.bitsbake.databinding.ActivityBaseBinding;
import co.alexdev.bitsbake.events.NetworkConnectionEvent;
import co.alexdev.bitsbake.events.OnRecipeClickEvent;
import co.alexdev.bitsbake.networking.NetworkResponse;
import co.alexdev.bitsbake.receiver.NetworkReceiver;
import co.alexdev.bitsbake.repo.BitsBakeRepository;
import co.alexdev.bitsbake.ui.fragment.RecipesDetailFragment;
import co.alexdev.bitsbake.ui.fragment.RecipesFragment;
import co.alexdev.bitsbake.utils.BitsBakeUtils;
import co.alexdev.bitsbake.utils.Constants;
import co.alexdev.bitsbake.viewmodel.RecipeActivityVM;
import co.alexdev.bitsbake.viewmodel.factory.ViewModelFactory;
import timber.log.Timber;

public class RecipeActivity extends AppCompatActivity {

    public static final String INTENT_FILTER_STRING = "android.net.conn.CONNECTIVITY_CHANGE";
    private Toolbar toolbar;
    private NetworkReceiver mNetworkReceiver;
    private IntentFilter mIntentFilter;
    private ActivityBaseBinding mBinding = null;
    private FragmentManager mFragmentManager;
    public boolean mTwoPane = false;
    public RecipeActivityVM vm;
    private ViewModelFactory mFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_base);
        mFactory = new ViewModelFactory(BitsBakeRepository.getInstance(this));
        vm = ViewModelProviders.of(this, mFactory).get(RecipeActivityVM.class);
        mFragmentManager = getSupportFragmentManager();

        initView();

        /*Just for testing purposes */
        if (BuildConfig.DEBUG) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
    }


    private void initView() {
        setupBroadcastReceiver();
        setupToolbar();
        checkIfIsFromWidget();
        loadRecipesFragment();
        vm.getNetworkResponse().observe(this, this::processResponse);
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
                Timber.d("Data success");
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

    private void checkIfIsFromWidget() {
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(Constants.RECIPE_INGREDIENT_ID_KEY)) {
            int recipeId = intent.getIntExtra(Constants.RECIPE_INGREDIENT_ID_KEY, 0);
            Timber.d("Recipe id: " + recipeId);
            Bundle args = new Bundle();
            args.putInt(getString(R.string.recipe_id), recipeId);
            RecipesDetailFragment recipesDetailFragment = new RecipesDetailFragment();
            recipesDetailFragment.setArguments(args);
            new Handler().postDelayed(() -> changeFragment(recipesDetailFragment), 250);
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

        Intent intent = new Intent(this, RecipeDetailActivity.class);
        intent.putExtra(getString(R.string.recipe_id), recipeId);
        startActivity(intent);
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

