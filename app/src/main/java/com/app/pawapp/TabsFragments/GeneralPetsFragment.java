package com.app.pawapp.TabsFragments;

import android.annotation.SuppressLint;
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
import com.app.pawapp.DataAccess.DataAccessObject.DaoFactory;
import com.app.pawapp.DataAccess.DataAccessObject.PetDao;
import com.app.pawapp.DataAccess.DataAccessObject.Ws;
import com.app.pawapp.DataAccess.DataTransferObject.OwnerDto;
import com.app.pawapp.DataAccess.DataTransferObject.PetDto;
import com.app.pawapp.DataAccess.Entity.Owner;
import com.app.pawapp.DataAccess.Entity.Pet;
import com.app.pawapp.R;
import com.app.pawapp.Util.Util;

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
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setCancelable(true);
                builder.setTitle("Eliga una opción");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @SuppressLint("InflateParams")
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                /*Envía aviso al otro usuario*/
                                Toast.makeText(getActivity(), "Solicitud de Adopción Enviada Satisfactoriamente", Toast.LENGTH_SHORT).show();
                                break;
                            case 1:
                                /*Muestra información del usuario de la mascota*/
                                /*Toast.makeText(getActivity(), "Información", Toast.LENGTH_SHORT).show();*/
                                if(getActivity() != null) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                                    LayoutInflater inflater = getActivity().getLayoutInflater();
                                    View view = inflater.inflate(R.layout.activity_contact_dialog, null);
                                    builder.setView(view);
                                    builder.show();
                                }
                                break;
                        }
                    }
                });
                builder.show();
            }
        });

        return layout;
    }

    private void GetArrayItems(ListView toPopulate) {
        petDao.findAllDto(new Ws.WsCallback<List<PetDto>>() {
            @Override
            public void execute(List<PetDto> response) {
                if(response != null)
                    listView.setAdapter(new ListPetsAdapter(getContext(), response));
            }
        });
    }

}