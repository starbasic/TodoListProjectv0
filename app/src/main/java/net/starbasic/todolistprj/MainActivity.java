package net.starbasic.todolistprj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


        private List<Task> tasks = new ArrayList();
        private int itemIndex;

        private final int CONTEXT_MENU_DEL = 10001;
        private final int CONTEXT_MENU_IMG = 10001;
        private final int CONTEXT_MENU_CONTENT = 10002;
        private final int CONTEXT_MENU_DATE = 10003;
        private final int CONTEXT_MENU_TIME = 10004;


        private StringBuffer textData;
        private ListView taskList;

        private final static String FILE_NAME = "saved.copy";

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);


            setInitialData();
            // получаем элемент ListView
            taskList = (ListView) findViewById(R.id.rootList);
            // создаем адаптер
            TaskAdapter taskAdapter = new TaskAdapter(this, R.layout.item_view, tasks);
            // устанавливаем адаптер
            taskList.setAdapter(taskAdapter);


            AdapterView.OnItemClickListener itemListener = new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {



                    Task selected = (Task)parent.getItemAtPosition(position);
                    Toast.makeText(getApplicationContext(), "Госмейстер " + selected.getContent() + "  "
                                    + selected.getDay() + ":" + selected.getMonth(),
                            Toast.LENGTH_SHORT).show();
                }
            };

            taskList.setOnItemClickListener(itemListener);

        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

            //TaskAdapter taskAdapter = (TaskAdapter) taskList.getAdapter();
            itemIndex = taskList.indexOfChild(v);
            //int index = states.indexOf();
            menu.add(0, CONTEXT_MENU_DEL, 0, "Delete");
            menu.add(0, CONTEXT_MENU_IMG, 0, "Change category");
            menu.add(0, CONTEXT_MENU_CONTENT, 0, "Change description");
            menu.add(0, CONTEXT_MENU_DATE, 0, "Change date");
            menu.add(0, CONTEXT_MENU_TIME, 0, "Change time");

            super.onCreateContextMenu(menu, v, menuInfo);
        }

        @Override
        public boolean onContextItemSelected(@NonNull MenuItem item) {

            switch(item.getItemId()) {
                case CONTEXT_MENU_DEL:
                    TaskAdapter taskAdapter = (TaskAdapter)taskList.getAdapter();
                    taskAdapter.remove(tasks.get( itemIndex));
                    break;
                case CONTEXT_MENU_CONTENT:
//                    String newDescription;
//                    Bundle b = new Bundle();
//                    showInputDialog(b);
//                    String s = (String) b.get("result");
//                    tasks.get( itemIndex).setContent(s);
                   taskAdapter = new TaskAdapter(this, R.layout.item_view, tasks);
                   View v = taskAdapter.getView(itemIndex,null,taskList);
                   TextView tv = v.findViewById(R.id.text_content);
                    showInputDialog(tv);
                    tasks.get(itemIndex).setContent(tv.getText().toString());
//                    taskList.setAdapter(taskAdapter);
//                    Toast.makeText(this, "Зміст завдання змінено", Toast.LENGTH_SHORT).show();
//                    //taskAdapter.
                    break;
                case CONTEXT_MENU_TIME:
                    int h=0,m=0;
                    showTimeDialog(h,m);
                    tasks.get( itemIndex).setHours(h);
                    tasks.get( itemIndex).setMinutes(m);
                    taskAdapter = new TaskAdapter(this, R.layout.item_view, tasks);
                    taskList.setAdapter(taskAdapter);
                    Toast.makeText(this, "Час змінено", Toast.LENGTH_SHORT).show();
                    break;
            }


            return super.onContextItemSelected(item);
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {

            getMenuInflater().inflate(R.menu.main_menu, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            int id = item.getItemId();
            if(id == R.id.add_order) {
                TaskAdapter taskA = (TaskAdapter) taskList.getAdapter();

                String sb = new String();
                //showInputDialog(sb);

                taskA.add(new Task (R.drawable.uncategorized, "Халявний текст",1, 1, 2020));
            }
            if(id == R.id.open_add) {
                Toast.makeText(this, "Відкриваємо додавальний фрагмент", Toast.LENGTH_SHORT).show();
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment_frame, new AddItemFragment())
                        .commit();

            }
            if(id == R.id.save_to_pref) {

                FileOutputStream fos = null;
                try {


                    fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                    oos.writeObject(tasks);
                    Toast.makeText(this, "Список збережено на внутрішньому носії", Toast.LENGTH_SHORT).show();
                }
                catch(IOException ex) {

                    Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            if(id == R.id.read_from_pref) {

                FileInputStream fis = null;
                try {


                    fis =  openFileInput(FILE_NAME);
                    ObjectInputStream ois = new ObjectInputStream(fis);
                    tasks = (List<Task>) ois.readObject();
                    TaskAdapter taskAdapter = new TaskAdapter(this, R.layout.item_view, tasks);

                    taskList.setAdapter(taskAdapter);
                    Toast.makeText(this, "Збережені дані відновлено", Toast.LENGTH_SHORT).show();
                }
                catch(IOException ex) {

                    Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            }
            return super.onOptionsItemSelected(item);


        }



        @Override
        protected void onRestoreInstanceState (Bundle savedInstanceState) {
            super.onRestoreInstanceState (savedInstanceState);

            tasks = savedInstanceState.getParcelableArrayList("list");
            TaskAdapter taskAdapter = new TaskAdapter(this, R.layout.item_view, tasks);
            // устанавливаем адаптер
            taskList.setAdapter(taskAdapter);
        }
        @Override
        protected void onSaveInstanceState (Bundle outState) {

            outState.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) tasks);
            super.onSaveInstanceState (outState);
        }


        private void setInitialData(){

            tasks.add(new Task (R.drawable.comp, "Конспект лекцій Інформатика для ПФ", 13, 11, 2019));
            tasks.add(new Task (R.drawable.edu, "Asp .net MVC", 24, 11, 2019));
            tasks.add(new Task (R.drawable.phis,"Розрахунки перевірити ", 31,12, 2019));
            tasks.add(new Task (R.drawable.uncategorized,"Дочитати 1984", 20, 11, 2019 ));

        }


    protected void showInputDialog(TextView v) {

        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
        View promptView = layoutInflater.inflate(R.layout.user_input_dlg, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder.setView(promptView);
        final TextView sText = v;
        final EditText editText = (EditText) promptView.findViewById(R.id.edit_text);
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String s = editText.getText().toString();
                        sText.setText(s);

                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();

    }


    protected void showTimeDialog(int hours, int minutes){
        TimePickerDialog tp = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Toast.makeText(getBaseContext(), "Вибрано " +  hourOfDay +" : " + minute, Toast.LENGTH_SHORT).show();


                    }
                },
                hours, minutes, true);
        tp.show();
    }



}
