package com.app.pawapp.TabsFragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.app.pawapp.Adapters.ListPetsAdapter;
import com.app.pawapp.Classes.PawPicture;
import com.app.pawapp.Classes.Pets;
import com.app.pawapp.DataAccess.DataAccessObject.DaoFactory;
import com.app.pawapp.DataAccess.DataAccessObject.PetDao;
import com.app.pawapp.DataAccess.DataAccessObject.RaceDao;
import com.app.pawapp.DataAccess.DataAccessObject.SpecieDao;
import com.app.pawapp.DataAccess.DataAccessObject.Ws;
import com.app.pawapp.DataAccess.DataTransferObject.OwnerDto;
import com.app.pawapp.DataAccess.DataTransferObject.PetDto;
import com.app.pawapp.DataAccess.Entity.Owner;
import com.app.pawapp.DataAccess.Entity.Pet;
import com.app.pawapp.DataAccess.Entity.Race;
import com.app.pawapp.DataAccess.Entity.Specie;
import com.app.pawapp.R;
import com.app.pawapp.Util.Util;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MyPetsFragment extends Fragment {

    private static final int UPDATE_PET_IMAGE_CODE = 0xFF2;

    private ListView listView;
    private ProgressBar progressBar;
    private ListPetsAdapter adapter;
    private SwipeRefreshLayout refresher;

    private OwnerDto loggedOwner;

    private PetDao petDao;
    private RaceDao raceDao;
    private SpecieDao specieDao;

    private List<PetDto> pets;

    private List<Specie> species;
    private List<Race> races;//WE SHOULD USE A DTO FOR THIS PURPOSE TOO

    private View popupLayout;
    private Bitmap selectedImg;
    private String selectedImgExt;

    //private boolean hasState = false;
    private CharSequence options[] = new CharSequence[]{"Editar Mascota", "Eliminar Mascota"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_mypets, container, false);
        listView = v.findViewById(R.id.MyPetsList);
        progressBar = v.findViewById(R.id.petsProgressBar);
        refresher = v.findViewById(R.id.refresher);

        if(!hasState()){
            petDao = DaoFactory.getPetDao(getContext());
            specieDao = DaoFactory.getSpecieDao(getContext());
            raceDao = DaoFactory.getRaceDao(getContext());
            loggedOwner = Util.getLoggedOwner(getContext());
            GetArrayItems();
        } else {
            listView.setAdapter(new ListPetsAdapter(getContext(), pets));
            progressBar.setVisibility(View.GONE);
        }

        refresher.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetArrayItems();
                refresher.setRefreshing(false);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, final View view, final int position, long l) {

                final PetDto selected = pets.get(position);

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setCancelable(true);
                builder.setTitle("Eliga una opción");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                final ProgressDialog pg = ProgressDialog.show(getContext(), "Cargando", "Espere porfavor");

                                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                                LayoutInflater inflater = getActivity().getLayoutInflater();

                                //LETS KEEP 2 REFERENCES OF THE SAME OBJECT FOR THE onActivityResult METHOD
                                popupLayout = inflater.inflate(R.layout.activity_edit_pet_dialog, null);
                                // = v;

                                if(selected.getPicture() != null && !selected.getPicture().isEmpty())
                                    Picasso.get()
                                            .load(selected.getPicture())
                                            .placeholder(R.drawable.progress_circle_anim)
                                            .fit()
                                            .centerInside()
                                            .into((ImageView)popupLayout.findViewById(R.id.imgEditPet));
                                else
                                    ((ImageView)popupLayout.findViewById(R.id.imgEditPet)).setImageResource(R.drawable.profile);

                                final TextInputEditText etPetName = popupLayout.findViewById(R.id.EditPetName);
                                final TextInputEditText etPetAge = popupLayout.findViewById(R.id.EditPetAge);
                                final TextInputEditText etDesc = popupLayout.findViewById(R.id.EditPetDes);
                                final TextInputEditText etRace = popupLayout.findViewById(R.id.etEditPetRace);
                                final MaterialBetterSpinner spnTypes = popupLayout.findViewById(R.id.spnEditTypes);
                                final MaterialBetterSpinner spnRaces = popupLayout.findViewById(R.id.spnEditRaces);

                                final Specie selectedSpecie = selected.getSpecie();
                                final Race selectedRace = selected.getRace();

                                popupLayout.findViewById(R.id.btnEditPetImage).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent i = new Intent(Intent.ACTION_PICK);
                                        i.setType("image/*");
                                        startActivityForResult(i, UPDATE_PET_IMAGE_CODE);
                                    }
                                });

                                etPetName.setText(selected.getName());
                                etPetAge.setText(selected.getAge());
                                etDesc.setText(selected.getDescription());
                                etRace.setText(selected.getOtherRace());
                                //IT WILL ONLY WORK THE FIRST TIME, WE ARE MODIFYING THE SAME LIST BY REFERENCE//, BUT WE ARE LOADING THE RACES AGAIN HMM
                                spnTypes.setText(selected.getSpecie().getName());
                                spnRaces.setText(selected.getRace().getName());

                                if(species != null) {
                                    //selectedSpecie.setId(species.get(0).getId());
                                    //selectedSpecie.setName(species.get(0).getName());
                                    fillSpecies(spnTypes);
                                    //spnTypes.setText(selectedSpecie.getName());

                                    fillRaces(selectedSpecie.getId(), spnRaces, new Handler.Callback() {
                                        @Override
                                        public boolean handleMessage(Message message) {
                                            //selectedRace.setId(races.get(0).getId());
                                            //selectedRace.setName(races.get(0).getName());
                                            pg.dismiss();
                                            return true;
                                        }
                                    });

                                } else {
                                    specieDao.findAll(new Ws.WsCallback<List<Specie>>() {
                                        @Override
                                        public void execute(List<Specie> response) {
                                            if(response != null) {
                                                species = response;
                                                //selectedSpecie.setId(species.get(0).getId());
                                                //selectedSpecie.setName(species.get(0).getName());
                                                fillSpecies(spnTypes);
                                                //spnTypes.setText(selectedSpecie.getName());

                                                fillRaces(selectedSpecie.getId(), spnRaces, new Handler.Callback() {
                                                    @Override
                                                    public boolean handleMessage(Message message) {
                                                        //selectedRace.setId(races.get(0).getId());
                                                        //selectedRace.setName(races.get(0).getName());
                                                        pg.dismiss();
                                                        return true;
                                                    }
                                                });
                                            }
                                        }
                                    });
                                }

                                spnTypes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                        spnRaces.setVisibility(View.VISIBLE);
                                        etRace.setVisibility(View.GONE);
                                        selectedSpecie.setId(species.get(i).getId());
                                        selectedSpecie.setName(species.get(i).getName());

                                        fillRaces(selectedSpecie.getId(), spnRaces, new Handler.Callback() {
                                            @Override
                                            public boolean handleMessage(Message message) {
                                                if(races != null && races.size() > 0) {
                                                    selectedRace.setId(races.get(0).getId());
                                                    selectedRace.setName(races.get(0).getName());
                                                    spnRaces.setText(selectedRace.getName());
                                                } else {
                                                    spnRaces.setVisibility(View.GONE);
                                                    etRace.setVisibility(View.VISIBLE);
                                                }
                                                return true;
                                            }
                                        });
                                    }
                                });

                                spnRaces.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                        selectedRace.setId(races.get(i).getId());
                                        selectedRace.setName(races.get(i).getName());
                                        //FOR SOME REASON IT DELETES ALL THE ITEMS
                                        //spnRaces.setText(selectedRace.getName());
                                    }
                                });

                                builder.setView(popupLayout)
                                        .setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int id) {
                                                Pet p = new Pet(
                                                        selected.getId(),
                                                        etPetName.getText().toString(),
                                                        etPetAge.getText().toString(),
                                                        etDesc.getText().toString(),
                                                        selected.getPicture(),
                                                        selected.getPublishDate(),
                                                        selected.isState(),
                                                        etRace.getText().toString(),
                                                        selectedSpecie.getId(),
                                                        selectedRace.getId(),
                                                        loggedOwner.getId(),
                                                        selectedImg != null ? Util.toBase64(Util.bitmapToBytes(selectedImg, selectedImgExt)) : null,
                                                        selectedImgExt
                                                );
                                                petDao.update(p, new Ws.WsCallback<Boolean>() {
                                                    @Override
                                                    public void execute(Boolean response) {
                                                        if(response) {
                                                            Toast.makeText(getActivity(), "Editado", Toast.LENGTH_SHORT).show();
                                                            GetArrayItems();
                                                        }
                                                    }
                                                });
                                            }
                                        })
                                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialogInterface, int id) {
                                                dialog.dismiss();
                                            }
                                        });
                                builder.show();

                                break;
                            case 1:
                                final AlertDialog alert = new AlertDialog.Builder(getContext())
                                        .setTitle("Confirmación")
                                        .setMessage("¿Esta seguro que desea eliminar esta publicacion?")
                                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                petDao.delete(selected.getId(), new Ws.WsCallback<Boolean>() {
                                                    @Override
                                                    public void execute(Boolean response) {
                                                        if (response) {
                                                            Toast.makeText(getContext(), "Publicacion eliminada", Toast.LENGTH_SHORT).show();
                                                            GetArrayItems();
                                                        }
                                                        dialog.dismiss();
                                                    }
                                                });
                                            }
                                        })
                                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                dialog.dismiss();
                                            }
                                        })
                                        .show();
                                break;
                        }
                    }
                });
                builder.show();
            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == UPDATE_PET_IMAGE_CODE && resultCode == Activity.RESULT_OK){
            try {
                PawPicture pic = Util.getPictureFromIntent(data, getContext());
                if(pic != null) {
                    selectedImg = pic.getImage();
                    selectedImgExt = pic.getType();
                    Picasso.get()
                            .load(pic.getUri())
                            .placeholder(R.drawable.progress_circle_anim)
                            .fit()
                            .centerInside()
                            .into((ImageView)popupLayout.findViewById(R.id.imgEditPet));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void GetArrayItems() {
        //final ProgressDialog pg = ProgressDialog.show(getContext(), "Cargando", "Espere");
        progressBar.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
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
                listView.setVisibility(View.VISIBLE);
            }
        });
    }

    //SAVE STATE ON DESTROY, USUALLY BUT WE USE INSTANCE VARIABLES
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

    private void fillSpecies(MaterialBetterSpinner spn){
        final String[] ss = new String[species.size()];

        for(int i = 0, len = species.size(); i < len; i++)
            ss[i] = species.get(i).getName();

        ArrayAdapter<String> arrayAdapterType = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_dropdown_item, ss);
        arrayAdapterType.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spn.setAdapter(arrayAdapterType);
    }

    private void fillRaces(int specieId, final MaterialBetterSpinner spn, final Handler.Callback callback){
        raceDao.findAll(specieId, new Ws.WsCallback<List<Race>>() {
            @Override
            public void execute(List<Race> response) {
                races = response;

                final String[] rc = new String[races.size()];

                for(int i = 0, len = races.size(); i < len; i++)
                    rc[i] = races.get(i).getName();

                ArrayAdapter<String> arrayAdapterType = new ArrayAdapter<String>(getContext(),
                        android.R.layout.simple_spinner_dropdown_item, rc);
                arrayAdapterType.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                spn.setAdapter(arrayAdapterType);

                callback.handleMessage(null);
            }
        });
    }

}