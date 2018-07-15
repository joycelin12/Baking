package com.example.android.baking;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.baking.Model.Recipe;

import org.json.JSONException;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by joycelin12 on 7/14/18.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.NumberViewHolder> {

    //add a variable to display the number of items
    private int mNumberItems;
    private ArrayList<Recipe> mData = new ArrayList<Recipe>();
    private RecipeAdapter.ItemClickListener mClickListener;
    private Context mContext;

    //create a constructor that accepts int as a parameter for number of items and store in the variable
    public RecipeAdapter(int numberOfItems, ArrayList<Recipe> data, Context context){
        mNumberItems = numberOfItems;
        this.mData = data;
        this.mContext = context;
    }


    @Override
    public NumberViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        int layoutIdForItem = R.layout.recipes_list_item;
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForItem, viewGroup, shouldAttachToParentImmediately);
        NumberViewHolder viewHolder = new NumberViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecipeAdapter.NumberViewHolder holder, int position) {

        String name = mData.get(position).getName();
        //String name = "testing";
        holder.listName.setText(name);

    }

    @Override
    public int getItemCount() {
       return mNumberItems;
    }

    public class NumberViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.recipe_name)
        TextView listName;


        public NumberViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) try {
                mClickListener.onItemClick(view, getAdapterPosition());

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    // convenience method for getting data at click position
    Recipe getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position) throws JSONException;
    }
}
