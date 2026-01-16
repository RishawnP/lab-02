package com.example.listycity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    ListView cityList;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> datalist;

    Button addCityButton, deleteCityButton;
    int selectedPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        cityList = findViewById(R.id.city_list);
        addCityButton = findViewById(R.id.add_city_button);
        deleteCityButton = findViewById(R.id.delete_city_button);
        String []cities = {"Edmonton", "Vancouver", "Moscow", "Sydney", "Berlin", "Vienna"};

        datalist = new ArrayList<>();
        datalist.addAll(Arrays.asList(cities));
        cityAdapter = new ArrayAdapter<>(this, R.layout.content, datalist);
        cityList.setAdapter(cityAdapter);

        cityList.setOnItemClickListener((parent, view, position, id) -> {
            selectedPosition = position;
            cityList.setItemChecked(position, true);
        });

        // ADD CITY
        addCityButton.setOnClickListener(v -> showAddCityDialog());

        // DELETE CITY
        deleteCityButton.setOnClickListener(v -> deleteSelectedCity());
    }

    private void showAddCityDialog() {
        final EditText input = new EditText(this);
        input.setHint("Enter city name");

        new AlertDialog.Builder(this)
                .setTitle("Add City")
                .setView(input)
                .setPositiveButton("CONFIRM", (dialog, which) -> {
                    String newCity = input.getText().toString().trim();
                    if (!TextUtils.isEmpty(newCity)) {
                        datalist.add(newCity);
                        cityAdapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("CANCEL", null)
                .show();
    }

    private void deleteSelectedCity() {
        if (selectedPosition >= 0 && selectedPosition < datalist.size()) {
            datalist.remove(selectedPosition);
            selectedPosition = -1;
            cityList.clearChoices();
            cityAdapter.notifyDataSetChanged();
        }
    }

}