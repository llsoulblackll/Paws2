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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ListPetsAdapter extends BaseAdapter {

    private Context context;
    private List<Pet> listpets;

    public ListPetsAdapter(Context context, List<Pet> listpets) {
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

        Pet item = (Pet) getItem(position);

        view = LayoutInflater.from(context).inflate(R.layout.item_pets, null);
        ImageView img = view.findViewById(R.id.PetImg);
        TextView name = view.findViewById(R.id.PetName);
        TextView age = view.findViewById(R.id.PetAge);
        TextView des = view.findViewById(R.id.PetDes);
        TextView type = view.findViewById(R.id.PetType);
        TextView race = view.findViewById(R.id.PetRace);

        if(item.getPicture() != null && !item.getPicture().isEmpty())
            Picasso.get().load(item.getPicture()).into(img);
        else
            img.setImageResource(R.drawable.puppy);
        name.setText(item.getName());
        age.setText(item.getAge());
        des.setText(item.getDescription());
        type.setText(""+item.getSpecieId());
        race.setText(""+item.getRaceId());

        return view;
    }
}