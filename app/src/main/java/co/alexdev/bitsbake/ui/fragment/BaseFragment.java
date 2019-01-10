package co.alexdev.bitsbake.ui.fragment;


import android.os.Bundle;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import co.alexdev.bitsbake.viewmodel.RecipeDetailSharedVM;

/*Base Fragment class which other fragments will extend*/
public class BaseFragment extends Fragment {

    RecipeDetailSharedVM vm;

    /*Used to restore recyclerView position when configuration changes occurs */
    static final String RECYCLER_VIEW_POS = "RECYCLER_VIEW_POSITION";
    Parcelable recyclerViewState;

    /*When configuration changes are happening */
    protected void saveRecyclerViewState(@NonNull Bundle outState, RecyclerView.LayoutManager layoutManager) {
        recyclerViewState = layoutManager.onSaveInstanceState();
        outState.putParcelable(RECYCLER_VIEW_POS, recyclerViewState);
    }

    protected void checkRecyclerViewState(Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState.containsKey(RECYCLER_VIEW_POS)) {
            recyclerViewState = savedInstanceState.getParcelable(RECYCLER_VIEW_POS);
        }
    }
}


