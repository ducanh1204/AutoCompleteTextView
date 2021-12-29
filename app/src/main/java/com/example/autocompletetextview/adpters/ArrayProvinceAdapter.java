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
import com.example.autocompletetextview.models.Province;

import java.util.ArrayList;
import java.util.List;

public class ArrayProvinceAdapter extends ArrayAdapter<Province> {

    private List<Province> provinceList;


    public ArrayProvinceAdapter(@NonNull Context context, int resource, @NonNull List<Province> objects) {
        super(context, resource, objects);
        provinceList = new ArrayList<>(objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        TextView tvProvince = view.findViewById(R.id.tv_value);
        Province province = this.getItem(position);
        if(province!=null){
            tvProvince.setText(province.getName());
        }
        return view;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<Province> mListProvince = new ArrayList<>();
                if(constraint == null || constraint.length()==0){
                    mListProvince.addAll(provinceList);
                }else{
                    String filter = constraint.toString().toLowerCase().trim();
                    for(Province province: provinceList){
                        if(province.getName().toLowerCase().contains(filter)){
                            mListProvince.add(province);
                        }
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mListProvince;
                filterResults.count = mListProvince.size();
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                clear();
                addAll((List<Province>)results.values);
                notifyDataSetInvalidated();
            }

            @Override
            public CharSequence convertResultToString(Object resultValue) {
                return ((Province)resultValue).getName();
            }
        };
    }
}
