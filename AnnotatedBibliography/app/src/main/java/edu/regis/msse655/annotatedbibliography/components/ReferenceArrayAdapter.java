package edu.regis.msse655.annotatedbibliography.components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import edu.regis.msse655.annotatedbibliography.R;
import edu.regis.msse655.annotatedbibliography.model.Reference;

/**
 * A custom ArrayAdapter used to display Reference list items with the view_reference_list_item layout.
 */
public class ReferenceArrayAdapter extends ArrayAdapter<Reference> {


    public ReferenceArrayAdapter(Context context, List<Reference> objects) {
        super(context, R.layout.view_reference_list_item, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Context context = this.getContext();

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.view_reference_list_item, parent, false);

        TypeOfMediaView typeOfMediaView = (TypeOfMediaView) rowView.findViewById(R.id.textViewFaIcon);
        TextView titleView = (TextView) rowView.findViewById(R.id.textViewTitle);
        TextView authorsView = (TextView) rowView.findViewById(R.id.textViewAuthors);

        Reference reference = this.getItem(position);
        typeOfMediaView.setTypeOfMedia(reference.getTypeOfMedia());
        titleView.setText(reference.getReferenceTitle());
        authorsView.setText(reference.getAuthors());

        return rowView;
    }
}
