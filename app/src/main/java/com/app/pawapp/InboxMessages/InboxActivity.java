package com.app.pawapp.InboxMessages;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.app.pawapp.Adapters.MessageAdapter;
import com.app.pawapp.Classes.Message;
import com.app.pawapp.R;

import java.util.ArrayList;

public class InboxActivity extends AppCompatActivity {

    ListView lv;
    MessageAdapter messageAdapter;
    ArrayList<Message> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);
        setTitle("Bandeja de Entrada");

        lv = findViewById(R.id.inbox_list);
        ArrayList<Message> messages = items();
        messageAdapter = new MessageAdapter(this, messages);
        lv.setAdapter(messageAdapter);
    }

    private ArrayList<Message> items() {
        items = new ArrayList<Message>();

        items.add(new Message(R.drawable.profile, "Armando", "Casas","acasas@gmail.com", "990676480",
                "10:35 AM"));
        items.add(new Message(R.drawable.profile, "Armando", "Casas","acasas@gmail.com", "990676480",
                "10:35 AM"));
        items.add(new Message(R.drawable.profile, "Armando", "Casas","acasas@gmail.com", "990676480",
                "10:35 AM"));
        items.add(new Message(R.drawable.profile, "Armando", "Casas","acasas@gmail.com", "990676480",
                "10:35 AM"));
        items.add(new Message(R.drawable.profile, "Armando", "Casas","acasas@gmail.com", "990676480",
                "10:35 AM"));
        items.add(new Message(R.drawable.profile, "Armando", "Casas","acasas@gmail.com", "990676480",
                "10:35 AM"));

        return items;
    }

}