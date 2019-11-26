package com.example.uidesign;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.MyViewHolder> {
    static List<Contact> mDataset;
    static RecyclerViewClickListener itemListener;
    Context context;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        // each data item is just a string in this case
        public TextView nameView;
        public TextView phoneView;
        public TextView emailView;
        public ImageView imageView;
        public View view;
        public MyViewHolder(View v) {
            super(v);
            view = v;
            nameView = v.findViewById(R.id.contacts_list_name);
            phoneView = v.findViewById(R.id.contacts_list_phone);
            emailView = v.findViewById(R.id.contacts_list_email);
            imageView = v.findViewById(R.id.contacts_list_image);
            v.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            int pos = this.getLayoutPosition();
            itemListener.recyclerViewListClicked(mDataset.get(pos));

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ContactsAdapter(List<Contact> myDataset, Context context, RecyclerViewClickListener itemListener) {
        mDataset = myDataset;
        this.context = context;
        this.itemListener = itemListener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ContactsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item, parent, false);

        return new MyViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.nameView.setText(mDataset.get(position).name);
        holder.phoneView.setText(mDataset.get(position).number);
        holder.emailView.setText(mDataset.get(position).email);
        if(mDataset.get(position).imageURI != null){
            holder.imageView.setImageURI(Uri.parse(mDataset.get(position).imageURI));
        }
        else{
            Resources resources = context.getResources();
            Uri uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                    + "://" + resources.getResourcePackageName(R.drawable.default_profile)
                    + '/' + resources.getResourceTypeName(R.drawable.default_profile)
                    + '/' + resources.getResourceEntryName(R.drawable.default_profile) );
            holder.imageView.setImageURI(uri);
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}