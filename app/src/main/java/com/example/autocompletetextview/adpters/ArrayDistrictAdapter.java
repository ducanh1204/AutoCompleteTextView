package com.example.autocompletetextview.adpters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.example.autocompletetextview.R;
import com.example.autocompletetextview.models.District;
import com.example.autocompletetextview.models.Province;

import java.util.ArrayList;
import java.util.List;

public class ArrayDistrictAdapter extends ArrayAdapter<District> {


    private List<District> districtList;
    public ArrayDistrictAdapter(@NonNull Context context, int resource, @NonNull List<District> objects) {
        super(context, resource, objects);
        districtList = new ArrayList<>(objects);
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        TextView tvProvince = view.findViewById(R.id.tv_value);
        District district = this.getItem(position);
        if(district!=null){
            tvProvince.setText(district.getName());
        }
        return view;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<District> mListDistrict = new ArrayList<>();
                if(constraint == null || constraint.length()==0){
                    mListDistrict.addAll(districtList);
                }else{
                    String filter = constraint.toString().toLowerCase().trim();
                    for(District district: districtList){
                        if(district.getName().toLowerCase().contains(filter)){
                            mListDistrict.add(district);
                        }
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mListDistrict;
                filterResults.count = mListDistrict.size();
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                clear();
                addAll((List<District>)results.values);
                notifyDataSetInvalidated();
            }

            @Override
            public CharSequence convertResultToString(Object resultValue) {
                return ((District)resultValue).getName();
            }
        };
    }
}
