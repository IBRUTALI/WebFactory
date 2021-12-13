package com.example.webfactory;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PollsPage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_polls_page);

        TextView pollsTitle = findViewById(R.id.pollsPageTitle);
        TextView pollsVar1 = findViewById(R.id.pollsPageVar1);
        TextView pollsVar2 = findViewById(R.id.pollsPageVar2);
        TextView pollsVar3 = findViewById(R.id.pollsPageVar3);

        pollsTitle.setText(getIntent().getStringExtra("pollsTitle"));
        pollsVar1.setText(getIntent().getStringExtra("pollsVar1"));
        pollsVar2.setText(getIntent().getStringExtra("pollsVar2"));
        pollsVar3.setText(getIntent().getStringExtra("pollsVar3"));

        Button btnDeletePolls = findViewById(R.id.deletePolls);
        btnDeletePolls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletePolls();
            }
        });

    }

    private void deletePolls() {

    }
}
