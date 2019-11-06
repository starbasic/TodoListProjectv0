package net.starbasic.todolistprj;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class AddItemFragment extends Fragment {
    //private OnFragmentInteractionListener mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_item, container, false);
        Button button = (Button) view.findViewById(R.id.update_button);
        // задаем обработчик кнопки
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Зараз буде обробка даних", Toast.LENGTH_LONG).show();

                //updateDetail();
            }
        });
        return view;
    }

//    interface OnFragmentInteractionListener {
//
//        void onFragmentInteraction(String link);
//    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Toast.makeText(getContext(),"Фрагмент доєднався", Toast.LENGTH_LONG).show();
//        try {
//            mListener = (OnFragmentInteractionListener) context;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(context.toString()
//                    + " должен реализовывать интерфейс OnFragmentInteractionListener");
//        }
    }
//    public void updateDetail() {
//        // генерируем некоторые данные
//        String curDate = new Date().toString();
//        // Посылаем данные Activity
//        mListener.onFragmentInteraction(curDate);
//    }
}
