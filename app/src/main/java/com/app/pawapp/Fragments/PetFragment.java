package com.app.pawapp.Fragments;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.app.pawapp.DataAccess.DataAccessObject.DaoFactory;
import com.app.pawapp.DataAccess.DataAccessObject.PetDao;
import com.app.pawapp.DataAccess.DataAccessObject.RaceDao;
import com.app.pawapp.DataAccess.DataAccessObject.SpecieDao;
import com.app.pawapp.DataAccess.DataAccessObject.Ws;
import com.app.pawapp.DataAccess.Entity.Pet;
import com.app.pawapp.DataAccess.Entity.Race;
import com.app.pawapp.DataAccess.Entity.Specie;
import com.app.pawapp.Login.LoginActivity;
import com.app.pawapp.R;
import com.app.pawapp.Util.Util;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.List;

public class PetFragment extends Fragment {

    private TextInputEditText nameTextInputEditText;
    private TextInputEditText ageTextInputEditText;
    private TextInputEditText descTextInputEditText;
    private ImageView dogImageView;
    private MaterialBetterSpinner mbSpinnerType;
    private MaterialBetterSpinner mbSpinnerRace;

    private PetDao petDao;
    private SpecieDao specieDao;
    private RaceDao raceDao;

    private List<Specie> species;
    private List<Race> races;

    private Specie selectedSpecie;
    private Race selectedRace;

    public PetFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        petDao = DaoFactory.getPetDao(getContext());
        specieDao = DaoFactory.getSpecieDao(getContext());
        raceDao = DaoFactory.getRaceDao(getContext());

        View v = inflater.inflate(R.layout.fragment_pet, container, false);

        nameTextInputEditText = v.findViewById(R.id.PetName);
        ageTextInputEditText = v.findViewById(R.id.PetAge);
        descTextInputEditText = v.findViewById(R.id.PetDes);

        //String[] types = {"Perro","Gato"};
        //String[] races = {"Pastor Alemán","Siamés"};

        mbSpinnerType = v.findViewById(R.id.spnTypes);
        mbSpinnerRace = v.findViewById(R.id.spnRaces);

        specieDao.findAll(new Ws.WsCallback<List<Specie>>() {
            @Override
            public void execute(List<Specie> response) {
                species = response;
                selectedSpecie = species.get(0);
                String[] ss = new String[response.size()];

                for (int i = 0; i < response.size(); i++){
                    ss[i] = species.get(i).getName();
                }

                ArrayAdapter<String> arrayAdapterType= new ArrayAdapter<>(PetFragment.this.getActivity(),
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

                fillRaces(species.get(0).getId());

                mbSpinnerRace.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        selectedRace = races.get(i);
                    }
                });

            }
        });

        v.findViewById(R.id.btnPetInsert).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Pet pet = new Pet();

                pet.setName(nameTextInputEditText.getText().toString());
                pet.setAge(ageTextInputEditText.getText().toString());
                pet.setDescription(descTextInputEditText.getText().toString());
                pet.setSpecieId(selectedSpecie.getId());
                pet.setRaceId(selectedRace.getId());
                pet.setOwnerId((int)Util.SharedPreferencesHelper.getValue(LoginActivity.USER_KEY, getContext()));

                petDao.insert(pet, new Ws.WsCallback<Object>() {
                    @Override
                    public void execute(Object response) {
                        Toast.makeText(getContext(), "Mascota registrada satisfactoriamente", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        return v;
    }

    private void fillRaces(int specieId){
        raceDao.findAll(specieId, new Ws.WsCallback<List<Race>>() {
            @Override
            public void execute(List<Race> response) {
                races = response;
                selectedRace = races.get(0);

                String[] rr = new String[races.size()];

                for(int i = 0;  i < races.size(); i++){
                    rr[i] = races.get(i).getName();
                }

                ArrayAdapter<String> arrayAdapterRaces = new ArrayAdapter<>(PetFragment.this.getActivity(),
                        android.R.layout.simple_spinner_dropdown_item, rr);
                mbSpinnerRace.setAdapter(arrayAdapterRaces);
                arrayAdapterRaces.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
            }
        });
    }

}