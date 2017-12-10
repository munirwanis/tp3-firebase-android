package com.wanis.firebasetp3.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.wanis.firebasetp3.Entities.UserEntity;
import com.wanis.firebasetp3.R;

import java.util.ArrayList;

/**
 * Created by munirwanis on 10/12/17.
 */

public class UserAdapter extends ArrayAdapter<UserEntity> {

    private ArrayList<UserEntity> user;
    private Context context;


    public UserAdapter(Context c, ArrayList<UserEntity> objects) {
        super(c, 0, objects);
        this.context = c;
        this.user = objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;

        if (user != null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            view = inflater.inflate(R.layout.adapter_user, parent, false);
            TextView nameTextField = view.findViewById(R.id.txtViewNome);

            UserEntity userPosition = user.get(position);
            nameTextField.setText(userPosition.getName());

        }
        return view;
    }
}
