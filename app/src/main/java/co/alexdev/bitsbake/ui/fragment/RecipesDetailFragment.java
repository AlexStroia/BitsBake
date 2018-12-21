package co.alexdev.bitsbake.ui.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import co.alexdev.bitsbake.R;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecipesDetailFragment extends BaseFragment {

    int recipeID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe_detail, container, false);
    }

    public void setRecipeId(int id) {
        recipeID = id;
    }
}
