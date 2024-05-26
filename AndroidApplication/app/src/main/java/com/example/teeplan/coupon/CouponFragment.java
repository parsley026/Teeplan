package com.example.teeplan.coupon;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teeplan.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CouponFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CouponFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CouponFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CouponFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CouponFragment newInstance(String param1, String param2) {
        CouponFragment fragment = new CouponFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    ArrayList<CouponModel> couponModels = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        Log.e("Coupon", "Coupon on create");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e("Coupon", "Coupon on createView");
        View view = inflater.inflate(R.layout.fragment_coupon, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.couponRecylerView);
        setUpCouponModels();

        CouponRecyclerViewAdapter adapter = new CouponRecyclerViewAdapter(getActivity(), couponModels);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    private void setUpCouponModels() {
        couponModels.add(new CouponModel("Żabka", "piwsko za darmo", "123981"));
        couponModels.add(new CouponModel("Lidl", "piwsko za pól-darmo", "542123"));
        couponModels.add(new CouponModel("Juwenalia", "juwenalia za darmo", "942125"));

        couponModels.add(new CouponModel("Żabka", "piwsko za darmo", "123981"));
        couponModels.add(new CouponModel("Lidl", "piwsko za pól-darmo", "542123"));
        couponModels.add(new CouponModel("Juwenalia", "juwenalia za darmo", "942125"));

        couponModels.add(new CouponModel("Żabka", "piwsko za darmo", "123981"));
        couponModels.add(new CouponModel("Lidl", "piwsko za pól-darmo", "542123"));
        couponModels.add(new CouponModel("Juwenalia", "juwenalia za darmo", "942125"));

        couponModels.add(new CouponModel("Żabka", "piwsko za darmo", "123981"));
        couponModels.add(new CouponModel("Lidl", "piwsko za pól-darmo", "542123"));
        couponModels.add(new CouponModel("Juwenalia", "juwenalia za darmo", "942125"));


        couponModels.add(new CouponModel("Żabka", "piwsko za darmo", "123981"));
        couponModels.add(new CouponModel("Lidl", "piwsko za pól-darmo", "542123"));
        couponModels.add(new CouponModel("Juwenalia", "juwenalia za darmo", "942125"));
    }
}