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

public class MessageAdapter extends BaseAdapter {
    protected Activity activity;
    protected ArrayList<Message> msg;

    public MessageAdapter(Activity activity, ArrayList<Message> msg) {
        this.activity = activity;
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
    public View getView(int position, View contentView, ViewGroup parent) {
        View vi=contentView;

        if(contentView == null) {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            vi = inflater.inflate(R.layout.item_message_list, null);
        }

        Message item = msg.get(position);

        ImageView image = vi.findViewById(R.id.imgAdopter);
        image.setImageResource(item.getImg());

        TextView name = vi.findViewById(R.id.txtNameAdopter);
        name.setText(item.getName());

        TextView lastname = vi.findViewById(R.id.txtLastNameAdopter);
        lastname.setText(item.getLastName());

        TextView email = vi.findViewById(R.id.txtEmailAdopter);
        email.setText(item.getEmail());

        TextView phone = vi.findViewById(R.id.txtPhoneAdopter);
        phone.setText(item.getPhone());

        TextView time = vi.findViewById(R.id.txtTime);
        time.setText(item.getTime());

        return vi;
    }
}