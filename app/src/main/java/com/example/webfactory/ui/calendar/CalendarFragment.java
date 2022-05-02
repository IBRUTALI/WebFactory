package com.example.webfactory.ui.calendar;

import android.content.DialogInterface;

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
    private List<String> calendarUserKeys;
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

        binding.addSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteSchedule();
            }
        });


        myRef.child(user.getUid()).child("calendar").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<CalendarUser> calendarUserList = new ArrayList<>();
                calendarUserKeys = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    CalendarUser calendarUser = dataSnapshot.getValue(CalendarUser.class);
                    calendarUserList.add(calendarUser);
                    calendarUserKeys.add(dataSnapshot.getKey());
                }
                calendarUserList.forEach((CalendarUser name) -> {
                    SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZ yyyy", Locale.ENGLISH);
                    try {
                        Date date = format.parse(name.getDate());
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(date);
                        mEventDays.add(new EventDay(calendar, android.R.drawable.presence_online));
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
                addSchedule(eventDay);
            }
        });

        return root;
    }

    private void addSchedule(EventDay eventDay) {
        String[] scheduleList = {"2/2", "5/2"};
        String[] selectedSchedule = new String[1];
        Calendar cal = eventDay.getCalendar();
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(requireContext());
        alertDialog.setTitle("Выберете график работы");
        alertDialog.setSingleChoiceItems(scheduleList, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectedSchedule[0] = scheduleList[which];
            }
        });
        alertDialog.setPositiveButton("Выбрать", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                CalendarUser calendarUser = new CalendarUser();
                int i = cal.get(Calendar.MONTH);
                if (selectedSchedule[0].equals(scheduleList[0])) {
                    for (cal.get(Calendar.DAY_OF_MONTH); cal.get(Calendar.DAY_OF_MONTH) <= Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH); cal.add(Calendar.DAY_OF_MONTH, 3)) {
                        if (i < cal.get(Calendar.MONTH)) {
                            return;
                        } else {
                            calendarUser.setDate(cal.getTime().toString());
                            myRef.child(user.getUid()).child("calendar").push().setValue(calendarUser);
                            cal.add(Calendar.DAY_OF_MONTH, 1);
                            calendarUser.setDate(cal.getTime().toString());
                            myRef.child(user.getUid()).child("calendar").push().setValue(calendarUser);
                        }
                    }
                    Toast.makeText(getContext(), "Выбран график работы 2/2", Toast.LENGTH_SHORT).show();
                } else {
                    for (cal.get(Calendar.DAY_OF_MONTH); cal.get(Calendar.DAY_OF_MONTH) <= Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH); cal.add(Calendar.DAY_OF_MONTH, 1)) {
                        if (cal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && cal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
                            if (i < cal.get(Calendar.MONTH)) {
                                return;
                            } else {
                                calendarUser.setDate(cal.getTime().toString());
                                myRef.child(user.getUid()).child("calendar").push().setValue(calendarUser);
                            }
                        }
                    }
                    Toast.makeText(getContext(), "Выбран график работы 5/2", Toast.LENGTH_SHORT).show();
                }
            }
        });
        alertDialog.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog.show();
    }

    private void deleteSchedule() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(requireContext());
        alertDialog.setTitle("Внимание");
        alertDialog.setMessage("Вы действительно хотите удалить рабочий график?");
        alertDialog.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DatabaseReference dbref = FirebaseDatabase.getInstance().getReference().child(user.getUid()).child("calendar");
                dbref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        dataSnapshot.getRef().removeValue();
                        mEventDays = new ArrayList<>();
                        binding.calendarView.setEvents(mEventDays);
                        Toast.makeText(getContext(), "График удален!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        alertDialog.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alertDialog.show();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}