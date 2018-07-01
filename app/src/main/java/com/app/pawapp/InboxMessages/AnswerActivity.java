package com.app.pawapp.InboxMessages;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.app.pawapp.Adapters.AnswerAdapter;
import com.app.pawapp.Classes.Answer;
import com.app.pawapp.R;

import java.util.ArrayList;

public class AnswerActivity extends AppCompatActivity {

    private ListView listView;
    private AnswerAdapter answerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        setTitle("Repuestas de Solicitudes");

        listView = findViewById(R.id.answer_list);
        answerAdapter = new AnswerAdapter(this, GetArrayItems());
        listView.setAdapter(answerAdapter);
    }

    private ArrayList<Answer> GetArrayItems() {
        ArrayList<Answer> answer = new ArrayList<>();

        answer.add(new Answer(R.drawable.profile, "Enrique", "Palacios",
               "Cumples las condiciones para adoptar a <Pet Name>. El dueño se pondrá en contacto contigo durante el día.","10:50 AM"));
        answer.add(new Answer(R.drawable.profile, "Enrique", "Palacios",
                "Cumples las condiciones para adoptar a <Pet Name>. El dueño se pondrá en contacto contigo durante el día.","10:50 AM"));
        answer.add(new Answer(R.drawable.profile, "Enrique", "Palacios",
                "Cumples las condiciones para adoptar a <Pet Name>. El dueño se pondrá en contacto contigo durante el día.","10:50 AM"));
        answer.add(new Answer(R.drawable.profile, "Enrique", "Palacios",
                "No cumples las condiciones para adoptar a <Pet Name>.","10:50 AM"));
        answer.add(new Answer(R.drawable.profile, "Enrique", "Palacios",
                "No cumples las condiciones para adoptar a <Pet Name>.","10:50 AM"));
        answer.add(new Answer(R.drawable.profile, "Enrique", "Palacios",
                "No cumples las condiciones para adoptar a <Pet Name>.","10:50 AM"));
        answer.add(new Answer(R.drawable.profile, "Enrique", "Palacios",
                "No cumples las condiciones para adoptar a <Pet Name>.","10:50 AM"));
        answer.add(new Answer(R.drawable.profile, "Enrique", "Palacios",
                "No cumples las condiciones para adoptar a <Pet Name>.","10:50 AM"));

        return answer;
    }

}