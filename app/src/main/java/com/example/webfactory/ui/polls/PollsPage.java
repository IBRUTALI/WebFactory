package com.example.webfactory.ui.polls;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.webfactory.R;
import com.example.webfactory.databinding.ActivityPollsPageBinding;

public class PollsPage extends AppCompatActivity {
    ActivityPollsPageBinding binding;
    String id, title, var1, var2, var3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPollsPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getAndSetIntentData();
    }



    void getAndSetIntentData(){
        if(getIntent().hasExtra("pollsId") && getIntent().hasExtra("pollsTitle")
                && getIntent().hasExtra("pollsVar1") && getIntent().hasExtra("pollsVar2") && getIntent().hasExtra("pollsVar3")){

            //Получение интента
            id = getIntent().getStringExtra("pollsId");
            title = getIntent().getStringExtra("pollsTitle");
            var1 = getIntent().getStringExtra("pollsVar1");
            var2 = getIntent().getStringExtra("pollsVar2");
            var3 = getIntent().getStringExtra("pollsVar3");

            //Установка интента
            binding.pollsPageTitle.setText(title);
            binding.pollsPageVar1.setText(var1);
            binding.pollsPageVar2.setText(var2);
            binding.pollsPageVar3.setText(var3);
        }else{
            Toast.makeText(this, "Нет данных", Toast.LENGTH_SHORT).show();
        }
    }

    private  void sendPolls(){
        // TODO: 01.03.2022 Возможность отправки результатов анкетирования с сохранением в БД 
    }
    
    private void deletePolls() {
        // TODO: 01.03.2022 Возможность удаление анкеты 
    }
}
