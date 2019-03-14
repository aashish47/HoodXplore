package com.example.major;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CommentListAdapter extends ArrayAdapter<Comment> {

    private LayoutInflater layoutInflater;
    private int layoutResource;
    private Context mContext;

    public CommentListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Comment> objects) {
        super(context, resource, objects);
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mContext = context;
        layoutResource = resource;
    }

    private static class ViewHolder{
        TextView comment;
        ImageView imageView;
    }

    @NonNull
    @Override
    public View getView(int position,  @Nullable View convertView, @NonNull ViewGroup parent) {
        final ViewHolder holder;

        if(convertView == null){
            convertView = layoutInflater.inflate(layoutResource,parent,false);
            holder = new ViewHolder();
            holder.comment = convertView.findViewById(R.id.tvComment);
            holder.imageView = convertView.findViewById(R.id.ivListImage);

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.comment.setText(getItem(position).getComment());
        String tempUri = null;
        Uri imageUri = null;
        tempUri = getItem(position).getPicture();
        imageUri = Uri.parse(tempUri);
        Picasso.get().load(imageUri).into(holder.imageView);

        return convertView;
    }
}
