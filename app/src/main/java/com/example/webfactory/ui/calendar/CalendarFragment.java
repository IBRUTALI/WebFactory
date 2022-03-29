package com.example.webfactory.ui.calendar;

import android.content.DialogInterface;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.example.webfactory.R;
import com.example.webfactory.databinding.FragmentCalendarBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class CalendarFragment extends Fragment {

    private FragmentCalendarBinding binding;
    private List<EventDay> mEventDays;
    private Calendar calendar;

    public CalendarFragment() {
        super(R.layout.fragment_calendar);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCalendarBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mEventDays = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();


        binding.calendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                addWorkerOnDate(eventDay);
            }
        });

        return root;
    }

    private void addWorkerOnDate(EventDay eventDay) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(requireContext());
        alertDialog.setPositiveButton("Добавить сотрудников", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Calendar clickedDayCalendar = eventDay.getCalendar();
                mEventDays.add(new EventDay(clickedDayCalendar, R.drawable.ic_menu_camera));
                binding.calendarView.setEvents(mEventDays);
                Toast.makeText(getContext(), "Сотрудники добавлены", Toast.LENGTH_SHORT).show();
            }
        });

        alertDialog.setNegativeButton("Удалить сотрудников", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Calendar clickedDayCalendar = eventDay.getCalendar();
                mEventDays.remove(eventDay);
                binding.calendarView.setEvents(mEventDays);
                Toast.makeText(getContext(), "Сотрудники удалены", Toast.LENGTH_SHORT).show();
            }
        });

        alertDialog.setNeutralButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        //ConstraintLayout cl1 = (ConstraintLayout) getLayoutInflater().inflate(R.layout.alert_polls, null);
        //alertDialog.setView(cl1);
        alertDialog.show();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }



}