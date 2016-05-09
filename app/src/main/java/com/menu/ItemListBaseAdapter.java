package com.menu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Tortuvshin on 5/10/2016.
 */
public class ItemListBaseAdapter extends BaseAdapter {

    private static ArrayList<ItemDetails> itemDetailsrrayList;

    private Integer[] imgid = {
            R.drawable.p1,
            R.drawable.bb2,
            R.drawable.p2,
            R.drawable.bb5,
            R.drawable.bb6,
            R.drawable.d1
    };

    private LayoutInflater l_Inflater;

    public ItemListBaseAdapter(Context context, ArrayList<ItemDetails> results) {
        itemDetailsrrayList = results;
        l_Inflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return itemDetailsrrayList.size();
    }

    public Object getItem(int position) {
        return itemDetailsrrayList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = l_Inflater.inflate(R.layout.item_details_view, null);
            holder = new ViewHolder();
            holder.txt_itemName = (TextView) convertView.findViewById(R.id.name);
            holder.txt_itemDescription = (TextView) convertView.findViewById(R.id.itemDescription);
            holder.txt_itemPrice = (TextView) convertView.findViewById(R.id.price);
            holder.itemImage = (ImageView) convertView.findViewById(R.id.photo);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txt_itemName.setText(itemDetailsrrayList.get(position).getName());
        holder.txt_itemDescription.setText(itemDetailsrrayList.get(position).getItemDescription());
        holder.txt_itemPrice.setText(itemDetailsrrayList.get(position).getPrice());
        holder.itemImage.setImageResource(imgid[itemDetailsrrayList.get(position).getImageNumber() - 1]);
//		imageLoader.DisplayImage("http://192.168.1.28:8082/ANDROID/images/BEVE.jpeg", holder.itemImage);

        return convertView;
    }

    static class ViewHolder {
        TextView txt_itemName;
        TextView txt_itemDescription;
        TextView txt_itemPrice;
        ImageView itemImage;
    }
}
