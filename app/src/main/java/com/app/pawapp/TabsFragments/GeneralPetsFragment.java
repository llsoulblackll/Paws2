package com.app.pawapp.TabsFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.app.pawapp.Adapters.ListPetsAdapter;
import com.app.pawapp.Classes.Pets;
import com.app.pawapp.DataAccess.DataAccessObject.DaoFactory;
import com.app.pawapp.DataAccess.DataAccessObject.PetDao;
import com.app.pawapp.DataAccess.DataAccessObject.Ws;
import com.app.pawapp.DataAccess.Entity.Owner;
import com.app.pawapp.DataAccess.Entity.Pet;
import com.app.pawapp.R;
import com.app.pawapp.Util.Util;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class GeneralPetsFragment extends Fragment {

    private ListView listView;
    private ListPetsAdapter adapter;

    private View layout;

    private PetDao petDao;
    private Owner loggedOwner;

    public GeneralPetsFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        petDao = DaoFactory.getPetDao(getContext());
        loggedOwner = new Gson().fromJson(Util.SharedPreferencesHelper.getValue(Util.LOGGED_OWNER_KEY, getContext()).toString(), Owner.class);

        layout = inflater.inflate(R.layout.fragment_generalpets, container, false);
        listView = layout.findViewById(R.id.GeneralList);

        GetArrayItems(listView);

        return layout;
    }

    private void GetArrayItems(ListView toPopulate) {
        petDao.findAll(loggedOwner.getId(), new Ws.WsCallback<List<Pet>>() {
            @Override
            public void execute(List<Pet> response) {
                if(response != null)
                    listView.setAdapter(new ListPetsAdapter(getContext(), response));
            }
        });
    }

}