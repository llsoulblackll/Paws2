package com.app.pawapp.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.app.pawapp.DataAccess.DataAccessObject.DaoFactory;
import com.app.pawapp.DataAccess.DataAccessObject.PetDao;
import com.app.pawapp.DataAccess.DataAccessObject.RaceDao;
import com.app.pawapp.DataAccess.DataAccessObject.SpecieDao;
import com.app.pawapp.DataAccess.DataAccessObject.Ws;
import com.app.pawapp.DataAccess.Entity.Owner;
import com.app.pawapp.DataAccess.Entity.Pet;
import com.app.pawapp.DataAccess.Entity.Race;
import com.app.pawapp.DataAccess.Entity.Specie;
import com.app.pawapp.Login.LoginActivity;
import com.app.pawapp.R;
import com.app.pawapp.Util.Util;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.io.IOException;
import java.util.List;

public class PetFragment extends Fragment {

    private static final int PICK_IMAGE_CODE = 0x2231;

    private TextInputEditText nameTextInputEditText;
    private TextInputEditText ageTextInputEditText;
    private TextInputEditText descTextInputEditText;
    private ImageView petImageView;
    private Button petImageButton;
    private MaterialBetterSpinner mbSpinnerType;
    private MaterialBetterSpinner mbSpinnerRace;

    private PetDao petDao;
    private SpecieDao specieDao;
    private RaceDao raceDao;

    private List<Specie> species;
    private List<Race> races;

    private Specie selectedSpecie;
    private Race selectedRace;

    private Bitmap selectedImg;
    private String selectedImgExtension;

    public PetFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        petDao = DaoFactory.getPetDao(getContext());
        specieDao = DaoFactory.getSpecieDao(getContext());
        raceDao = DaoFactory.getRaceDao(getContext());

        View v = inflater.inflate(R.layout.fragment_pet, container, false);

        nameTextInputEditText = v.findViewById(R.id.PetName);
        ageTextInputEditText = v.findViewById(R.id.PetAge);
        descTextInputEditText = v.findViewById(R.id.PetDes);
        petImageView = v.findViewById(R.id.imgPet);
        petImageButton = v.findViewById(R.id.btnPetImage);

        //String[] types = {"Perro","Gato"};
        //String[] races = {"Pastor Alemán","Siamés"};

        mbSpinnerType = v.findViewById(R.id.spnTypes);
        mbSpinnerRace = v.findViewById(R.id.spnRaces);

        specieDao.findAll(new Ws.WsCallback<List<Specie>>() {
            @Override
            public void execute(List<Specie> response) {
                species = response;
                String[] ss = new String[response.size()];

                for (int i = 0; i < response.size(); i++){
                    ss[i] = species.get(i).getName();
                }

                if(ss.length > 0) {
                    ArrayAdapter<String> arrayAdapterType = new ArrayAdapter<>(PetFragment.this.getActivity(),
                            android.R.layout.simple_spinner_dropdown_item, ss);
                    arrayAdapterType.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                    mbSpinnerType.setAdapter(arrayAdapterType);

                    mbSpinnerType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            selectedSpecie = species.get(i);
                            fillRaces(species.get(i).getId());
                        }
                    });

                    //select the first one by default
                    selectedSpecie = species.get(0);
                    mbSpinnerType.setText(selectedSpecie.getName());
                    fillRaces(species.get(0).getId());
                }

            }
        });

        petImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.setType("image/*");
                startActivityForResult(i, PICK_IMAGE_CODE);
            }
        });

        v.findViewById(R.id.btnPetInsert).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Pet pet = new Pet();

                pet.setName(nameTextInputEditText.getText().toString());
                pet.setAge(ageTextInputEditText.getText().toString());
                pet.setDescription(descTextInputEditText.getText().toString());
                pet.setSpecieId(selectedSpecie != null ? selectedSpecie.getId() : 0);
                pet.setRaceId(selectedRace != null ? selectedRace.getId() : 0);
                pet.setOwnerId(new Gson().fromJson(Util.SharedPreferencesHelper.getValue(Util.LOGGED_OWNER_KEY, getContext()).toString(), Owner.class).getId());

                pet.setImageBase64(Util.toBase64(Util.bitmapToBytes(selectedImg, selectedImgExtension)));
                pet.setImageExtension(selectedImgExtension);

                petDao.insert(pet, new Ws.WsCallback<Object>() {
                    @Override
                    public void execute(Object response) {
                        Toast.makeText(getContext(), "Mascota registrada satisfactoriamente", Toast.LENGTH_SHORT).show();
                        clear();
                    }
                });
            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == PICK_IMAGE_CODE && resultCode == Activity.RESULT_OK){
            try {
                if(data.getData() != null) {
                    Bitmap img = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), data.getData());
                    String ext = getContext().getContentResolver().getType(data.getData());
                    selectedImg = img;
                    selectedImgExtension = ext.substring(ext.indexOf("/") + 1);
                    Picasso.get().load(data.getData()).into(petImageView);
                }
            } catch (IOException | NullPointerException e) {
                Util.showAlert("Hubo un error al seleccionar la imagen", getContext());
            }
        }
    }

    private void fillRaces(int specieId){
        raceDao.findAll(specieId, new Ws.WsCallback<List<Race>>() {
            @Override
            public void execute(List<Race> response) {
                races = response;

                String[] rr = new String[races.size()];

                for(int i = 0;  i < races.size(); i++){
                    rr[i] = races.get(i).getName();
                }

                if(rr.length > 0) {
                    ArrayAdapter<String> arrayAdapterRaces = new ArrayAdapter<>(PetFragment.this.getActivity(),
                            android.R.layout.simple_spinner_dropdown_item, rr);
                    mbSpinnerRace.setAdapter(arrayAdapterRaces);
                    arrayAdapterRaces.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

                    mbSpinnerRace.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            selectedRace = races.get(i);
                        }
                    });

                    selectedRace = races.get(0);
                    mbSpinnerRace.setText(selectedRace.getName());

                    if(!mbSpinnerRace.isEnabled()) {
                        mbSpinnerRace.setClickable(true);
                        mbSpinnerRace.setEnabled(true);
                    }

                } else {
                    mbSpinnerRace.setClickable(false);
                    mbSpinnerRace.setEnabled(false);
                    mbSpinnerRace.setText("");
                    selectedRace = null;
                }
            }
        });
    }

    private void clear(){
        nameTextInputEditText.setText("");
        ageTextInputEditText.setText("");
        descTextInputEditText.setText("");
        petImageView.setImageResource(R.drawable.dog);
        selectedSpecie = species.get(0);
        selectedRace = races.size() > 0 ? races.get(0) : null;
        selectedImg = null;
        selectedImgExtension = "";
    }

}