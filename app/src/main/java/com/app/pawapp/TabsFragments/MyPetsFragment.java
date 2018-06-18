package com.app.pawapp.TabsFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.app.pawapp.Adapters.ListPetsAdapter;
import com.app.pawapp.Classes.Pets;
import com.app.pawapp.DataAccess.Entity.Pet;
import com.app.pawapp.R;

import java.util.ArrayList;

public class    MyPetsFragment extends Fragment {

    private ListView listView;
    private ListPetsAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_mypets, container, false);

        listView = v.findViewById(R.id.MyPetsList);
        adapter = new ListPetsAdapter(getContext(), GetArrayItems());
        listView.setAdapter(adapter);

        return v;
    }

    private ArrayList<Pets> GetArrayItems() {

        ArrayList<Pets> list = new ArrayList<>();
        list.add(new Pets(R.drawable.puppy, "Coco", "3 meses", "Color marrón claro. Juguetón.",
                "Perro", "Golden Retriever"));
        list.add(new Pets(R.drawable.puppy, "Firulais", "5 meses", "Color marrón claro. Juguetón.",
                "Perro", "Golden Retriever"));
        list.add(new Pets(R.drawable.puppy, "Firulais", "5 meses", "Color marrón claro. Juguetón.",
                "Perro", "Golden Retriever"));
        list.add(new Pets(R.drawable.puppy, "Firulais", "5 meses", "Color marrón claro. Juguetón.",
                "Perro", "Golden Retriever"));
        return list;

    }

}