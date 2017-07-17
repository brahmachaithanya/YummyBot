package com.example.brahma.yummybot; //change the package name to your project's package name

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class MyFirebaseRecylerAdapter extends FirebaseRecyclerAdapter<Recipe, MyFirebaseRecylerAdapter.MovieViewHolder> {

    private Context mContext;
    static ClickItem itemClickListener;

    public MyFirebaseRecylerAdapter(Class<Recipe> modelClass, int modelLayout,
                                    Class<MovieViewHolder> holder, Query ref, Context context) {
        super(modelClass, modelLayout, holder, ref);
        this.mContext = context;
    }
    public void SetOnItemClick(ClickItem clickItems){
        this.itemClickListener =  clickItems;
    }
    public interface ClickItem{
        void LongClick(View v, int position);
        void ShortClick(View v, int position);
        void OnThreeDotClick(View v, int position);
    }

    @Override
    protected void populateViewHolder(MovieViewHolder RecipeViewHolder, Recipe recipe, int i)
    {
        //TODO: Populate viewHolder by setting the movie attributes to cardview fields
        try{
        RecipeViewHolder.recipeName.setText(recipe.getRecipe_name());}
        catch (Exception ae){}
        RecipeViewHolder.recipeDesc.setText(recipe.getDescription());
        try {
            Picasso.with(mContext).load(recipe.getImage_URL()).into(RecipeViewHolder.recipeImage);}
        catch (Exception ae){}
        RecipeViewHolder.recipeRating.setRating(Float.parseFloat((String) recipe.getRating()));
    }


    public static class MovieViewHolder extends RecyclerView.ViewHolder {

        final View mView;
        public ImageView recipeImage;
        public RatingBar recipeRating;
        public TextView recipeName;
        public TextView recipeDesc;
        public ImageView pop_up;

        public MovieViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            recipeName = (TextView) itemView.findViewById(R.id.card_name);
            recipeImage = (ImageView) itemView.findViewById(R.id.card_image);
            recipeRating = (RatingBar) itemView.findViewById(R.id.ratingbar);
            recipeDesc = (TextView) itemView.findViewById(R.id.card_desc);



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(itemClickListener != null){
                        itemClickListener.ShortClick(view, getAdapterPosition());
                    }
                }
            });
        }

    }
}
