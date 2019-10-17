package com.example.bakingapp.ui;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingapp.R;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

public class StepsRecyclerAdapter extends RecyclerView.Adapter<StepsRecyclerAdapter.StepsViewHolder> {

    int count;
    OnStepSelectedListener mClickListener;

    public interface OnStepSelectedListener{
        void onStepClick(int position);
    }

    public StepsRecyclerAdapter(int count) {
        this.count = count;
    }

    @NonNull
    @Override
    public StepsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        StepsViewHolder viewHolder;

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recycler_view, parent, false );

        viewHolder = new StepsViewHolder(view);

        viewHolder.mDescription.setText(String.valueOf(count));

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StepsViewHolder holder, int position) {
        Log.d(StepsRecyclerAdapter.class.getSimpleName(), "position: "+ String.valueOf(position));
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return count;
    }


    public class StepsViewHolder extends RecyclerView.ViewHolder implements OnStepSelectedListener{

        TextView mDescription;
        SimpleExoPlayerView simpleExoPlayerView;

        public StepsViewHolder(@NonNull View itemView) {
            super(itemView);

            mDescription = (TextView) itemView.findViewById(R.id.description_textView);
            simpleExoPlayerView = (SimpleExoPlayerView) itemView.findViewById(R.id.playerView);
        }

       public void bind(int position){
            mDescription.setText("test");
       }

        @Override
        public void onStepClick(int position) {
            //int position  = getAdapterPosition();
            mClickListener.onStepClick(position);
        }
    }
}
