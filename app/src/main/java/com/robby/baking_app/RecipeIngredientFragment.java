package com.robby.baking_app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A fragment representing a single Recipe detail screen.
 * This fragment is either contained in a {@link RecipeListActivity}
 * in two-pane mode (on tablets) or a {@link RecipeIngredientActivity}
 * on handsets.
 *
 * @author Robby Tan
 */
public class RecipeIngredientFragment extends Fragment {

    @BindView(R.id.tv_ingredient)
    TextView tvIngredient;
//    @BindView(R.id.ingredient_toolbar_layout)
//    CollapsingToolbarLayout collapsingToolbarLayout;

    private String ingredientsInString;

    public RecipeIngredientFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(getActivity().getResources()
                .getString(R.string.send_string_ingredients_key))) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            ingredientsInString = getArguments().getString(getActivity().getResources()
                    .getString(R.string.send_string_ingredients_key));
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        tvIngredient.setText(ingredientsInString);
//        collapsingToolbarLayout.setTitle("Ingredients");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.ingredient_detail, container, false);
    }
}
