package co.alexdev.bitsbake.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import co.alexdev.bitsbake.R;
import co.alexdev.bitsbake.databinding.ActivityDetailBinding;
import co.alexdev.bitsbake.events.IsTwoPaneEvent;
import co.alexdev.bitsbake.ui.fragment.BaseFragment;
import co.alexdev.bitsbake.ui.fragment.RecipeVideoDialogFragment;

import android.content.Intent;
import android.os.Bundle;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class RecipeDetailActivity extends AppCompatActivity {

    private FragmentManager mFragmentManager;
    private ActivityDetailBinding mBinding;
    public boolean mTwoPane = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        Intent intent = getIntent();
        setupBaseFragment(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    private void initView() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        mFragmentManager = getSupportFragmentManager();

        if (mBinding.fragmentVideoContainer != null) {
            mTwoPane = true;
        }
    }

    private void setupBaseFragment(Intent intent) {
        String recipeKeyId = getString(R.string.recipe_id);
        if (intent != null && intent.hasExtra(recipeKeyId)) {
            int recipeID = intent.getIntExtra(recipeKeyId, 0);
            Bundle args = new Bundle();
            args.putInt(recipeKeyId, recipeID);
            BaseFragment baseFragment = new BaseFragment();
            baseFragment.setArguments(args);
            changeFragment(baseFragment);
        }
    }

    public void changeFragment(Fragment fragment) {

        if (fragment instanceof BaseFragment) {
            mFragmentManager.beginTransaction().
                    replace(R.id.fragment_container, fragment).
                    commit();
        } else if (fragment instanceof  RecipeVideoDialogFragment) {
            mBinding.fragmentVideoContainer.removeAllViews();
            mFragmentManager.beginTransaction()
                    .add(R.id.fragment_video_container, fragment)
                    .addToBackStack(null)
                    .commit();
        } else {
            mFragmentManager.beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.fragment_container, fragment)
                    .commit();
        }
    }

    @Subscribe
    public void isTwoPaneEvent(IsTwoPaneEvent event) {
        boolean isTwoPane = event.isTwoPane();
        if(isTwoPane) {
            changeFragment(event.getRecipeVideoDialogFragment());
        }
    }
}
