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

import androidx.recyclerview.widget.RecyclerView;
import ru.dpwg.itnews.R;
import ru.dpwg.itnews.domain.article.ui.UiArticle;
import ru.dpwg.itnews.domain.comment.NwComment;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> {

    private final List<NwComment> comments = new ArrayList<>();

    public interface DeleteClickListener {
        void onClick(NwComment comment);
    }

    private CommentsAdapter.DeleteClickListener clickListener;

    public CommentsAdapter(DeleteClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_comment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NotNull CommentsAdapter.ViewHolder holder, int position) {
        holder.userNameTextView.setText(comments.get(position).author.fullName);
        holder.dateTextView.setText(comments.get(position).created);
        holder.commentTextView.setText(comments.get(position).text);
        Glide.with(holder.iconImageView)
                .load(comments.get(position).author.avatar)
                .into(holder.iconImageView);
        holder.deleteImageView.setOnClickListener(view -> clickListener.onClick(comments.get(position)));
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public void setComments(List<NwComment> commentList) {
        comments.clear();
        comments.addAll(commentList);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView iconImageView;
        public TextView userNameTextView;
        public TextView dateTextView;
        public TextView commentTextView;
        public ImageView deleteImageView;

        ViewHolder(View view) {
            super(view);
            iconImageView = view.findViewById(R.id.iconImageView);
            userNameTextView = view.findViewById(R.id.userNameTextView);
            dateTextView = view.findViewById(R.id.dateTextView);
            commentTextView = view.findViewById(R.id.commentTextView);
            deleteImageView = view.findViewById(R.id.deleteImageView);
        }
    }
}
