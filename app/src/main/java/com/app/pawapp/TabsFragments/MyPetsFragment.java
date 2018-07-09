package com.app.pawapp.TabsFragments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.app.pawapp.Adapters.ListPetsAdapter;
import com.app.pawapp.Classes.Pets;
import com.app.pawapp.DataAccess.DataAccessObject.DaoFactory;
import com.app.pawapp.DataAccess.DataAccessObject.PetDao;
import com.app.pawapp.DataAccess.DataAccessObject.Ws;
import com.app.pawapp.DataAccess.DataTransferObject.OwnerDto;
import com.app.pawapp.DataAccess.DataTransferObject.PetDto;
import com.app.pawapp.DataAccess.Entity.Owner;
import com.app.pawapp.DataAccess.Entity.Pet;
import com.app.pawapp.R;
import com.app.pawapp.Util.Util;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MyPetsFragment extends Fragment {

    private ListView listView;
    private ProgressBar progressBar;
    private ListPetsAdapter adapter;

    private PetDao petDao;
    private OwnerDto loggedOwner;

    private List<PetDto> pets;

    //private boolean hasState = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_mypets, container, false);
        listView = v.findViewById(R.id.MyPetsList);
        progressBar = v.findViewById(R.id.petsProgressBar);

        if(!hasState()){
            petDao = DaoFactory.getPetDao(getContext());
            loggedOwner = Util.getLoggedOwner(getContext());
            GetArrayItems(listView);
        } else {
            listView.setAdapter(new ListPetsAdapter(getContext(), pets));
            progressBar.setVisibility(View.GONE);
        }

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
        //final ProgressDialog pg = ProgressDialog.show(getContext(), "Cargando", "Espere");
        petDao.findAllDto(loggedOwner.getId(), true, new Ws.WsCallback<List<PetDto>>() {
            @Override
            public void execute(List<PetDto> response) {
                if(getContext() != null) {
                    if (response != null) {
                        pets = response;
                        listView.setAdapter(new ListPetsAdapter(getContext(), response));
                    }
                    //pg.dismiss();
                }
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * Checks whether the fragment has a state (all instance variables are still set) or not
     */
    private boolean hasState(){
        return petDao != null && loggedOwner != null && pets != null;

    }
}