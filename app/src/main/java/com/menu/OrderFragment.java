package com.menu;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.menu.Database.ProductAdapter;
import com.menu.Model.Product;

import java.util.ArrayList;
import java.util.HashMap;

public class OrderFragment extends Fragment {

    public final static String  TAG = "com.menu";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_order, container, false);

        final ProductAdapter productAdapter = new ProductAdapter(this.getActivity());
        ArrayList<Product> data = productAdapter.readData();

        // Get a reference to the ListView, and attach this adapter to it.
        ListView listView = (ListView) rootView.findViewById(R.id.fragment_item_ordered_list_view);

        final ArrayList<HashMap<String, String>> dataList;

        dataList = new ArrayList<HashMap<String, String>>();

        // adding each child node to HashMap key => value
        for (Product x : data) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("Title", x.getTitle());
            map.put("TotalOrder", "Total Item: " + x.getTotalOrder());
            map.put("TotalCost", "Total Cost: $" + x.getTotalCost());
            dataList.add(map);
        }

        ListAdapter adapter = new SimpleAdapter(
                this.getActivity(),
                dataList,
                R.layout.list_image,
                new String[]{"Title", "TotalOrder", "TotalCost"},
                new int[]{R.id.list_image_text_view, R.id.recepit_total_order_text_view, R.id.recepit_cost_text_view});

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String title = dataList.get(position).get("Title");
                Product item = productAdapter.findByTitle(title);
                String costString = "" + item.getCost();
                String totalCostString = "" + item.totalCost;
                String totalOrderString = "" + item.getTotalOrder();
                String ratingString = "" + item.getRatinng();
                String[] transferData = {item.getTitle(), item.getDescription(), costString, totalCostString, totalOrderString, item.getImage(),ratingString};
                Intent intent = new Intent(getActivity(), MenuEditActivity.class);
                intent.putExtra(TAG, transferData);
                startActivity(intent);
            }
        });
        productAdapter.close();
        return rootView;

    }



}
