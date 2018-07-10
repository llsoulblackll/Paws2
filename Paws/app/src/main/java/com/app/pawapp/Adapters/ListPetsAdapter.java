package com.app.pawapp.Adapters;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.pawapp.Classes.Pets;
import com.app.pawapp.DataAccess.Entity.Pet;
import com.app.pawapp.R;
import com.app.pawapp.TabsFragments.MyPetsFragment;

import java.util.ArrayList;

public class ListPetsAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Pets> listpets;

    public ListPetsAdapter(Context context, ArrayList<Pets> listpets) {
        this.context = context;
        this.listpets = listpets;
    }

    @Override
    public int getCount() {
        return listpets.size();
    }

    @Override
    public Object getItem(int position) {
        return listpets.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        Pets item = (Pets) getItem(position);

        view = LayoutInflater.from(context).inflate(R.layout.item_pets, null);
        ImageView img = view.findViewById(R.id.PetImg);
        TextView name = view.findViewById(R.id.PetName);
        TextView age = view.findViewById(R.id.PetAge);
        TextView des = view.findViewById(R.id.PetDes);
        TextView type = view.findViewById(R.id.PetType);
        TextView race = view.findViewById(R.id.PetRace);

        img.setImageResource(item.getImg());
        name.setText(item.getName());
        age.setText(item.getAge());
        des.setText(item.getDescription());
        type.setText(item.getType());
        race.setText(item.getRace());

        return view;
    }
}