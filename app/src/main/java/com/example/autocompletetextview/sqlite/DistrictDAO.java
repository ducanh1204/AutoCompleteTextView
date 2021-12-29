package com.example.autocompletetextview.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.autocompletetextview.models.District;

import java.util.ArrayList;
import java.util.List;

public class DistrictDAO {

    private MySqliteOpenHelper mySqliteOpenHelper;

    public DistrictDAO(Context context) {
        this.mySqliteOpenHelper = new MySqliteOpenHelper(context);
    }

    private String TABLE_NAME = "district";
    private String ID = "id";
    private String NAME = "name";
    private String TYPE = "type";
    private String PROVINCE_ID = "province_id";

    public List<District> getByProvince(String provinceId) {
        List<District> districtList = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = mySqliteOpenHelper.getReadableDatabase();

        String SQL = "SELECT * FROM " + TABLE_NAME + " WHERE " + PROVINCE_ID + "=?";

        Cursor cursor = sqLiteDatabase.rawQuery(SQL, new String[]{provinceId});
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    District district = new District();
                    district.setId(cursor.getString(cursor.getColumnIndex(ID)));
                    district.setName(cursor.getString(cursor.getColumnIndex(NAME)));
                    district.setType(cursor.getString(cursor.getColumnIndex(TYPE)));
                    district.setProvinceId(cursor.getString(cursor.getColumnIndex(PROVINCE_ID)));
                    districtList.add(district);
                    cursor.moveToNext();

                }
                cursor.close();
            }
        }
        return districtList;
    }
}
