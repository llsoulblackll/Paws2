package com.app.pawapp.InboxMessages;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.app.pawapp.Adapters.MessageAdapter;
import com.app.pawapp.Classes.Message;
import com.app.pawapp.DataAccess.DataAccessObject.DaoFactory;
import com.app.pawapp.DataAccess.DataAccessObject.PetAdopterDao;
import com.app.pawapp.DataAccess.DataAccessObject.Ws;
import com.app.pawapp.DataAccess.DataTransferObject.OwnerDto;
import com.app.pawapp.DataAccess.DataTransferObject.PetAdopterDto;
import com.app.pawapp.DataAccess.Entity.PetAdopter;
import com.app.pawapp.DialogUser.InfoDialogActivity;
import com.app.pawapp.R;
import com.app.pawapp.Util.Util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class InboxActivity extends AppCompatActivity {

    private ListView lv;
    private MessageAdapter messageAdapter;

    private OwnerDto loggedOwner;
    private List<PetAdopterDto> requests;
    private PetAdopterDao petAdopterDao;

    CharSequence options[] = new CharSequence[]{"Ver Información de Usuario", "Cancelar"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);
        setTitle("Solicitud de Adopciones");

        petAdopterDao = DaoFactory.getPetAdopterDao(this);
        loggedOwner = Util.getLoggedOwner(this);

        lv = findViewById(R.id.inbox_list);

        GetArrayItems(false);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                final PetAdopterDto selected = requests.get(position);

                AlertDialog.Builder builder = new AlertDialog.Builder(InboxActivity.this);
                LayoutInflater inflater = InboxActivity.this.getLayoutInflater();
                builder.setView(inflater.inflate(R.layout.activity_info_dialog, null))
                        .setPositiveButton(R.string.accept, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                /*Aquí envía una notificación al otro usuario en una lista especial que alberga las respuestas*/
                                final ProgressDialog pg = ProgressDialog.show(InboxActivity.this, "Enviando", "Porfavor espere");
                                petAdopterDao.update(new PetAdopter(
                                        selected.getPet().getId(),
                                        selected.getAdopter().getId(),
                                        selected.getRequestDate(),
                                        new Date(),
                                        true
                                ), new Ws.WsCallback<Boolean>() {
                                    @Override
                                    public void execute(Boolean response) {
                                        if(response) {
                                            Toast.makeText(InboxActivity.this, "Solicitud Aceptada", Toast.LENGTH_SHORT).show();
                                            GetArrayItems(true);
                                        }
                                        pg.dismiss();
                                    }
                                });
                            }
                        })
                        .setNegativeButton(R.string.refuse, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                /*Aquí envía una notificación al otro usuario en una lista especial que alberga las respuestas*/
                                final ProgressDialog pg = ProgressDialog.show(InboxActivity.this, "Enviando", "Porfavor espere");
                                petAdopterDao.update(new PetAdopter(
                                        selected.getPet().getId(),
                                        selected.getAdopter().getId(),
                                        selected.getRequestDate(),
                                        new Date(),
                                        false
                                ), new Ws.WsCallback<Boolean>() {
                                    @Override
                                    public void execute(Boolean response) {
                                        if(response) {
                                            Toast.makeText(InboxActivity.this, "Solicitud Rechazada", Toast.LENGTH_SHORT).show();
                                            GetArrayItems(true);
                                        }
                                        pg.dismiss();
                                    }
                                });
                            }
                        });
                builder.show();
            }
        });
    }

    private void GetArrayItems(boolean block) {
        final ProgressDialog pg = new ProgressDialog(this);

        pg.setTitle("Cargando");
        pg.setMessage("Espere porfavor");

        if(block)
            pg.show();

        petAdopterDao.findAllRequestsDto(loggedOwner.getId(), new Ws.WsCallback<List<PetAdopterDto>>() {
            @Override
            public void execute(List<PetAdopterDto> response) {
                if(response != null){
                    requests = response;

                    DateFormat timeFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());
                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    //System.out.println(timeFormat.format(new Date()));
                    //System.out.println(dateFormat.format(new Date()));
                    //System.out.println(TimeZone.getDefault());

                    /*for(String t : TimeZone.getAvailableIDs())
                        System.out.println(t);*/

                    timeFormat.setTimeZone(TimeZone.getTimeZone("America/Lima"));
                    dateFormat.setTimeZone(TimeZone.getTimeZone("America/Lima"));

                    List<Message> messages = new ArrayList<>();

                    for(PetAdopterDto pa : requests) {
                        Message msg = new Message();
                        msg.setName(pa.getAdopter().getName());
                        msg.setLastName(pa.getAdopter().getLastName());
                        msg.setTime(
                                dateFormat.format(new Date()).equals(dateFormat.format(pa.getRequestDate()))
                                        ? timeFormat.format(pa.getRequestDate())
                                        : dateFormat.format(pa.getRequestDate())
                        );
                        msg.setMessage("Desea adoptar a " + pa.getPet().getName());
                        msg.setImgUrl(pa.getAdopter().getProfilePicture());
                        messages.add(msg);
                    }

                    lv.setAdapter(new MessageAdapter(InboxActivity.this, messages));
                }
                if(pg.isShowing())
                    pg.dismiss();
            }
        });

    }

}