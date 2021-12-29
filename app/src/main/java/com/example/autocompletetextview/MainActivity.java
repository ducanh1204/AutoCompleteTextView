package com.example.autocompletetextview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.example.autocompletetextview.adpters.ArrayDistrictAdapter;
import com.example.autocompletetextview.adpters.ArrayProvinceAdapter;
import com.example.autocompletetextview.models.District;
import com.example.autocompletetextview.models.Province;
import com.example.autocompletetextview.sqlite.DistrictDAO;
import com.example.autocompletetextview.sqlite.MySqliteOpenHelper;
import com.example.autocompletetextview.sqlite.ProvinceDAO;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DistrictDAO districtDAO;
    private ProvinceDAO provinceDAO;
    private List<Province> provinceList;
    private List<District> districtList;
    private String currentProvinceId="";
    private String currentProvinceName = "";
    private AutoCompleteTextView autoCompleteTextView,autoCompleteTextView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        autoCompleteTextView = findViewById(R.id.auto_complete);
        autoCompleteTextView2 = findViewById(R.id.auto_complete_2);
        MySqliteOpenHelper mySqliteOpenHelper = new MySqliteOpenHelper(this);
        mySqliteOpenHelper.createDataBase();
        districtDAO = new DistrictDAO(this);
        provinceDAO = new ProvinceDAO(this);
        provinceList = provinceDAO.getAll();
        ArrayProvinceAdapter arrayProvinceAdapter = new ArrayProvinceAdapter(this,R.layout.item,provinceList);
        autoCompleteTextView.setAdapter(arrayProvinceAdapter);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                autoCompleteTextView2.setText("");
                String result = autoCompleteTextView.getText().toString();
                for (int i=0;i<provinceList.size();i++){
                    if(provinceList.get(i).getName().equals(result)){
                        currentProvinceId = provinceList.get(i).getId();
                        viewDistrict();
                    }
                }
            }
        });
    }
    private void viewDistrict(){
        districtList = districtDAO.getByProvince(currentProvinceId);
        ArrayDistrictAdapter arrayDistrictAdapter = new ArrayDistrictAdapter(this,R.layout.item,districtList);
        autoCompleteTextView2.setAdapter(arrayDistrictAdapter);

    }
}