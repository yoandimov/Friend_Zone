package com.example.friendzone.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.friendzone.models.Commentaire;
import com.example.friendzone.R;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentHolder>{

    private Context context;
    private List<Commentaire> commentaireList;
    int cid;

    public CommentAdapter(Context context, List<Commentaire> commentaireList) {
        this.context = context;
        this.commentaireList = commentaireList;
    }

    @NonNull
    @Override
    public CommentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_comment, parent, false);

        return new CommentHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentHolder holder, int position) {
        cid = commentaireList.get(position).getCommentaireId();
        int uid = commentaireList.get(position).getUserId();
        String uName = commentaireList.get(position).getUsername();
        String uImage = commentaireList.get(position).getProfileImage();
        String timeStamp = commentaireList.get(position).getDateCreated();
        String message = commentaireList.get(position).getMessage();

        holder.nameTv.setText(uName);
        if (uImage != null) {
            if(uImage.contains("https://")){
                Glide.with(holder.avatarIv.getContext())
                        .setDefaultRequestOptions(new RequestOptions()
                                .circleCrop())
                        .load(uImage)
                        .into(holder.avatarIv);
            } else {
                byte[] decodedString = Base64.decode(uImage, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                holder.avatarIv.setImageBitmap(decodedByte);
            }
        }
        //holder.timeTv.setText(timeStamp);
        holder.commentTv.setText(message);


    }

    @Override
    public int getItemCount() {
        return commentaireList.size();
    }

    class CommentHolder extends RecyclerView.ViewHolder{
        ImageView avatarIv;
        TextView nameTv, commentTv, timeTv;


        public CommentHolder(@NonNull View itemView) {
            super(itemView);
            avatarIv = itemView.findViewById(R.id.avatarIv);
            nameTv = itemView.findViewById(R.id.nameTv);
            commentTv = itemView.findViewById(R.id.commentTv);
            //timeTv = itemView.findViewById(R.id.timeTv);
        }
    }
}
