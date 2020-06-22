package com.dreamgyf.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class LoadingRecyclerAdapter extends RecyclerView.Adapter<LoadingRecyclerAdapter.ViewHolder> {

	private Context mContext;

	private List<Article> data;

	public LoadingRecyclerAdapter(Context context) {
		mContext = context;
		data = new ArrayList<>();
	}

	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(mContext).inflate(R.layout.loading_recycler_view_item, parent,false);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
		holder.tv_title.setText(data.get(position).getTitle());
		holder.tv_description.setText(data.get(position).getDescription());
	}

	@Override
	public int getItemCount() {
		return data.size();
	}

	class ViewHolder extends RecyclerView.ViewHolder{

		TextView tv_title;
		TextView tv_description;

		public ViewHolder(@NonNull View itemView) {
			super(itemView);
			tv_title = itemView.findViewById(R.id.tv_title);
			tv_description = itemView.findViewById(R.id.tv_description);
		}
	}

	public void addDataAtStart(Article article) {
		data.add(0,article);
		notifyItemRangeInserted(0,1);
	}
	public void addDataAtStart(List<Article> articles) {
		data.addAll(0,articles);
		notifyItemRangeInserted(0,articles.size());
	}

	public void addDataAtEnd(Article article) {
		data.add(article);
		notifyDataSetChanged();
	}

	public void addDataAtEnd(List<Article> articles) {
		data.addAll(articles);
		notifyDataSetChanged();
	}
}
