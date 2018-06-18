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

import com.app.pawapp.MainActivity;
import com.app.pawapp.R;

public class ProfileFragment extends Fragment {

    TextView txtName,txtLastName,txtDni,txtBirth,txtEmail,txtPhone,txtAddress,txtDistrict;
    EditText etName,etLastName,etDni,etBirth,etEmail,etPhone,etAddress,etDistrict;
    FloatingActionButton fabEdit,fabSave;

    public ProfileFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

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

        v.findViewById(R.id.fabEdit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
            }
        });

        v.findViewById(R.id.fabSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        });

        return v;
    }
}