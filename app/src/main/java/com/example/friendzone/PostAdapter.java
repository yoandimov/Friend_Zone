package com.example.friendzone;

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

import com.example.friendzone.Models.Post;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostHolder> {

    private Context mContext;
    private List<Post> mPostList;
    private OnPostClickListener mListener;

    public interface OnPostClickListener {
        void onPostClick(int id);
    }

    public void setOnPostClickListener(OnPostClickListener listener) {
        mListener = listener;
    }

    public PostAdapter(Context context, List<Post> postList) {
        mPostList = postList;
        mContext = context;
    }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_post, parent, false);

        return new PostHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, int position) {

        //int uid = mPostList.get(position).getUserId();
        //int pid = mPostList.get(position).getPostId();
        String pTitle = mPostList.get(position).getTitle();
        String pTimeStamp = mPostList.get(position).getDateCreated();
        String pContent = mPostList.get(position).getContent();
        String pImage = mPostList.get(position).getImage();


        holder.pTimeTv.setText(pTimeStamp);
        if (pTitle != null) {
            holder.pTitleTv.setVisibility(View.VISIBLE);
            holder.pTitleTv.setText(pTitle);
        }
        if (pContent != null) {
            holder.pDescriptionTv.setVisibility(View.VISIBLE);
            holder.pDescriptionTv.setText(pContent);
        }
        if (pImage != null) {
            holder.pImageIv.setVisibility(View.VISIBLE);
            byte[] decodedString = Base64.decode(pImage, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            holder.pImageIv.setImageBitmap(decodedByte);
        }

    }

    @Override
    public int getItemCount() {
        return mPostList.size();
    }


    class PostHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView uPictureIv, pImageIv;
        TextView uNameTv, pTimeTv, pTitleTv, pDescriptionTv;

        public PostHolder(@NonNull View itemView) {
            super(itemView);

            uPictureIv = itemView.findViewById(R.id.uPictureIv);
            pImageIv = itemView.findViewById(R.id.pImageIv);
            uNameTv = itemView.findViewById(R.id.uNameTv);
            pTimeTv = itemView.findViewById(R.id.pTimeTv);
            pTitleTv = itemView.findViewById(R.id.pTitleTv);
            pDescriptionTv = itemView.findViewById(R.id.pDescriptionTv);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onPostClick(position);
                        }
                    }
                }
            });
        }

        @Override
        public void onClick(View v) {

        }
    }
}
