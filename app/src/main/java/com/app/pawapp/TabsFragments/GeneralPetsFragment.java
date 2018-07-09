package com.app.pawapp.TabsFragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.pawapp.Adapters.ListPetsAdapter;
import com.app.pawapp.DataAccess.DataAccessObject.DaoFactory;
import com.app.pawapp.DataAccess.DataAccessObject.PetAdopterDao;
import com.app.pawapp.DataAccess.DataAccessObject.PetDao;
import com.app.pawapp.DataAccess.DataAccessObject.Ws;
import com.app.pawapp.DataAccess.DataTransferObject.OwnerDto;
import com.app.pawapp.DataAccess.DataTransferObject.PetDto;
import com.app.pawapp.DataAccess.Entity.Owner;
import com.app.pawapp.DataAccess.Entity.Pet;
import com.app.pawapp.DataAccess.Entity.PetAdopter;
import com.app.pawapp.R;
import com.app.pawapp.Util.Util;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.List;

public class GeneralPetsFragment extends Fragment {

    private ListView listView;
    private ProgressBar progressBar;
    private ListPetsAdapter adapter;

    private View layout;

    private PetDao petDao;
    private OwnerDto loggedOwner;
    private PetAdopterDao petAdopterDao;

    private List<PetDto> pets;

    private CharSequence options[] = new CharSequence[] {"Adoptar Mascota", "Información de Contacto"};

    public GeneralPetsFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        layout = inflater.inflate(R.layout.fragment_generalpets, container, false);
        listView = layout.findViewById(R.id.GeneralList);
        progressBar = layout.findViewById(R.id.petsProgressBar);

        //IS ALWAYS NULL FOR SOME REASON
        if(savedInstanceState == null) {
        }

        if(!hasState()){
            petDao = DaoFactory.getPetDao(getContext());
            petAdopterDao = DaoFactory.getPetAdopterDao(getContext());
            loggedOwner = Util.getLoggedOwner(getContext());
            GetArrayItems(listView);
        } else {
            listView.setAdapter(new ListPetsAdapter(getContext(), pets));
            progressBar.setVisibility(View.GONE);
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, final View view, int i, long l) {
                final PetDto selectedPet = pets.get(i);
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
                                final ProgressDialog pg = ProgressDialog.show(getContext(), "Eviando", "Espere porfavor");
                                petAdopterDao.insert(new PetAdopter(
                                        selectedPet.getId(),
                                        loggedOwner.getId(),
                                        new Date(),
                                        //THIS DATE IS JUST A PLACEHOLDER TO PREVENT WCF FROM CRASHING SINCE ITS STRUCT S DO NOT ALLOW NULLS
                                        //AND OUR GSON FACTORY SERIALIZES NULLS, CONFIGURED BY US
                                        new Date(),
                                        true
                                ), new Ws.WsCallback<Object>() {
                                    @Override
                                    public void execute(Object response) {
                                        if(getContext() != null) {
                                            if (response != null && (Boolean)response) {
                                                Toast.makeText(getActivity(), "Solicitud de Adopción Enviada Satisfactoriamente", Toast.LENGTH_SHORT).show();
                                            } else
                                                Util.showAlert("Ya solicitaste esta mascota", getContext());
                                        }
                                        pg.dismiss();
                                    }
                                });
                                break;
                            case 1:
                                /*Muestra información del usuario de la mascota*/
                                /*Toast.makeText(getActivity(), "Información", Toast.LENGTH_SHORT).show();*/
                                if(getActivity() != null) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                                    LayoutInflater inflater = getActivity().getLayoutInflater();
                                    View view = inflater.inflate(R.layout.activity_contact_dialog, null);

                                    if(selectedPet.getOwner().getProfilePicture() != null && !selectedPet.getOwner().getProfilePicture().isEmpty())
                                        Picasso.get()
                                                .load(selectedPet.getOwner().getProfilePicture())
                                                .placeholder(R.drawable.progress_circle_anim)
                                                .into((ImageView)view.findViewById(R.id.imgProfile));
                                    else
                                        Picasso.get()
                                                .load(R.drawable.profile)
                                                .placeholder(R.drawable.progress_circle_anim)
                                                .into((ImageView)view.findViewById(R.id.imgProfile));

                                    ((TextView)view.findViewById(R.id.txtName)).setText(selectedPet.getOwner().getName());
                                    ((TextView)view.findViewById(R.id.txtLastName)).setText(selectedPet.getOwner().getLastName());
                                    ((TextView)view.findViewById(R.id.txtEmail)).setText(selectedPet.getOwner().geteMail());
                                    ((TextView)view.findViewById(R.id.txtPhoneNumber)).setText(selectedPet.getOwner().getPhoneNumber());

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
        petDao.findAllDto(loggedOwner.getId(), false, new Ws.WsCallback<List<PetDto>>() {
            @Override
            public void execute(List<PetDto> response) {
                if(getContext() != null) {
                    if (response != null) {
                        pets = response;
                        listView.setAdapter(new ListPetsAdapter(getContext(), response));
                    }
                }
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private boolean hasState(){
        return petDao != null && petAdopterDao != null && loggedOwner != null && pets != null;
    }

}