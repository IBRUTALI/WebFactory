package com.example.webfactory;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PollsPage extends AppCompatActivity {
    TextView pollsTitle, pollsVar1, pollsVar2, pollsVar3;
    String id, title, var1, var2, var3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_polls_page);

        pollsTitle = findViewById(R.id.pollsPageTitle);
        pollsVar1 = findViewById(R.id.pollsPageVar1);
        pollsVar2 = findViewById(R.id.pollsPageVar2);
        pollsVar3 = findViewById(R.id.pollsPageVar3);

        getAndSetIntentData();


        Button btnDeletePolls = findViewById(R.id.deletePolls);
        btnDeletePolls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletePolls();
            }
        });

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
            pollsTitle.setText(title);
            pollsVar1.setText(var1);
            pollsVar2.setText(var2);
            pollsVar3.setText(var3);
        }else{
            Toast.makeText(this, "Нет данных", Toast.LENGTH_SHORT).show();
        }
    }

    private void deletePolls() {

    }
}
