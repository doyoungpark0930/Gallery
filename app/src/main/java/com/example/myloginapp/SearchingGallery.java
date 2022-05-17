package com.example.myloginapp;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class SearchingGallery extends AppCompatActivity {
    SearchView searchView;
    ListView listView;

    int images[] = {R.drawable.project_gallery,R.drawable.project_gallery,R.drawable.project_gallery,R.drawable.project_gallery,R.drawable.project_gallery};     //이미지 가져와서 동적으로 넣어줘야함
    String names[]={"apple","banana","kiwi","watermelon","orange"};      //여기나중에 동적으로 끌어서 넣어줘야함  ,일단 소문자만 넣어야함
    String desc[]={"This is  apple","This is Banana","This is Kiwi","This is Watermelon","This is orange"};  //여기도 동적으로 넣어줘야함

    List<GalleryInfo> listItems = new ArrayList<>();

    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searching_gallery);

        searchView=findViewById(R.id.search);
        listView=findViewById(R.id.listView);
/*
        for(int i=0;i<names.length;i++){
            GalleryInfo itemsModel = new GalleryInfo(names[i],desc[i],images[i]);

            listItems.add(itemsModel);
        } */

        customAdapter = new CustomAdapter(Object.art,this);

        listView.setAdapter(customAdapter);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                customAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }


    public class CustomAdapter extends BaseAdapter implements Filterable {

        private List<GalleryInfo> itemsModelList;
        private List<GalleryInfo> itemsModelListFiltered;
        private Context context;

        public CustomAdapter(List<GalleryInfo> itemsModelList, Context context) {
            this.itemsModelList = itemsModelList;
            this.itemsModelListFiltered= itemsModelList;
            this.context = context;
        }

        @Override
        public int getCount() {
            return itemsModelListFiltered.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = getLayoutInflater().inflate(R.layout.row_items,null);

            ImageView imageView = view.findViewById(R.id.imageView);
            TextView itemName= view.findViewById(R.id.itemName);
            TextView itemDesc = view.findViewById(R.id.itemDesc);

            //imageView.setImageResource(itemsModelListFiltered.get(position).getImage());
            itemName.setText(itemsModelListFiltered.get(position).getName());
            itemDesc.setText(itemsModelListFiltered.get(position).getDesc());

            return view;
        }

        @Override
        public Filter getFilter() {

            Filter filter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults filterResults = new FilterResults();

                    if(constraint == null || constraint.length() == 0){
                        filterResults.count = itemsModelList.size();
                        filterResults.values = itemsModelList;
                    }
                    else {
                        String searchStr = constraint.toString().toLowerCase();

                        List<GalleryInfo> resultData = new ArrayList<>();

                        for(GalleryInfo itemsModel:itemsModelList){
                            if(itemsModel.getName().contains(searchStr)|| itemsModel.getDesc().contains(searchStr)){
                                resultData.add(itemsModel);

                            }

                            filterResults.count= resultData.size();
                            filterResults.values= resultData;
                        }
                    }

                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {

                    itemsModelListFiltered = (List<GalleryInfo>) results.values;

                    notifyDataSetChanged();

                }
            };
            return filter;
        }
    }
}
