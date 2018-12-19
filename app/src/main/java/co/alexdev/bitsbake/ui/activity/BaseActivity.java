package co.alexdev.bitsbake.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import co.alexdev.bitsbake.R;
import co.alexdev.bitsbake.databinding.ActivityBaseBinding;
import co.alexdev.bitsbake.events.NetworkConnectionEvent;
import co.alexdev.bitsbake.receiver.NetworkReceiver;
import co.alexdev.bitsbake.repo.BitsBakeRepository;
import co.alexdev.bitsbake.viewmodel.MainViewModel;
import timber.log.Timber;

public class BaseActivity extends AppCompatActivity {

    public static final String INTENT_FILTER_STRING = "android.net.conn.CONNECTIVITY_CHANGE";
    private Toolbar toolbar;
    private NetworkReceiver mNetworkReceiver;
    private IntentFilter mIntentFilter;
    private MainViewModel vm;
    ActivityBaseBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_base);
        vm = ViewModelProviders.of(this).get(MainViewModel.class);

        setupBroadcastReceiver();
        setupToolbar();

        vm.fetchData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mNetworkReceiver, mIntentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mNetworkReceiver);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    private void setupToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void setupBroadcastReceiver() {
        mNetworkReceiver = new NetworkReceiver();
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(INTENT_FILTER_STRING);
    }

    @Subscribe
    public void onNetworkStateChanged(NetworkConnectionEvent event) {
        Timber.d("NetworkConnectionEvent: " + event.getNetworkState());
        if (event.getNetworkState()) {
            BitsBakeRepository.getInstance(this).fetchData();
        } else {
            Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_LONG).show();
        }
    }
}

