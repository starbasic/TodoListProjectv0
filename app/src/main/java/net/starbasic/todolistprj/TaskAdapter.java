package net.starbasic.todolistprj;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class TaskAdapter extends ArrayAdapter<Task> {

    private LayoutInflater inflater;
    private int layout;
    private List<Task> tasks;
    private Context context;


    public TaskAdapter(Context context, int resource, List<Task> tasks) {
        super(context, resource, tasks);
        this.tasks = tasks;
        this.layout = resource;
        this.context = context;
        this.inflater = LayoutInflater.from(context);


    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View view=inflater.inflate(this.layout, parent, false);

        ImageView logoView = (ImageView) view.findViewById(R.id.logo);
        TextView contentView = (TextView) view.findViewById(R.id.text_content);
        TextView viewD = (TextView) view.findViewById(R.id.day_field);
        TextView viewM = (TextView) view.findViewById(R.id.month_field);
        TextView viewY = (TextView) view.findViewById(R.id.year_field);
        TextView viewHH = (TextView) view.findViewById(R.id.hours);
        TextView viewMM = (TextView) view.findViewById(R.id.minutes);

        TextView rateView = (TextView) view.findViewById(R.id.rating);

        Task task = tasks.get(position);

        logoView.setImageResource(task.getCategory());
        contentView.setText(task.getContent());
        viewD.setText(String.valueOf(task.getDay()));
        viewM.setText(String.valueOf(task.getMonth()));
        viewY.setText(String.valueOf(task.getYear()));
        viewHH.setText(String.valueOf(task.getHours()));
        viewMM.setText(String.valueOf(task.getMinutes()));
        rateView.setText(String.valueOf(task.getPriority()));
        Activity activity = (Activity) context;
        activity.registerForContextMenu(view);
        return view;
    }


}
