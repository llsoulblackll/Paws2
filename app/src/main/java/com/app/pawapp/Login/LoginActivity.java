package com.app.pawapp.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.app.pawapp.DataAccess.DataAccessObject.DaoFactory;
import com.app.pawapp.DataAccess.DataAccessObject.DistrictDao;
import com.app.pawapp.DataAccess.DataAccessObject.OwnerDao;
import com.app.pawapp.DataAccess.DataAccessObject.Ws;
import com.app.pawapp.DataAccess.DataTransferObject.OwnerDto;
import com.app.pawapp.DataAccess.Entity.District;
import com.app.pawapp.DataAccess.Entity.Owner;
import com.app.pawapp.MainActivity;
import com.app.pawapp.R;
import com.app.pawapp.Register.RegisterActivity;
import com.app.pawapp.Survey.SurveyActivity;
import com.app.pawapp.Util.Util;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    public static final String USER_KEY = "UserKey";

    private OwnerDao ownerDao;

    private TextInputEditText userEditText;
    private TextInputEditText passEditText;

    AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(Util.isLoggedIn(this)) {
            startActivity(new Intent(this, MainActivity.class));
            ActivityCompat.finishAffinity(this);
        }

        setContentView(R.layout.activity_login);

        ownerDao = DaoFactory.getOwnerDao(this);

        awesomeValidation = new AwesomeValidation(ValidationStyle.TEXT_INPUT_LAYOUT);

        awesomeValidation.addValidation(this, R.id.tilUser, "^(?=[^\\d_].*?)\\w(\\w|[!@#$%]){4,14}$*", R.string.USER_ERROR);
        awesomeValidation.addValidation(this, R.id.tilPass, "^(?=[^\\d_].*?\\d)\\w(\\w|[.!@#$%]){4,9}$", R.string.PASS_ERROR);

        userEditText = findViewById(R.id.etUser);
        passEditText = findViewById(R.id.etPass);

        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

        Window window = this.getWindow();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    public void Login(View view) {
        if (awesomeValidation.validate()) {
            Validate();
        }
    }

    private void Validate() {
        final ProgressDialog pd = ProgressDialog.show(this, "Iniciando sesion", "Espere porfavor");

        Owner o = new Owner();
        o.setUsername(userEditText.getText().toString());
        o.setPassword(passEditText.getText().toString());
        ownerDao.fullLogin(o, new Ws.WsCallback<OwnerDto>() {

            @Override
            public void execute(OwnerDto response) {
                if (response != null) {
                    pd.dismiss();

                    //save user current user
                    Util.setLoggedOwner(response, LoginActivity.this);

                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    Toast.makeText(LoginActivity.this, String.format("Bievenido %s %s", response.getName(), response.getLastName()),
                            Toast.LENGTH_LONG).show();
                } else {
                    pd.dismiss();
                    Util.showAlert("Usuario y/o Contraseña incorrectos", LoginActivity.this);
                }
            }
        });

        pd.show();
    }

    public void Register(View view) {
        Intent i = new Intent(this, RegisterActivity.class);
        startActivity(i);
    }
}