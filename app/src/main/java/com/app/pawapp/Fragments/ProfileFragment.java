package com.app.pawapp.Fragments;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.app.pawapp.Classes.PawPicture;
import com.app.pawapp.DataAccess.DataAccessObject.DaoFactory;
import com.app.pawapp.DataAccess.DataAccessObject.DistrictDao;
import com.app.pawapp.DataAccess.DataAccessObject.OwnerDao;
import com.app.pawapp.DataAccess.DataAccessObject.Ws;
import com.app.pawapp.DataAccess.DataTransferObject.OwnerDto;
import com.app.pawapp.DataAccess.Entity.District;
import com.app.pawapp.DataAccess.Entity.Owner;
import com.app.pawapp.InboxMessages.AnswerActivity;
import com.app.pawapp.InboxMessages.InboxActivity;
import com.app.pawapp.Login.LoginActivity;
import com.app.pawapp.MainActivity;
import com.app.pawapp.R;
import com.app.pawapp.Util.Util;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ProfileFragment extends Fragment {

    private static final int PROFILE_PIC_REQUEST_CODE = 0xFA;

    private TextView txtName, txtLastName, txtDni, txtBirth, txtEmail, txtPhone, txtAddress, txtDistrict, txtRegisteredPets, txtAdoptedPets;
    private EditText etName, etLastName, etDni, etBirth, etEmail, etPhone, etAddress;
    private Spinner spnDistrict;
    private FloatingActionButton fabEdit, fabSave, fabInbox, fabPet, fabPhoto;
    private Button btnLogout;
    private ImageView imgProfilePic;

    private OwnerDao ownerDao;
    private DistrictDao distritcDao;

    private OwnerDto loggedOwner;

    private List<District> districts;
    private District selectedDistrict;

    private PawPicture selectedPicture;

    public ProfileFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        ownerDao = DaoFactory.getOwnerDao(getContext());
        distritcDao = DaoFactory.getDistrictDao(getContext());

        txtName = v.findViewById(R.id.txtName);
        txtLastName = v.findViewById(R.id.txtLastName);
        txtDni = v.findViewById(R.id.txtDni);
        txtBirth = v.findViewById(R.id.txtBirth);
        txtEmail = v.findViewById(R.id.txtEmail);
        txtPhone = v.findViewById(R.id.txtPhone);
        txtAddress = v.findViewById(R.id.txtAddress);
        txtDistrict = v.findViewById(R.id.txtDistrict);
        txtRegisteredPets = v.findViewById(R.id.txtRegisteredPets);
        txtAdoptedPets = v.findViewById(R.id.txtAdoptedPets);

        etName = v.findViewById(R.id.etName);
        etLastName = v.findViewById(R.id.etLastName);
        etDni = v.findViewById(R.id.etDni);
        etBirth = v.findViewById(R.id.etBirth);
        etEmail = v.findViewById(R.id.etEmail);
        etPhone = v.findViewById(R.id.etPhone);
        etAddress = v.findViewById(R.id.etAddress);

        spnDistrict = v.findViewById(R.id.spnDistrict);

        fabEdit = v.findViewById(R.id.fabEdit);
        fabSave = v.findViewById(R.id.fabSave);
        fabInbox = v.findViewById(R.id.fabInbox);
        fabPet = v.findViewById(R.id.fabPet);
        fabPhoto = v.findViewById(R.id.fabPhoto);

        btnLogout = v.findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(logoutAction);

        imgProfilePic = v.findViewById(R.id.imgProfilePic);

        loggedOwner = Util.getLoggedOwner(getContext());

        txtRegisteredPets.setText(String.valueOf(loggedOwner.getRegisteredAmount()));
        txtAdoptedPets.setText(String.valueOf(loggedOwner.getAdoptedAmount()));

        Picasso.get()
                .load(loggedOwner.getProfilePicture())
                .placeholder(R.drawable.progress_circle_anim)
                .into(imgProfilePic);

        //INSTANCE VARIABLES ARE ALWAYS SAVED SINCE OUR FRAGMENTS ARE NEVER DETACHED FROM THE ACTIVITY
        if(districts == null) {
            distritcDao.findAll(new Ws.WsCallback<List<District>>() {
                @Override
                public void execute(List<District> response) {
                    if (getContext() != null) {
                        districts = response;

                        String[] diss = new String[response.size()];

                        for (int i = 0, len = response.size(); i < len; i++)
                            diss[i] = response.get(i).getName();

                        spnDistrict.setAdapter(new ArrayAdapter<>(
                                getContext(),
                                R.layout.support_simple_spinner_dropdown_item,
                                diss));

                        selectedDistrict = loggedOwner.getDistrict();

                        switchViews(false);

                        spnDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                selectedDistrict = districts.get(i);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {
                            }
                        });
                    }
                }
            });
        } else
            switchViews(false);

        v.findViewById(R.id.fabEdit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchViews(true);
            }
        });

        v.findViewById(R.id.fabSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loggedOwner.setName(etName.getText().toString());
                loggedOwner.setLastName(etLastName.getText().toString());
                try {
                    loggedOwner.setBirthDate(new SimpleDateFormat("dd/MM/yyyy").parse(etBirth.getText().toString()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                loggedOwner.setDNI(etDni.getText().toString());
                loggedOwner.seteMail(etEmail.getText().toString());
                loggedOwner.setAddress(etAddress.getText().toString());
                loggedOwner.setPhoneNumber(etPhone.getText().toString());
                loggedOwner.setDistrict(selectedDistrict);

                Owner o = new Owner(
                        loggedOwner.getId(),
                        loggedOwner.getUsername(),
                        loggedOwner.getPassword(),
                        loggedOwner.getName(),
                        loggedOwner.getLastName(),
                        loggedOwner.getBirthDate(),
                        loggedOwner.getDNI(),
                        loggedOwner.geteMail(),
                        loggedOwner.getAddress(),
                        loggedOwner.getPhoneNumber(),
                        loggedOwner.getProfilePicture(),
                        loggedOwner.getDistrict().getId()
                );

                ownerDao.update(o, new Ws.WsCallback<Boolean>() {
                    @Override
                    public void execute(Boolean response) {
                        if (response)
                            Util.setLoggedOwner(loggedOwner, getContext());
                        switchViews(false);
                    }
                });

            }
        });

        fabInbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), InboxActivity.class);
                startActivity(i);
            }
        });

        fabPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), AnswerActivity.class);
                startActivity(i);
            }
        });

        fabPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.setType("image/*");
                startActivityForResult(i, PROFILE_PIC_REQUEST_CODE);
            }
        });

        etBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getContext() != null) {
                    final Calendar calendar = Calendar.getInstance();
                    DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                            String dateString = String.format(Locale.getDefault(), "%d/%s/%d", datePicker.getDayOfMonth(),
                                    datePicker.getMonth() + 1 < 10 ? "0" + (datePicker.getMonth() + 1) : datePicker.getMonth() + 1, datePicker.getYear());
                            etBirth.setText(dateString);
                        }
                    }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

                    datePickerDialog.show();
                }
            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PROFILE_PIC_REQUEST_CODE) {
            try {

                if (data != null) {

                    selectedPicture = Util.getPictureFromIntent(data, getContext());

                    Owner o = new Owner(
                            loggedOwner.getId(),
                            loggedOwner.getUsername(),
                            loggedOwner.getPassword(),
                            loggedOwner.getName(),
                            loggedOwner.getLastName(),
                            loggedOwner.getBirthDate(),
                            loggedOwner.getDNI(),
                            loggedOwner.geteMail(),
                            loggedOwner.getAddress(),
                            loggedOwner.getPhoneNumber(),
                            loggedOwner.getProfilePicture(),
                            loggedOwner.getDistrict().getId()
                    );

                    o.setImageBase64(Util.toBase64(Util.bitmapToBytes(selectedPicture.getImage(), selectedPicture.getType())));
                    o.setImageExtension(selectedPicture.getType());
                    ownerDao.update(o, new Ws.WsCallback<Boolean>() {
                        @Override
                        public void execute(Boolean response) {
                            if (response) {
                                Owner own = new Owner();
                                own.setUsername(own.getUsername());
                                own.setPassword(own.getPassword());

                                ownerDao.fullLogin(own, new Ws.WsCallback<OwnerDto>() {
                                    @Override
                                    public void execute(OwnerDto response) {
                                        Picasso.get()
                                                .load(selectedPicture.getUri())
                                                .placeholder(R.drawable.progress_circle_anim)
                                                .into(imgProfilePic);

                                        loggedOwner = response;
                                        Util.setLoggedOwner(loggedOwner, getContext());
                                    }
                                });

                            }
                        }
                    });

                }

            } catch (IOException e) {
                Util.showAlert("Ha ocurrido un error al subir su imagen", getContext());
            }
        }
    }

    private View.OnClickListener logoutAction = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (Util.logout(getContext())) {
                Intent i = new Intent(getContext(), LoginActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            } else
                Util.showAlert("Ha currido un problema", getContext());
        }
    };

    private void switchViews(boolean edit) {

        txtName.setText(loggedOwner.getName());
        etName.setText(loggedOwner.getName());

        txtLastName.setText(loggedOwner.getLastName());
        etLastName.setText(loggedOwner.getLastName());

        txtDni.setText(loggedOwner.getDNI());
        etDni.setText(loggedOwner.getDNI());

        Format f = new SimpleDateFormat("dd/MM/yyyy");
        txtBirth.setText(f.format(loggedOwner.getBirthDate()));
        etBirth.setText(f.format(loggedOwner.getBirthDate()));

        txtEmail.setText(loggedOwner.geteMail());
        etEmail.setText(loggedOwner.geteMail());

        txtPhone.setText(loggedOwner.getPhoneNumber());
        etPhone.setText(loggedOwner.getPhoneNumber());

        txtAddress.setText(loggedOwner.getAddress());
        etAddress.setText(loggedOwner.getAddress());

        txtDistrict.setText(loggedOwner.getDistrict().getName());
        spnDistrict.setSelection(districts.indexOf(selectedDistrict));

        if (edit) {
            fabEdit.setVisibility(View.GONE);
            fabSave.setVisibility(View.VISIBLE);

            txtName.setVisibility(View.GONE);
            etName.setVisibility(View.VISIBLE);

            txtLastName.setVisibility(View.GONE);
            etLastName.setVisibility(View.VISIBLE);

            txtDni.setVisibility(View.GONE);
            etDni.setVisibility(View.VISIBLE);

            txtBirth.setVisibility(View.GONE);
            etBirth.setVisibility(View.VISIBLE);

            txtEmail.setVisibility(View.GONE);
            etEmail.setVisibility(View.VISIBLE);

            txtPhone.setVisibility(View.GONE);
            etPhone.setVisibility(View.VISIBLE);

            txtAddress.setVisibility(View.GONE);
            etAddress.setVisibility(View.VISIBLE);

            txtDistrict.setVisibility(View.GONE);
            spnDistrict.setVisibility(View.VISIBLE);
        } else {
            fabEdit.setVisibility(View.VISIBLE);
            fabSave.setVisibility(View.GONE);

            txtName.setVisibility(View.VISIBLE);
            etName.setVisibility(View.GONE);

            txtLastName.setVisibility(View.VISIBLE);
            etLastName.setVisibility(View.GONE);

            txtDni.setVisibility(View.VISIBLE);
            etDni.setVisibility(View.GONE);

            txtBirth.setVisibility(View.VISIBLE);
            etBirth.setVisibility(View.GONE);

            txtEmail.setVisibility(View.VISIBLE);
            etEmail.setVisibility(View.GONE);

            txtPhone.setVisibility(View.VISIBLE);
            etPhone.setVisibility(View.GONE);

            txtAddress.setVisibility(View.VISIBLE);
            etAddress.setVisibility(View.GONE);

            txtDistrict.setVisibility(View.VISIBLE);
            spnDistrict.setVisibility(View.GONE);
        }
    }

}