package com.app.pawapp.TabsFragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.app.pawapp.Adapters.ListPetsAdapter;
import com.app.pawapp.Classes.Pets;
import com.app.pawapp.DataAccess.DataAccessObject.DaoFactory;
import com.app.pawapp.DataAccess.DataAccessObject.PetDao;
import com.app.pawapp.DataAccess.DataAccessObject.Ws;
import com.app.pawapp.DataAccess.DataTransferObject.OwnerDto;
import com.app.pawapp.DataAccess.Entity.Owner;
import com.app.pawapp.DataAccess.Entity.Pet;
import com.app.pawapp.R;
import com.app.pawapp.Util.Util;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class    MyPetsFragment extends Fragment {

    private ListView listView;
    private ListPetsAdapter adapter;

    private PetDao petDao;
    private OwnerDto loggedOwner;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        petDao = DaoFactory.getPetDao(getContext());
        loggedOwner = Util.getLoggedOwner(getContext());

        View v = inflater.inflate(R.layout.fragment_mypets, container, false);

        listView = v.findViewById(R.id.MyPetsList);

        GetArrayItems(listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage(R.string.deletemsg)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Toast.makeText(getActivity(), "Mascota Eliminada Satisfactoriamente", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
                builder.show();
            }
        });

        return v;
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