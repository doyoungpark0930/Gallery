package com.example.myloginapp.Mypage;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.myloginapp.DesReviewInfo;
import com.example.myloginapp.GalleryInfo;
import com.example.myloginapp.HelperClasses.Adapter.ExhibitionViewAdapter;

import com.example.myloginapp.HelperClasses.Adapter.ReviewAdapter;
import com.example.myloginapp.Object;
import com.example.myloginapp.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private TextView textView1;
    private TextView textView2;
    private ImageView imageView;

    private RecyclerView featuredRecycler;
    private RecyclerView featuredRecycler2;

    private SubscribeViewAdapter adapter;

    private ReviewAdapter adapter2;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UserFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserFragment newInstance(String param1, String param2) {
        UserFragment fragment = new UserFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_user, container, false);

        imageView =  (ImageView) rootView.findViewById(R.id.profile_image);
        textView1 = (TextView) rootView.findViewById(R.id.profile_id);
        textView2 = (TextView) rootView.findViewById(R.id.profile_message);

        featuredRecycler = (RecyclerView) rootView.findViewById(R.id.featured_recycler);
        featuredRecycler2 = (RecyclerView) rootView.findViewById(R.id.featured_recycler2);

        featuredRecycler.setHasFixedSize(true);
        featuredRecycler2.setHasFixedSize(true);

        adapter = new SubscribeViewAdapter();
        ArrayList<DesReviewInfo> tmp=new ArrayList<DesReviewInfo>();
        for(DesReviewInfo i : Object.review){
            if(i.getArtnum()==Object.user.getNum()) {
                tmp.add(i);
            }
        }
        if(tmp!=null)
            adapter2 = new ReviewAdapter(tmp);

        featuredRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        featuredRecycler.setAdapter(adapter);

        featuredRecycler2.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        featuredRecycler2.setAdapter(adapter2);


        imageView.setImageResource(R.drawable.profile);
        textView1.setText(Object.user.getId());
        textView2.setText(Object.user.getEmail());


        Log.e("Frag", "마이페이지 recycler");
        return rootView;
    }

}