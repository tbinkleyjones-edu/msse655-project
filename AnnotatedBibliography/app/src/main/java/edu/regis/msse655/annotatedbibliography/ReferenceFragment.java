package edu.regis.msse655.annotatedbibliography;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import edu.regis.msse655.annotatedbibliography.model.Reference;
import edu.regis.msse655.annotatedbibliography.service.ServiceLocator;

/**
 * A fragment used to display a Reference object.
 *
 * The index of the Reference to display must be passed via the parent Activity's Intent, using
 * an extras key of "index" contain a integer value.
 *
 * The fragment also launches a web browser via an ACTION_VIEW Intent passing a the Reference's
 * Url property, or an Url constructed using the Reference's DOI property.
 */
public class ReferenceFragment extends Fragment {

    public ReferenceFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Intent intent = getActivity().getIntent();
        int index = intent.getIntExtra("index", -1);

        final Reference reference = ServiceLocator.getReferenceService().retrieveReference(index);

        View rootView = inflater.inflate(R.layout.fragment_reference, container, false);
        ((TextView)rootView.findViewById(R.id.textViewReferenceTitle)).setText(reference.getReferenceTitle());
        ((TextView)rootView.findViewById(R.id.textViewMediaTitle)).setText(reference.getMediaTitle());
        ((TextView)rootView.findViewById(R.id.textViewAuthors)).setText(reference.getAuthors());
        ((TextView)rootView.findViewById(R.id.textViewAbstract)).setText(reference.getReferenceAbstract());
        ((TextView)rootView.findViewById(R.id.textViewNotes)).setText(reference.getNotes());
        ((TextView)rootView.findViewById(R.id.textViewUrl)).setText(reference.getUrl());
        ((TextView)rootView.findViewById(R.id.textViewDoi)).setText(reference.getDoi());

        Button urlButton = (Button)rootView.findViewById(R.id.buttonUrl);
        urlButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReferenceFragment.this.launchUrl(reference.getUrl());
            }
        });

        Button doiButton = (Button)rootView.findViewById(R.id.buttonDoi);
        doiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // DOI urls are constructed by prepending doi.org to the DOI number. See doi.org for details.
                ReferenceFragment.this.launchUrl("https://doi.org/" + reference.getDoi());
            }
        });

        // TODO: change the content of the ImageView based on the TypeOfMedia property.

        return rootView;
    }

    private void launchUrl(String url) {
        Intent webpage = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(webpage);
    }

}
