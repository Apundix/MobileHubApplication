package com.evan.chattest2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;


public class noteAdapter extends ArrayAdapter<Note> {

    public noteAdapter(Context context, List<Note> notes){

        super(context, 0, notes);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Note note = getItem(position);
        if(convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.notecell, parent, false);

        TextView title = convertView.findViewById(R.id.cellTitle);
        TextView desc = convertView.findViewById(R.id.cellDesc);
        TextView mac = convertView.findViewById(R.id.macAddress);

        title.setText(note.getTitle());
        desc.setText(note.getDescription());
        mac.setText(note.getMac());

        return convertView;
    }
}
