package com.fdbapps.gontobbo.adapters;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.fdbapps.gontobbo.R;
import com.fdbapps.gontobbo.models.Hotel;

import java.util.ArrayList;

public class HotelsAdapter extends ArrayAdapter<Hotel> implements Filterable{

    private Context context;
    private ArrayList<Hotel> hotels;

    private ArrayList<Hotel> originalList;

    private class HotelFilter extends Filter {

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
                ArrayList<Hotel> newList = new ArrayList<>();
                hotels = originalList;
                for(Hotel hotel : hotels) {
                    if(hotel.getTitle().toUpperCase().contains(constraint.toString().toUpperCase()))
                        newList.add(hotel);
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
                hotels = (ArrayList<Hotel>) results.values;
                notifyDataSetChanged();
            }
        }
    }

    private HotelFilter hotelFilter;

    @Override
    public Filter getFilter() {
        if (hotelFilter == null)
            hotelFilter = new HotelFilter();
        return hotelFilter;
    }

    @Override
    public int getCount() {
        return hotels == null ? 0 : hotels.size();
    }

    @Nullable
    @Override
    public Hotel getItem(int position) {
        return hotels.get(position);
    }


    public HotelsAdapter(ArrayList<Hotel> spots, Context context) {
        super(context, R.layout.tourism_list_item, spots);

        this.context = context;
        this.originalList = spots;
        this.hotels = spots;

    }

    private class ViewHolder {
        public ImageView ivSpot;
        public TextView tvSpotName;
        public TextView tvSpotLocation;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Hotel hotel = hotels.get(position);
        HotelsAdapter.ViewHolder viewHolder;

        final View result;

        if(convertView == null) {

            viewHolder = new HotelsAdapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());

            convertView = inflater.inflate(R.layout.tourism_list_item, parent, false);

            viewHolder.ivSpot = (ImageView) convertView.findViewById(R.id.ivSpot);
            viewHolder.tvSpotName = (TextView) convertView.findViewById(R.id.tvSpotName);
            viewHolder.tvSpotLocation = (TextView) convertView.findViewById(R.id.tvSpotLocation);

            result = convertView;
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (HotelsAdapter.ViewHolder) convertView.getTag();
            result = convertView;
        }

       // int imageId = parent.getResources().getIdentifier(hotel.getImageName(), "drawable", parent.getContext().getPackageName());
        viewHolder.ivSpot.setImageBitmap(BitmapFactory.decodeByteArray(hotel.getImageName(), 0, hotel.getImageName().length));

        viewHolder.tvSpotName.setText(hotel.getTitle());
        viewHolder.tvSpotLocation.setText(hotel.getAddress());

        return convertView;
    }

}
