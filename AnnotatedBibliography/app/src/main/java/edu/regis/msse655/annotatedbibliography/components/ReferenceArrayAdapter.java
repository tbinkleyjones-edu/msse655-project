package edu.regis.msse655.annotatedbibliography.components;

import android.content.Context;
import android.graphics.Typeface;
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
        Reference reference = this.getItem(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.view_reference_list_item, parent, false);

        // TypeOfMedia icons come from the Font Awesome typeface.
        TextView faIconView = (TextView) rowView.findViewById(R.id.textViewFaIcon);
        Typeface font = Typeface.createFromAsset(context.getAssets(), "fontawesome-webfont.ttf");
        faIconView.setTypeface(font);

        int typeOfMediaId;
        switch(reference.getTypeOfMedia()) {
            case BOOK:
                typeOfMediaId = R.string.icon_book;
            case MAGAZINE:
                typeOfMediaId = R.string.icon_magazine;
                break;
            case JOURNAL:
                typeOfMediaId = R.string.icon_journal;
                break;
            case WEBSITE:
                typeOfMediaId = R.string.icon_website;
                break;
            default:
                typeOfMediaId = R.string.icon_other;
                break;
        }
        faIconView.setText(context.getString(typeOfMediaId));


        TextView titleView = (TextView) rowView.findViewById(R.id.textViewTitle);
        titleView.setText(reference.getReferenceTitle());

        TextView authorsView = (TextView) rowView.findViewById(R.id.textViewAuthors);
        authorsView.setText(reference.getAuthors());



        return rowView;
    }
}
