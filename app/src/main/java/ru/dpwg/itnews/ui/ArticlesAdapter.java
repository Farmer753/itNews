package ru.dpwg.itnews.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ru.dpwg.itnews.R;
import ru.dpwg.itnews.domain.article.NwArticle;

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ViewHolder> {

    public ArticlesAdapter(ArticleClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public interface ArticleClickListener{
        public void onClick (NwArticle article);
    }


    private final List<NwArticle> articles = new ArrayList<>();
    private ArticleClickListener clickListener;

    public void setArticles(List<NwArticle> articleList) {
        articles.clear();
        articles.addAll(articleList);
        notifyDataSetChanged();
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_article, parent, false);
        return new ArticlesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NotNull ArticlesAdapter.ViewHolder holder, int position) {
        holder.titleTextView.setText(articles.get(position).translations.get(0).title);
        holder.dateTextView.setText(articles.get(position).publishedDate);
        holder.articleTextView.setText(articles.get(position).translations.get(0).shortDescription);
        holder.itemView.setOnClickListener(v -> clickListener.onClick(articles.get(position)));
        Glide.with(holder.articleImageView)
                .load("https://dont-play-with-google.com:8443/api/" + articles.get(position).translations.get(0).imageUrl)
                .into(holder.articleImageView);
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView articleImageView;
        public TextView titleTextView;
        public TextView dateTextView;
        public TextView articleTextView;

        public ViewHolder(@NonNull @NotNull View view) {
            super(view);

            articleImageView = view.findViewById(R.id.articleImageView);
            titleTextView = view.findViewById(R.id.titleTextView);
            dateTextView = view.findViewById(R.id.dateTextView);
            articleTextView = view.findViewById(R.id.articleTextView);
        }
    }

}
