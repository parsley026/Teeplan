package com.example.teeplan.coupon;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teeplan.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CouponFragment extends Fragment {

    private ArrayList<CouponModel> couponModels = new ArrayList<>();
    private CouponRecyclerViewAdapter adapter;

    public CouponFragment() {
        // Required empty public constructor
    }

    public static CouponFragment newInstance() {
        return new CouponFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_coupon, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.couponRecylerView);
        setUpRecyclerView();

        adapter = new CouponRecyclerViewAdapter(getActivity(), couponModels);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        retrieveDataFromDatabase();

        return view;
    }

    private void setUpRecyclerView() {
        // You can add any setup code here if needed
    }

    private void retrieveDataFromDatabase() {
        DatabaseReference couponsRef = FirebaseDatabase.getInstance().getReference().child("coupons");

        couponsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String name = snapshot.child("name").getValue(String.class);
                    String description = snapshot.child("description").getValue(String.class);
                    String code = snapshot.child("code").getValue(String.class);

                    couponModels.add(new CouponModel(name, description, code));
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("CouponFragment", "Failed to retrieve data", databaseError.toException());
            }
        });
    }
}
