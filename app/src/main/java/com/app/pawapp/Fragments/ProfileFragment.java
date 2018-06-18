package com.app.pawapp.Fragments;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.app.pawapp.DataAccess.DataAccessObject.DaoFactory;
import com.app.pawapp.DataAccess.DataAccessObject.OwnerDao;
import com.app.pawapp.DataAccess.DataAccessObject.Ws;
import com.app.pawapp.DataAccess.Entity.Owner;
import com.app.pawapp.MainActivity;
import com.app.pawapp.R;
import com.app.pawapp.Util.Util;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ProfileFragment extends Fragment {

    TextView txtName,txtLastName,txtDni,txtBirth,txtEmail,txtPhone,txtAddress,txtDistrict;
    EditText etName,etLastName,etDni,etBirth,etEmail,etPhone,etAddress,etDistrict;
    FloatingActionButton fabEdit,fabSave;

    private Gson gson;
    private Owner loggedOwner;

    public ProfileFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        final OwnerDao ownerDao = DaoFactory.getOwnerDao(getContext());

        txtName = v.findViewById(R.id.txtName);
        txtLastName = v.findViewById(R.id.txtLastName);
        txtDni = v.findViewById(R.id.txtDni);
        txtBirth = v.findViewById(R.id.txtBirth);
        txtEmail = v.findViewById(R.id.txtEmail);
        txtPhone = v.findViewById(R.id.txtPhone);
        txtAddress = v.findViewById(R.id.txtAddress);
        txtDistrict = v.findViewById(R.id.txtDistrict);

        etName = v.findViewById(R.id.etName);
        etLastName= v.findViewById(R.id.etLastName);
        etDni = v.findViewById(R.id.etDni);
        etBirth = v.findViewById(R.id.etBirth);
        etEmail = v.findViewById(R.id.etEmail);
        etPhone = v.findViewById(R.id.etPhone);
        etAddress = v.findViewById(R.id.etAddress);
        etDistrict = v.findViewById(R.id.etDistrict);

        fabEdit = v.findViewById(R.id.fabEdit);
        fabSave = v.findViewById(R.id.fabSave);

        gson = new Gson();
        loggedOwner = gson.fromJson(Util.SharedPreferencesHelper.getValue(Util.LOGGED_OWNER_KEY, getContext()).toString(), Owner.class);

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

                loggedOwner = new Owner();

                loggedOwner.setName(etName.getText().toString());
                loggedOwner.setLastName(etLastName.getText().toString());
                try {
                    loggedOwner.setBirthDate(new SimpleDateFormat().parse(etBirth.getText().toString()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                loggedOwner.setDNI(etDni.getText().toString());
                loggedOwner.seteMail(etEmail.getText().toString());
                loggedOwner.setAddress(etAddress.getText().toString());
                loggedOwner.setPhoneNumber(etPhone.getText().toString());
                loggedOwner.setDistrictId(Integer.parseInt(etDistrict.getText().toString()));

                ownerDao.update(loggedOwner, new Ws.WsCallback<Boolean>() {
                    @Override
                    public void execute(Boolean response) {
                        if(response)
                            Util.SharedPreferencesHelper.setValue(Util.LOGGED_OWNER_KEY, gson.toJson(loggedOwner), getContext());
                        switchViews(false);
                    }
                });

            }
        });

        return v;
    }

    void switchViews(boolean edit){

        txtName.setText(loggedOwner.getName());
        etName.setText(loggedOwner.getName());

        txtLastName.setText(loggedOwner.getLastName());
        etLastName.setText(loggedOwner.getLastName());

        txtDni.setText(loggedOwner.getDNI());
        etDni.setText(loggedOwner.getDNI());

        txtBirth.setText(loggedOwner.getBirthDate().toString());
        etBirth.setText(loggedOwner.getBirthDate().toString());

        txtEmail.setText(loggedOwner.geteMail());
        etEmail.setText(loggedOwner.geteMail());

        txtPhone.setText(loggedOwner.getPhoneNumber());
        etPhone.setText(loggedOwner.getPhoneNumber());

        txtAddress.setText(loggedOwner.getAddress());
        etAddress.setText(loggedOwner.getAddress());

        txtDistrict.setText(String.valueOf(loggedOwner.getDistrictId()));
        etDistrict.setText(String.valueOf(loggedOwner.getDistrictId()));

        if(edit){
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
            etDistrict.setVisibility(View.VISIBLE);
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
            etDistrict.setVisibility(View.GONE);
        }
    }

}