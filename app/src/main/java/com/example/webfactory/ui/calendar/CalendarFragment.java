package com.example.webfactory.ui.calendar;

import android.content.DialogInterface;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.example.webfactory.R;
import com.example.webfactory.databinding.FragmentCalendarBinding;
import com.example.webfactory.model.CalendarUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class CalendarFragment extends Fragment {

    private FragmentCalendarBinding binding;
    private List<EventDay> mEventDays;
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    private FirebaseUser user;


    public CalendarFragment() {
        super(R.layout.fragment_calendar);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCalendarBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        myRef = FirebaseDatabase.getInstance().getReference();

        mEventDays = new ArrayList<>();


        myRef.child(user.getUid()).child("calendar").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<CalendarUser> calendarUserList = new ArrayList<>();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    CalendarUser calendarUser = dataSnapshot.getValue(CalendarUser.class);
                    calendarUserList.add(calendarUser);
                }
                calendarUserList.forEach((CalendarUser name) -> {
                    SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZ yyyy", Locale.ENGLISH);
                    try {
                        Date date = format.parse(name.getDate());
                        Toast.makeText(getContext(), date.toString(), Toast.LENGTH_SHORT).show();
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(date);
                        mEventDays.add(new EventDay(calendar, R.drawable.ic_menu_camera));
                       binding.calendarView.setEvents(mEventDays);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




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
                CalendarUser calendarUser = new CalendarUser();
                calendarUser.setDate(clickedDayCalendar.getTime().toString());
                myRef.child(user.getUid()).child("calendar").push().setValue(calendarUser);
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
        ConstraintLayout cl1 = (ConstraintLayout) getLayoutInflater().inflate(R.layout.alert_calendar, null);
        alertDialog.setView(cl1);
        alertDialog.show();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }



}