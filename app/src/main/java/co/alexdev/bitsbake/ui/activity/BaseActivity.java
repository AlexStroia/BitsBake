package co.alexdev.bitsbake.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.IntentFilter;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import co.alexdev.bitsbake.R;
import co.alexdev.bitsbake.receiver.NetworkReceiver;

public class BaseActivity extends AppCompatActivity {

    public static final String INTENT_FILTER_STRING = "android.net.conn.CONNECTIVITY_CHANGE";
    private Toolbar toolbar;
    private NetworkReceiver mNetworkReceiver;
    private IntentFilter mIntentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        initView();
    }

    private void initView() {
        setupBroadcastReceiver();
        setupToolbar();
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
}

