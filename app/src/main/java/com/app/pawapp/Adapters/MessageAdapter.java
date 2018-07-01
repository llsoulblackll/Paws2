package com.app.pawapp.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.pawapp.Classes.Message;
import com.app.pawapp.R;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Message> msg;

    public MessageAdapter(Context context, ArrayList<Message> msg) {
        this.context = context;
        this.msg = msg;
    }

    @Override
    public int getCount() {
        return msg.size();
    }

    @Override
    public Object getItem(int position) {
        return msg.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        Message item = (Message) getItem(position);

        view = LayoutInflater.from(context).inflate(R.layout.item_message_list, null);

        ImageView img = view.findViewById(R.id.imgAdopter);
        TextView name = view.findViewById(R.id.txtNameAdopter);
        TextView lastname = view.findViewById(R.id.txtLastNameAdopter);
        TextView message = view.findViewById(R.id.txtMessage);
        TextView time = view.findViewById(R.id.txtTime);

        img.setImageResource(item.getImg());
        name.setText(item.getName());
        lastname.setText(item.getLastName());
        message.setText(item.getMessage());
        time.setText(item.getTime());

        return view;
    }
}