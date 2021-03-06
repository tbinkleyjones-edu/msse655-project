package edu.regis.msse655.annotatedbibliography;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import edu.regis.msse655.annotatedbibliography.components.TypeOfMediaView;
import edu.regis.msse655.annotatedbibliography.model.Reference;
import edu.regis.msse655.annotatedbibliography.service.ServiceLocator;

/**
 * A fragment used to display a Reference object.
 *
 * The index of the Reference to display must be passed via the parent Activity's Intent, using
 * an extras key of "id" contain a integer value.
 *
 * The fragment also launches a web browser via an ACTION_VIEW Intent passing a the Reference's
 * Url property, or an Url constructed using the Reference's DOI property.
 */
public class ReferenceActivityFragment extends Fragment {

    private Reference reference;

    public ReferenceActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Intent intent = getActivity().getIntent();
        long id = intent.getLongExtra("id", -1);

        reference = ServiceLocator.getReferenceService().retrieveReference(id);

        View rootView = inflater.inflate(R.layout.fragment_reference, container, false);
        ((TypeOfMediaView)rootView.findViewById(R.id.imageViewMediaType)).setTypeOfMedia(reference.getTypeOfMedia());
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
                ReferenceActivityFragment.this.launchUrl(reference.getUrl());
            }
        });

        Button doiButton = (Button)rootView.findViewById(R.id.buttonDoi);
        doiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // DOI urls are constructed by prepending doi.org to the DOI number. See doi.org for details.
                ReferenceActivityFragment.this.launchUrl("https://doi.org/" + reference.getDoi());
            }
        });

        // TODO: change the content of the ImageView based on the TypeOfMedia property.

        return rootView;
    }

    private void launchUrl(String url) {
        Intent webpage = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(webpage);
    }

    public void favoriteReference() {
        reference.setFavorite(!reference.isFavorite());
        reference = ServiceLocator.getReferenceService().update(reference);
    }

    public void deleteReference() {
        reference = ServiceLocator.getReferenceService().delete(reference);
    }
}
