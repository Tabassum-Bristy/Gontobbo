package com.fdbapps.gontobbo.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.fdbapps.gontobbo.R;
import com.fdbapps.gontobbo.models.Hospital;

import java.util.ArrayList;

public class HospitalsAdapter extends ArrayAdapter<Hospital> implements Filterable {

    private Context context;
    private ArrayList<Hospital> hospitals;


    private ArrayList<Hospital> originalList;

    private class HospitalFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if(constraint == null || constraint.length() == 0) {
                // no filter applied
                results.values = originalList;
                results.count = originalList.size();
            }
            else {
                // perform filter logic
                ArrayList<Hospital> newList = new ArrayList<>();
                hospitals = originalList;
                for(Hospital hospital : hospitals) {
                    if(hospital.getTitle().toUpperCase().contains(constraint.toString().toUpperCase()))
                        newList.add(hospital);
                }

                results.values = newList;
                results.count = newList.size();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            // Now we have to inform the adapter about the new list filtered
            if (results.count == 0)
                notifyDataSetInvalidated();
            else {
                hospitals = (ArrayList<Hospital>) results.values;
                notifyDataSetChanged();
            }
        }
    }

    private HospitalFilter hospitalFilter;

    @Override
    public Filter getFilter() {
        if (hospitalFilter == null)
            hospitalFilter = new HospitalFilter();
        return hospitalFilter;
    }

    @Override
    public int getCount() {
        return hospitals == null ? 0 : hospitals.size();
    }

    @Nullable
    @Override
    public Hospital getItem(int position) {
        return hospitals.get(position);
    }

    public HospitalsAdapter(ArrayList<Hospital> hospitals, Context context) {
        super(context, R.layout.hospital_list_item, hospitals);

        this.context = context;
        this.hospitals = hospitals;
        this.originalList = hospitals;

    }

    private class ViewHolder {
        public TextView tvHospitalTitle;
        public TextView tvHospitalAddress;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Hospital hospital = hospitals.get(position);
        HospitalsAdapter.ViewHolder viewHolder;

        final View result;

        if(convertView == null) {

            viewHolder = new HospitalsAdapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());

            convertView = inflater.inflate(R.layout.hospital_list_item, parent, false);

            viewHolder.tvHospitalTitle = (TextView) convertView.findViewById(R.id.tvHospitalTitle);
            viewHolder.tvHospitalAddress = (TextView) convertView.findViewById(R.id.tvHospitalAddress);

            result = convertView;
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (HospitalsAdapter.ViewHolder) convertView.getTag();
            result = convertView;
        }
        viewHolder.tvHospitalTitle.setText(hospital.getTitle());
        viewHolder.tvHospitalAddress.setText(hospital.getAddress());

        return convertView;
    }

}
