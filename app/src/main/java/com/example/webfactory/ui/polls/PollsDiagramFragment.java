package com.example.webfactory.ui.polls;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.webfactory.Databases.DBHelperReview;
import com.example.webfactory.R;
import com.example.webfactory.databinding.FragmentPollsDiagramBinding;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PollsDiagramFragment extends Fragment {
    private FragmentPollsDiagramBinding binding;
    private NavController navController;
    private DBHelperReview dBHelperReview;
    private ArrayList<String> answers_id, answers_title, answers_var1, answers_ans1, answers_var2, answers_ans2, answers_var3, answers_ans3;
    private ArrayList<Long> answers_count1, answers_count2, answers_count3;
    private String[] answers_values = {"Да", "Скорее да, чем нет", "Скорее нет, чем да", "Нет"};
    private ArrayList<String> varList;

    public PollsDiagramFragment() {
        super(R.layout.fragment_polls_diagram);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentPollsDiagramBinding.inflate(inflater);
        View root = binding.getRoot();
        dBHelperReview = new DBHelperReview(getContext());
        storeDataInArrays();
        return root;
    }

    private void storeDataInArrays() {
        Cursor cursor = dBHelperReview.readAllDataPA();
        answers_id = new ArrayList<>();
        answers_title = new ArrayList<>();
        answers_var1 = new ArrayList<>();
        answers_ans1 = new ArrayList<>();
        answers_var2 = new ArrayList<>();
        answers_ans2 = new ArrayList<>();
        answers_var3 = new ArrayList<>();
        answers_ans3 = new ArrayList<>();
        answers_count1 = new ArrayList<>();
        answers_count2 = new ArrayList<>();
        answers_count3 = new ArrayList<>();
        if (cursor.getCount() == 0) {
            Toast.makeText(getContext(), "Нет данных ", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                if (getArguments() != null && cursor.getString(1).equals(getArguments().getString("pollsTitle"))) {
                    answers_id.add(cursor.getString(0));
                    answers_title.add(cursor.getString(1));
                    answers_var1.add(cursor.getString(2));
                    answers_ans1.add(cursor.getString(3));
                    answers_var2.add(cursor.getString(4));
                    answers_ans2.add(cursor.getString(5));
                    answers_var3.add(cursor.getString(6));
                    answers_ans3.add(cursor.getString(7));
                } else {
                    cursor.moveToNext();
                }
            }
            binding.textView2.setText(getArguments().getString("pollsTitle"));
        }
        varList = new ArrayList<>();
        varList.add(answers_var1.get(0));
        varList.add(answers_var2.get(0));
        varList.add(answers_var3.get(0));

        answers_count1.add(0l);
        answers_count1.add(0l);
        answers_count1.add(0l);
        answers_count1.add(0l);
        answers_count2.add(0l);
        answers_count2.add(0l);
        answers_count2.add(0l);
        answers_count2.add(0l);
        answers_count3.add(0l);
        answers_count3.add(0l);
        answers_count3.add(0l);
        answers_count3.add(0l);

        Map<String, Long> frequency1 =
                answers_ans1.stream().collect(Collectors.groupingBy(
                        Function.identity(), Collectors.counting()));

        Map<String, Long> frequency2 =
                answers_ans2.stream().collect(Collectors.groupingBy(
                        Function.identity(), Collectors.counting()));

        Map<String, Long> frequency3 =
                answers_ans3.stream().collect(Collectors.groupingBy(
                        Function.identity(), Collectors.counting()));


        frequency1.forEach((k, v) -> {
            switch (k) {
                case "Да":
                    answers_count1.set(0, v);
                    break;
                case "Скорее да, чем нет":
                    answers_count1.set(1, v);
                    break;
                case "Скорее нет, чем да":
                    answers_count1.set(2, v);
                    break;
                case "Нет":
                    answers_count1.set(3, v);
                    break;
            }
        });

        frequency2.forEach((k, v) -> {
            switch (k) {
                case "Да":
                    answers_count2.set(0, v);
                    //color2.set(0, Color.GREEN);
                    break;
                case "Скорее да, чем нет":
                    answers_count2.set(1, v);
                    //color2.set(1, Color.BLUE);
                    break;
                case "Скорее нет, чем да":
                    answers_count2.set(2, v);
                    //color2.set(2, Color.YELLOW);
                    break;
                case "Нет":
                    answers_count2.set(3, v);
                    //color2.set(3, Color.RED);
                    break;
            }
        });

        frequency3.forEach((k, v) -> {
            switch (k) {
                case "Да":
                    answers_count3.set(0, v);
                   // color3.set(0, Color.GREEN);
                    break;
                case "Скорее да, чем нет":
                    answers_count3.set(1, v);
                    //color3.set(1, Color.BLUE);
                    break;
                case "Скорее нет, чем да":
                    answers_count3.set(2, v);
                    break;
                case "Нет":
                    answers_count3.set(3, v);
                    break;
            }
        });

        ArrayList<ArrayList<Long>> arrayListCounts = new ArrayList<ArrayList<Long>>();
        ArrayList<Map<String, Integer>> colors = new ArrayList<Map<String, Integer>>();
        arrayListCounts.add(answers_count1);
        arrayListCounts.add(answers_count2);
        arrayListCounts.add(answers_count3);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.polls_diagram_item, R.id.pollsDiagramTV, varList) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                PieDataSet pieDataSet;
                View view = convertView;
                if (view == null) {
                    LayoutInflater vi = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    view = vi.inflate(R.layout.polls_diagram_item, null);
                }
                TextView tv = (TextView) view.findViewById(R.id.pollsDiagramTV);
                PieChart pieChart = view.findViewById(R.id.pieChart);

                tv.setText(varList.get(position));

                ArrayList<PieEntry> yEntrys = new ArrayList<>();
                ArrayList<String> xEntrys = new ArrayList<>();

                ArrayList<ArrayList<Integer>> colors1 = new ArrayList<ArrayList<Integer>>();
                ArrayList<ArrayList<Integer>> colors2 = new ArrayList<ArrayList<Integer>>(4);
                colors1.add(new ArrayList<>());
                colors1.add(new ArrayList<>());
                colors1.add(new ArrayList<>());
                colors2.add(new ArrayList<>());
                colors2.add(new ArrayList<>());
                colors2.add(new ArrayList<>());
                colors1.get(position).add(Color.GREEN);
                colors1.get(position).add(Color.BLUE);
                colors1.get(position).add(Color.YELLOW);
                colors1.get(position).add(Color.RED);


                for (int i = 0; i < arrayListCounts.get(position).size(); i++) {
                    if (arrayListCounts.get(position).get(i) != 0) {
                        yEntrys.add(new PieEntry(arrayListCounts.get(position).get(i), i));
                        if(arrayListCounts.get(position).get(i) != 0l)
                        colors2.get(position).add(colors1.get(position).get(i));
                    }
                }

                //xEntrys.addAll(answers_var1);

                pieDataSet = new PieDataSet(yEntrys, "Ответы");

                pieDataSet.setSliceSpace(2);
                pieDataSet.setValueTextSize(18);

               pieDataSet.setColors(colors2.get(position));

                Legend legend = pieChart.getLegend();
                legend.setForm(Legend.LegendForm.CIRCLE);
                legend.setTextSize(10);

                PieData pieData = new PieData(pieDataSet);
                Description description = new Description();
                description.setText("Ответы пользователей на анкету");
                pieChart.setDescription(description);
                pieChart.setRotationEnabled(false);
                pieChart.setHoleRadius(0);
                pieChart.setTransparentCircleAlpha(0);
                pieChart.setData(pieData);
                pieChart.invalidate();

                pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                    @Override
                    public void onValueSelected(Entry e, Highlight h) {
                        String a = e.toString();
                        Long b = Long.getLong(a);
                        Toast.makeText(getContext(), a, Toast.LENGTH_SHORT).show();
                        for (int i = 0; i < answers_count1.size(); i++) {
                            if (answers_count1.get(i).equals(b)) {
                                Toast.makeText(getContext(), answers_values[i] + " " + "Количество ответов: " + a, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onNothingSelected() {

                    }
                });

                return view;
            }
        };
        binding.listViewDiagram.setAdapter(arrayAdapter);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
