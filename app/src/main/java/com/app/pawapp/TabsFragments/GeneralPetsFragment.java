package com.app.pawapp.TabsFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.app.pawapp.Adapters.ListPetsAdapter;
import com.app.pawapp.Classes.Pets;
import com.app.pawapp.R;

import java.util.ArrayList;

public class GeneralPetsFragment extends Fragment {

    private ListView listView;
    private ListPetsAdapter adapter;

    private View layout;

    public GeneralPetsFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter = new ListPetsAdapter(getContext(), GetArrayItems());
        listView.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        layout = inflater.inflate(R.layout.fragment_generalpets, container, false);

        listView = layout.findViewById(R.id.GeneralList);

        return layout;

    }

    private ArrayList<Pets> GetArrayItems() {

        ArrayList<Pets> list = new ArrayList<>();
        list.add(new Pets(R.drawable.puppy, "Coco", "3 meses", "Color marrón claro. Juguetón.",
                "Perro", "Golden Retriever"));
        list.add(new Pets(R.drawable.puppy, "Firulais", "5 meses", "Color marrón claro. Juguetón.",
                "Perro", "Golden Retriever"));
        return list;

    }

}