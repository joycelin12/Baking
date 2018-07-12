package com.example.android.baking;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.baking.Model.Steps;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;



/**
 * Created by joycelin12 on 7/12/18.
 */

public class StepsAdapter extends RecyclerView.Adapter <StepsAdapter.NumberViewHolder> {



    private ArrayList<Steps> mData = new ArrayList<>();
    private Context mContext;
    private StepsAdapter.ItemClickListener mClickListener;

    //add a private int variable called mNumberItems
    private int mNumberItems;

    public StepsAdapter(int numberOfItems, ArrayList<Steps> data, Context context) {
        mNumberItems = numberOfItems;
        this.mData = data;
        this.mContext = context;

    }

    @Override
    public NumberViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.steps_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        NumberViewHolder viewHolder = new NumberViewHolder(view);

        return viewHolder;
    }

    public class NumberViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.stepsShort)
        TextView listItemStepsShortTextView;
        @BindView(R.id.stepsDescription)
        TextView listItemStepsDescriptionTextView;
        @BindView(R.id.stepsImage)
        ImageView listItemStepsImageView;

        public NumberViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mClickListener != null) try {
                mClickListener.onItemClick(v, getAdapterPosition());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onBindViewHolder(StepsAdapter.NumberViewHolder holder, int position) {

        Steps steps = mData.get(position);
        Context context = holder.listItemStepsImageView.getContext();
        if (!steps.getThumbnailURL().isEmpty())  {
            holder.listItemStepsImageView.setVisibility(View.VISIBLE);
            Picasso.with(context)
                    .load(steps.getThumbnailURL())
                    .placeholder(R.drawable.user_placeholder)
                    .error(R.drawable.user_placeholder_error)
                    .into(holder.listItemStepsImageView);
        }

        holder.listItemStepsShortTextView.setText(steps.getShortDescription());
        holder.listItemStepsDescriptionTextView.setText(steps.getDescription());

    }


    @Override
    public int getItemCount() {
        return mNumberItems;
    }

    Steps getItem(int id){
        return mData.get(id);
    }

    void setClickListener(StepsAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener{
        void onItemClick(View view, int position) throws JSONException;
    }
}