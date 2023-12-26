package com.burta.burtaxi;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FragmentDorduncu extends Fragment {
    private Toolbar toolbar_fragmentDort;
    private RecyclerView rv_fragmentDort;

    private ArrayList<Calisanlar> calisanlarArrayList;
    private CalisanlarAdapterCagir adapter;

    private DatabaseReference calisanlarRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_dorduncu, container, false);

        toolbar_fragmentDort = rootView.findViewById(R.id.toolbar_seyahatler);
        rv_fragmentDort = rootView.findViewById(R.id.rv_seyahatler);

        toolbar_fragmentDort.setTitle("Seyahat Listesi");
        toolbar_fragmentDort.setTitleTextColor(Color.BLACK);

        rv_fragmentDort.setHasFixedSize(true);
        rv_fragmentDort.setLayoutManager(new LinearLayoutManager(getContext()));

        calisanlarArrayList = new ArrayList<>();
        adapter = new CalisanlarAdapterCagir(rootView.getContext(), calisanlarArrayList);

        calisanlarRef = FirebaseDatabase.getInstance().getReference().child("Calisanlar");

        calisanlarRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                calisanlarArrayList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Calisanlar calisanlar = snapshot.getValue(Calisanlar.class);
                    calisanlarArrayList.add(calisanlar);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Hata durumu
            }
        });

        rv_fragmentDort.setAdapter(adapter);

        return rootView;

    }
}
