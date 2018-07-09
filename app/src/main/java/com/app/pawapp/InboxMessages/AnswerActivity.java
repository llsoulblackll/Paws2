package com.app.pawapp.InboxMessages;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.app.pawapp.Adapters.AnswerAdapter;
import com.app.pawapp.Classes.Answer;
import com.app.pawapp.DataAccess.DataAccessObject.DaoFactory;
import com.app.pawapp.DataAccess.DataAccessObject.PetAdopterDao;
import com.app.pawapp.DataAccess.DataAccessObject.Ws;
import com.app.pawapp.DataAccess.DataTransferObject.PetAdopterDto;
import com.app.pawapp.DataAccess.Entity.PetAdopter;
import com.app.pawapp.R;
import com.app.pawapp.Util.Util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class AnswerActivity extends AppCompatActivity {

    private ListView listView;
    private AnswerAdapter answerAdapter;

    private List<PetAdopterDto> answers;
    private PetAdopterDao petAdopterDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        setTitle("Repuestas de Solicitudes");

        petAdopterDao = DaoFactory.getPetAdopterDao(this);

        listView = findViewById(R.id.answer_list);

        GetArrayItems(false);
    }

    private void GetArrayItems(boolean block) {
        petAdopterDao.findAllAnswersDto(Util.getLoggedOwner(this).getId(), new Ws.WsCallback<List<PetAdopterDto>>() {
            @Override
            public void execute(List<PetAdopterDto> response) {
                if (response != null) {
                    ArrayList<Answer> anss = new ArrayList<>();

                    answers = response;

                    DateFormat timeFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());
                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    timeFormat.setTimeZone(TimeZone.getTimeZone("UTC+7"));
                    dateFormat.setTimeZone(TimeZone.getTimeZone("UTC+7"));

                    Answer ans;
                    for (PetAdopterDto pa : response) {
                        ans = new Answer();
                        if (pa.isState())
                            ans.setAnswer("Cumples las condiciones para adoptar a " + pa.getPet().getName() + ". El dueño se pondrá en contacto contigo durante el día.");
                        else
                            ans.setAnswer("No cumples las condiciones para adoptar a " + pa.getPet().getName() + ".");

                        ans.setImgUrl(pa.getAdopter().getProfilePicture());
                        ans.setNameOwner(pa.getPet().getOwner().getName());
                        ans.setLastNameOwner(pa.getPet().getOwner().getLastName());
                        ans.setTime(dateFormat.format(pa.getResponseDate()).equals(dateFormat.format(new Date()))
                                ? timeFormat.format(pa.getResponseDate())
                                : dateFormat.format(pa.getResponseDate()));

                        anss.add(ans);
                    }

                    listView.setAdapter(new AnswerAdapter(AnswerActivity.this, anss));
                }
            }
        });
    }

}