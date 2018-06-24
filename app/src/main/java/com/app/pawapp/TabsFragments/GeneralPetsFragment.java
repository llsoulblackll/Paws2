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

public class GeneralPetsFragment extends Fragment {

    private ListView listView;
    private ListPetsAdapter adapter;

    private View layout;

    private PetDao petDao;
    private OwnerDto loggedOwner;

    CharSequence options[] = new CharSequence[] {"Adoptar Mascota", "Información de Contacto"};

    public GeneralPetsFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        petDao = DaoFactory.getPetDao(getContext());
        loggedOwner = Util.getLoggedOwner(getContext());

        layout = inflater.inflate(R.layout.fragment_generalpets, container, false);
        listView = layout.findViewById(R.id.GeneralList);

        GetArrayItems(listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, final View view, int i, long l) {
                /*Toast.makeText(getActivity(), "Funciona!!!", Toast.LENGTH_SHORT).show();*/
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setCancelable(true);
                builder.setTitle("Selecciona una opción");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            /*Envía aviso al otro usuario*/
                            Toast.makeText(getActivity(), "Solicitud de Adopción Enviada", Toast.LENGTH_SHORT).show();
                        } else if (which == 1) {
                            /*Muestra información del usuario de la mascota*/
                            /*Toast.makeText(getActivity(), "Información", Toast.LENGTH_SHORT).show();*/
                            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                            LayoutInflater inflater = getActivity().getLayoutInflater();
                            builder.setView(inflater.inflate(R.layout.activity_dialog, null));
                            builder.show();
                        }
                    }
                });
                builder.show();
            }
        });

        return layout;
    }

    private void GetArrayItems(ListView toPopulate) {
        petDao.findAll(new Ws.WsCallback<List<Pet>>() {
            @Override
            public void execute(List<Pet> response) {
                if(response != null)
                    listView.setAdapter(new ListPetsAdapter(getContext(), response));
            }
        });
    }

}