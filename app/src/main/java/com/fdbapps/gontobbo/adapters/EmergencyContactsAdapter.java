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
import com.fdbapps.gontobbo.models.EmergencyContact;
import com.fdbapps.gontobbo.models.Hospital;

import java.util.ArrayList;

public class EmergencyContactsAdapter extends ArrayAdapter<EmergencyContact> implements Filterable {

    private Context context;
    private ArrayList<EmergencyContact> contacts;


    private ArrayList<EmergencyContact> originalList;

    private class ContactFilter extends Filter {

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
                ArrayList<EmergencyContact> newList = new ArrayList<>();
                contacts = originalList;
                for(EmergencyContact contact : contacts) {
                    if(contact.getName().toUpperCase().contains(constraint.toString().toUpperCase()))
                        newList.add(contact);
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
                contacts = (ArrayList<EmergencyContact>) results.values;
                notifyDataSetChanged();
            }
        }
    }

    private ContactFilter contactFilter;

    @Override
    public Filter getFilter() {
        if (contactFilter == null)
            contactFilter = new ContactFilter();
        return contactFilter;
    }

    @Override
    public int getCount() {
        return contacts == null ? 0 : contacts.size();
    }

    @Nullable
    @Override
    public EmergencyContact getItem(int position) {
        return contacts.get(position);
    }

    public EmergencyContactsAdapter(ArrayList<EmergencyContact> contacts, Context context) {
        super(context, R.layout.hospital_list_item, contacts);

        this.context = context;
        this.contacts = contacts;
        this.originalList = contacts;

    }

    private class ViewHolder {
        public TextView tvHospitalTitle;
        public TextView tvHospitalAddress;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        EmergencyContact contact = contacts.get(position);
        EmergencyContactsAdapter.ViewHolder viewHolder;

        final View result;

        if(convertView == null) {

            viewHolder = new EmergencyContactsAdapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());

            convertView = inflater.inflate(R.layout.hospital_list_item, parent, false);

            viewHolder.tvHospitalTitle = (TextView) convertView.findViewById(R.id.tvHospitalTitle);
            viewHolder.tvHospitalAddress = (TextView) convertView.findViewById(R.id.tvHospitalAddress);

            result = convertView;
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (EmergencyContactsAdapter.ViewHolder) convertView.getTag();
            result = convertView;
        }
        viewHolder.tvHospitalTitle.setText(contact.getName());
        viewHolder.tvHospitalAddress.setText(contact.getPhoneNumber());

        return convertView;
    }

}
