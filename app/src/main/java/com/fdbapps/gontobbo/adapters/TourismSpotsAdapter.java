package com.fdbapps.gontobbo.adapters;


import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.annotation.LayoutRes;
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
import com.fdbapps.gontobbo.models.TourismSpot;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class TourismSpotsAdapter extends ArrayAdapter<TourismSpot> implements Filterable{

    private Context context;
    private ArrayList<TourismSpot> spots;
    private ArrayList<TourismSpot> originalSpots;

    private class TourismSpotFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if(constraint == null || constraint.length() == 0) {
                // no filter applied
                results.values = originalSpots;
                results.count = originalSpots.size();
            }
            else {
                // perform filter logic
                ArrayList<TourismSpot> newSpots = new ArrayList<>();
                spots = originalSpots;
                for(TourismSpot spot : spots) {
                    if(spot.getTitle().toUpperCase().contains(constraint.toString().toUpperCase()))
                        newSpots.add(spot);
                }

                results.values = newSpots;
                results.count = newSpots.size();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            // Now we have to inform the adapter about the new list filtered
            if (results.count == 0)
                notifyDataSetInvalidated();
            else {
                spots = (ArrayList<TourismSpot>) results.values;
                notifyDataSetChanged();
            }
        }
    }

    private TourismSpotFilter spotsFilter;

    @Override
    //implementation of filterable interface
    public Filter getFilter() {
        if (spotsFilter == null)
            spotsFilter = new TourismSpotFilter();
        return spotsFilter;
    }
  //constructor
    public TourismSpotsAdapter(ArrayList<TourismSpot> spots, Context context) {
        super(context, R.layout.tourism_list_item, spots);

        this.context = context;
        this.spots = spots;
        this.originalSpots = spots;

    }

    private class ViewHolder {
        public ImageView ivSpot;
        public TextView tvSpotName;
        public TextView tvSpotLocation;
    }
//for list view
    @Override
    public int getCount() {
        return spots == null ? 0 : spots.size();//return super.getCount();
    }

    @Nullable
    @Override
    public TourismSpot getItem(int position) {
        return spots.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        TourismSpot spot = spots.get(position);
        ViewHolder viewHolder;

        final View result;

        if(convertView == null) {

            viewHolder = new ViewHolder();
            //for binding custom layout
            LayoutInflater inflater = LayoutInflater.from(getContext());

            convertView = inflater.inflate(R.layout.tourism_list_item, parent, false);
       //for initialling
            viewHolder.ivSpot = (ImageView) convertView.findViewById(R.id.ivSpot);
            viewHolder.tvSpotName = (TextView) convertView.findViewById(R.id.tvSpotName);
            viewHolder.tvSpotLocation = (TextView) convertView.findViewById(R.id.tvSpotLocation);

            result = convertView;
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        //int imageId = parent.getResources().getIdentifier(spot.getImageName(), "drawable", parent.getContext().getPackageName());

        viewHolder.ivSpot.setImageBitmap(BitmapFactory.decodeByteArray(spot.getImageName(), 0, spot.getImageName().length));

        viewHolder.tvSpotName.setText(spot.getTitle());
        viewHolder.tvSpotLocation.setText(spot.getAddress());

        return convertView;
    }
}
