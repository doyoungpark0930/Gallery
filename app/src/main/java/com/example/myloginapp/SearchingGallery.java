package com.example.myloginapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
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

import com.example.myloginapp.Description.DesReviewInfo;
import com.example.myloginapp.Description.Description;

import java.io.ByteArrayOutputStream;
import java.io.FilterReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class SearchingGallery extends AppCompatActivity {
    SearchView searchView;
    ListView listView;


    StringBuilder ArtTime;
    String getArtTime;
    byte[] ImageBt;
    String ArtName;
    String ArtDsc;
    ArrayList<DesReviewInfo> ReviewList; //해당 뷰 객체의 리뷰리스트

    CustomAdapter customAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searching_gallery);

        searchView=findViewById(R.id.search);
        listView=findViewById(R.id.listView);


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
        public View getView(int position, View convertView, ViewGroup parent) { //listview의 해당 뷰에 이미지,이름,설명을 뿌린다
            View view = getLayoutInflater().inflate(R.layout.row_items,null); //row_items를 view객체로 만든다.

            ImageView imageView = view.findViewById(R.id.imageView);
            TextView itemName= view.findViewById(R.id.itemName);
            TextView itemDesc = view.findViewById(R.id.itemDesc);

            imageView.setImageBitmap(itemsModelListFiltered.get(position).getImage());
            itemName.setText(itemsModelListFiltered.get(position).getName());
            itemDesc.setText(itemsModelListFiltered.get(position).PrintArt()); //PrintArt는 시작날짜,종료날짜,가격

            view.setOnClickListener(new View.OnClickListener() { //해당 뷰를 클릭하면 Description클래스로 페이지전환
                @Override
                public void onClick(View v) { //해당 뷰 객체의 이미지,설명 이런 것들을 intent를 이용해 넘겨줌


                    ArtTime=(StringBuilder)  itemsModelListFiltered.get(position).PrintArt();
                    getArtTime=ArtTime.toString(); //StringBuilder인 ArtTime을 String형으로 변경
                    ImageBt=bitmap2Bytes(itemsModelListFiltered.get(position).getImage()); //bitmap인 이미지를 byte값으로 변환
                    ArtName=(String)itemsModelListFiltered.get(position).getName();
                    ArtDsc=(String)itemsModelListFiltered.get(position).getDesc();
                    ReviewList=(ArrayList<DesReviewInfo>) itemsModelListFiltered.get(position).getDesReviewInfo();
                    Intent intent=new Intent(SearchingGallery.this, Description.class); //intent를 이용해 Activity전환
                    //이렇게 putExtra로 값을 전달하고 Description.java에서 GetExtra로 값을 받는다
                    intent.putExtra("ArtTime",getArtTime);
                    intent.putExtra("Image",ImageBt); //일단 byte값을 넘기고 getExtra에서 byte를 bitmap으로 다시 변환
                    intent.putExtra("Name",ArtName);
                    intent.putExtra("ArtInfo",ArtDsc);
                    intent.putExtra("ReviewList",ReviewList);
                    startActivity(intent);

                }
            });
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
                        String searchStr = constraint.toString();

                        List<GalleryInfo> resultData = new ArrayList<>();

                        for(GalleryInfo itemsModel:itemsModelList){
                            if(itemsModel.getName().contains(searchStr)|| itemsModel.PrintArt().toString().contains(searchStr)){
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
    private byte[] bitmap2Bytes(Bitmap bitmap) { //Bitmap을 byte형식으로 바꿔주는 메소드
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return baos.toByteArray();
    }
}
