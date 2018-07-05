package com.app.pawapp.InboxMessages;

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
import com.app.pawapp.DialogUser.InfoDialogActivity;
import com.app.pawapp.R;

import java.util.ArrayList;

public class InboxActivity extends AppCompatActivity {

    private ListView lv;
    private MessageAdapter messageAdapter;

    CharSequence options[] = new CharSequence[]{"Ver Información de Usuario", "Cancelar"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);
        setTitle("Solicitud de Adopciones");

        lv = findViewById(R.id.inbox_list);
        messageAdapter = new MessageAdapter(this, GetArrayItems());
        lv.setAdapter(messageAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                AlertDialog.Builder builder = new AlertDialog.Builder(InboxActivity.this);
                LayoutInflater inflater = InboxActivity.this.getLayoutInflater();
                builder.setView(inflater.inflate(R.layout.activity_info_dialog, null))
                        .setPositiveButton(R.string.accept, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                /*Aquí envía una notificación al otro usuario en una lista especial que alberga las respuestas*/
                                Toast.makeText(InboxActivity.this, "Solicitud Aceptada", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton(R.string.refuse, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                /*Aquí envía una notificación al otro usuario en una lista especial que alberga las respuestas*/
                                Toast.makeText(InboxActivity.this, "Solicitud Rechazada", Toast.LENGTH_SHORT).show();
                            }
                        });
                builder.show();
            }
        });
    }

    private ArrayList<Message> GetArrayItems() {
        ArrayList<Message> messages = new ArrayList<>();

        messages.add(new Message(R.drawable.profile, "Armando", "Casas", "Desea Adoptar a <Pet Name>.",
                "10:40 AM"));
        messages.add(new Message(R.drawable.profile, "Armando", "Casas", "Desea Adoptar a <Pet Name>.",
                "10:40 AM"));
        messages.add(new Message(R.drawable.profile, "Armando", "Casas", "Desea Adoptar a <Pet Name>.",
                "10:40 AM"));
        messages.add(new Message(R.drawable.profile, "Armando", "Casas", "Desea Adoptar a <Pet Name>.",
                "10:40 AM"));
        messages.add(new Message(R.drawable.profile, "Armando", "Casas", "Desea Adoptar a <Pet Name>.",
                "10:40 AM"));
        messages.add(new Message(R.drawable.profile, "Armando", "Casas", "Desea Adoptar a <Pet Name>.",
                "10:40 AM"));
        messages.add(new Message(R.drawable.profile, "Armando", "Casas", "Desea Adoptar a <Pet Name>.",
                "10:40 AM"));
        messages.add(new Message(R.drawable.profile, "Armando", "Casas", "Desea Adoptar a <Pet Name>.",
                "10:40 AM"));
        messages.add(new Message(R.drawable.profile, "Armando", "Casas", "Desea Adoptar a <Pet Name>.",
                "10:40 AM"));

        return messages;
    }

}