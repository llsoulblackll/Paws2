package com.app.pawapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.pawapp.Classes.Answer;
import com.app.pawapp.R;

import java.util.ArrayList;

public class AnswerAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Answer> asw;

    public AnswerAdapter(Context context, ArrayList<Answer> asw) {
        this.context = context;
        this.asw = asw;
    }

    @Override
    public int getCount() {
        return asw.size();
    }

    @Override
    public Object getItem(int position) {
        return asw.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        Answer item = (Answer) getItem(position);

        view = LayoutInflater.from(context).inflate(R.layout.item_answer_list, null);

        ImageView img = view.findViewById(R.id.imgOwner);
        TextView name = view.findViewById(R.id.txtNameOwner);
        TextView lastname = view.findViewById(R.id.txtLastNameOwner);
        TextView message = view.findViewById(R.id.txtAnswer);
        TextView time = view.findViewById(R.id.txtTime);

        img.setImageResource(item.getImgOwner());
        name.setText(item.getNameOwner());
        lastname.setText(item.getLastNameOwner());
        message.setText(item.getAnswer());
        time.setText(item.getTime());

        return view;

    }
}