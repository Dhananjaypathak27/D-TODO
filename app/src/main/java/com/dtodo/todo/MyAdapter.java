package com.dtodo.todo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class MyAdapter extends ArrayAdapter<TodoList> {

    Context context;
    int resource;
    List<TodoList> list;

    public MyAdapter(@NonNull Context context, int resource, @NonNull List<TodoList> objects) {
        super(context, resource, objects);

        this.context =context;
        this.resource = resource;
        this.list = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater =LayoutInflater.from(context);
        View view= inflater.inflate(R.layout.list_item_background,null);
        TextView textView= view.findViewById(R.id.textView);
        TodoList todoList = list.get(position);
        textView.setText(todoList.getData());

        return view;
    }
}
