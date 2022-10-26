package com.example.webfactory.presentation.calendar

import com.example.webfactory.R
import com.applandeo.materialcalendarview.EventDay
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.auth.FirebaseUser
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DataSnapshot
import com.example.webfactory.model.CalendarUser
import com.google.firebase.database.DatabaseError
import android.content.DialogInterface
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.webfactory.databinding.FragmentCalendarBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import io.reactivex.rxjava3.core.*;
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

class CalendarFragment : Fragment(R.layout.fragment_calendar) {

    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!
    private var mEventDays: MutableList<EventDay>
    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private var myRef: DatabaseReference
    private var user: FirebaseUser
    private val scheduleList = arrayOf("2/2", "5/2")

    init {
        mEventDays = ArrayList()
        myRef = FirebaseDatabase.getInstance().reference
        user = mAuth.currentUser!!
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.addSchedule.setOnClickListener { deleteSchedule() }

        val dispose = updateCalendar()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                binding.calendarView.setEvents(mEventDays)
            }){
                Log.e("MyLog", "${it.localizedMessage}")
            }

        binding.calendarView.setOnDayClickListener { eventDay -> addSchedule(eventDay) }
        return root
    }

    private fun addSchedule(eventDay: EventDay) {
        val selectedSchedule = arrayOfNulls<String>(1)
        val cal = eventDay.calendar
        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.setTitle(getString(R.string.select_shedule))
        alertDialog.setSingleChoiceItems(scheduleList, -1) { _, which ->
            selectedSchedule[0] = scheduleList[which]
        }
        alertDialog.setPositiveButton(getString(R.string.select), DialogInterface.OnClickListener { dialog, which ->
            val calendarUser = CalendarUser(null, "")
            val i = cal[Calendar.MONTH]
            if (selectedSchedule[0] == scheduleList[0]) {
                cal[Calendar.DAY_OF_MONTH]
                while (cal[Calendar.DAY_OF_MONTH] <= Calendar.getInstance().getActualMaximum(
                        Calendar.DAY_OF_MONTH
                    )
                ) {
                    if (i < cal[Calendar.MONTH]) {
                        return@OnClickListener
                    } else {
                        calendarUser.date = cal.time.toString()
                        myRef.child(user.uid).child("calendar").push().setValue(calendarUser)
                        cal.add(Calendar.DAY_OF_MONTH, 1)
                        calendarUser.date = cal.time.toString()
                        myRef.child(user.uid).child("calendar").push().setValue(calendarUser)
                    }
                    cal.add(Calendar.DAY_OF_MONTH, 3)
                }
                Toast.makeText(context, getString(R.string.selected_shedule) + selectedSchedule, Toast.LENGTH_SHORT).show()
            } else {
                cal[Calendar.DAY_OF_MONTH]
                while (cal[Calendar.DAY_OF_MONTH] <= Calendar.getInstance().getActualMaximum(
                        Calendar.DAY_OF_MONTH
                    )
                ) {
                    if (cal[Calendar.DAY_OF_WEEK] != Calendar.SATURDAY && cal[Calendar.DAY_OF_WEEK] != Calendar.SUNDAY) {
                        if (i < cal[Calendar.MONTH]) {
                            return@OnClickListener
                        } else {
                            calendarUser.date = cal.time.toString()
                            myRef.child(user.uid).child("calendar").push()
                                .setValue(calendarUser)
                        }
                    }
                    cal.add(Calendar.DAY_OF_MONTH, 1)
                }
                Toast.makeText(context, getString(R.string.selected_shedule) + selectedSchedule, Toast.LENGTH_SHORT).show()
            }
        })
        alertDialog.setNegativeButton(getString(R.string.cancel)) { _, _ -> }
        alertDialog.show()
    }

    private fun deleteSchedule() {
        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.setTitle(getString(R.string.atention))
        alertDialog.setMessage(getString(R.string.delete_shedule_ask))
        alertDialog.setPositiveButton("Да") { _, _ ->
            val dbref = FirebaseDatabase.getInstance().reference.child(
                user.uid
            ).child("calendar")
            dbref.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    dataSnapshot.ref.removeValue()
                    mEventDays = ArrayList()
                    binding.calendarView.setEvents(mEventDays)
                    Toast.makeText(context, getString(R.string.delete_shedule), Toast.LENGTH_SHORT).show()
                }

                override fun onCancelled(error: DatabaseError) {}
            })
        }
        alertDialog.setNegativeButton("Нет") { _, _ -> }
        alertDialog.show()
    }

    private fun updateCalendar(): Observable<MutableList<EventDay>> {
        return Observable.create {subscriber ->
            myRef.child(user.uid).child("calendar")
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {

                        val calendarUserList: MutableList<CalendarUser> = ArrayList()
                        val calendarUserKeys: MutableList<String> = ArrayList()

                        for (dataSnapshot in snapshot.children) {
                            val calendarUser = dataSnapshot.getValue(CalendarUser::class.java)!!
                            calendarUserList.add(calendarUser)
                            calendarUserKeys.add(dataSnapshot.key!!)
                        }

                        calendarUserList.forEach { name: CalendarUser ->
                            val format = SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZ yyyy", Locale.ENGLISH)
                            try {
                                val date = format.parse(name.date!!)!!
                                val calendar = Calendar.getInstance()
                                calendar.time = date
                                mEventDays.add(EventDay(calendar, android.R.drawable.presence_online))
                                subscriber.onNext(mEventDays)
                            } catch (e: ParseException) {
                                e.printStackTrace()
                            }
                        }
                    }
                    override fun onCancelled(error: DatabaseError) {}
                })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}