package wmp.uksw.pl.pracalicencjacka_prototyp2.adapter;

import android.content.Context;
import android.location.Address;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import wmp.uksw.pl.pracalicencjacka_prototyp2.R;

/**
 * Created by MSI on 2016-01-27.
 */
public class SearchAdapter extends ArrayAdapter<Address> {

    public SearchAdapter(Context context, List<Address> list) {
        super(context, 0, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Address address = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.search_adapter, parent, false);

        TextView tvCityName = (TextView) convertView.findViewById(R.id.tvCityName);
        TextView tvCountryName = (TextView) convertView.findViewById(R.id.tvCountryName);

        tvCityName.setText(address.getAddressLine(0));
        tvCountryName.setText(address.getAddressLine(1));

        return convertView;
    }

}
