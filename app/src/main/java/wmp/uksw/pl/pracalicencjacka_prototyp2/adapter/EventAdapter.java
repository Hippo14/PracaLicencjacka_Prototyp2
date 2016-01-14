package wmp.uksw.pl.pracalicencjacka_prototyp2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import wmp.uksw.pl.pracalicencjacka_prototyp2.R;
import wmp.uksw.pl.pracalicencjacka_prototyp2.models.EventRow;

/**
 * Created by MSI on 2016-01-14.
 */
public class EventAdapter extends ArrayAdapter<EventRow> {

    public EventAdapter(Context context, List<EventRow> list) {
        super(context, 0, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        EventRow eventRow = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.event_adapter, parent, false);

        TextView eventName = (TextView) convertView.findViewById(R.id.eventName);
        TextView eventDescription = (TextView) convertView.findViewById(R.id.eventDescription);

        eventName.setText(eventRow.name);
        eventDescription.setText(eventRow.description);

        return convertView;
    }

}
