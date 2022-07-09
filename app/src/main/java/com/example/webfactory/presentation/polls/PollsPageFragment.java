package com.example.webfactory.presentation.polls;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.data.databases.DBHelperReview;
import com.example.webfactory.R;
import com.example.webfactory.databinding.FragmentPollsPageBinding;

import java.util.ArrayList;

public class PollsPageFragment extends Fragment {
    private FragmentPollsPageBinding binding;
    private ArrayList<String> varList;
    private String id, title, var1, var2, var3;
    private NavController navController;
    private String[] checkList = new String[3];


    public PollsPageFragment() {
        super(R.layout.fragment_polls_page);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPollsPageBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        getAndSetIntentData();

        binding.sendPolls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendPolls();
            }
        });
        return root;
    }


    void getAndSetIntentData() {
        if (getArguments() != null) {

            id = getArguments().getString("pollsId");
            title = getArguments().getString("pollsTitle");
            var1 = getArguments().getString("pollsVar1");
            var2 = getArguments().getString("pollsVar2");
            var3 = getArguments().getString("pollsVar3");

            binding.pollsPageTitle.setText(title);
            varList = new ArrayList<>();
            varList.add(var1);
            varList.add(var2);
            varList.add(var3);
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.polls_list_item, R.id.pollsPageTVItem, varList) {
                int selectedPosition = 0;

                @NonNull
                @Override
                public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                    View v = convertView;
                    if (v == null) {
                        LayoutInflater vi = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        v = vi.inflate(R.layout.polls_list_item, null);
                    }
                    TextView tv = (TextView) v.findViewById(R.id.pollsPageTVItem);
                    tv.setText(varList.get(position));
                    RadioGroup radioGroup = v.findViewById(R.id.radioGroup1);
                    View finalV = v;
                    radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(RadioGroup group, int checkedId) {
                            RadioButton radioButton = finalV.findViewById(checkedId);
                            checkList[position] = (String) radioButton.getText();
                        }
                    });
                    return v;
                }
            };
            binding.pollsPageLV.setAdapter(arrayAdapter);
        } else {
            Toast.makeText(getContext(), "Нет данных", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendPolls() {
        String a1 = title;
        String a2 = var1;
        String a3 = checkList[0];
        String a4 = var2;
        String a5 = checkList[1];
        String a6 = var3;
        String a7 = checkList[2];
        if (a3 != null && a5 != null && a7 != null && !a3.isEmpty() && !a5.isEmpty() && !a7.isEmpty()) {
            DBHelperReview myDB = new DBHelperReview(getContext());
            myDB.addPollsAnswerDB(a1, a2, a3, a4, a5, a6, a7);
            navController.popBackStack();
        } else {
            Toast.makeText(getContext(), "Заполните анкету!", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
