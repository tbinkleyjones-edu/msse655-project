package edu.regis.msse655.annotatedbibliography;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import edu.regis.msse655.annotatedbibliography.components.ReferenceArrayAdapter;
import edu.regis.msse655.annotatedbibliography.model.Reference;
import edu.regis.msse655.annotatedbibliography.service.ReferenceService;
import edu.regis.msse655.annotatedbibliography.service.ServiceLocator;

/**
 * A fragment that display a list of Reference objects. When an item is selected,
 * an Intent is sent to start a ReferenceActivity.
 */
public class ReferenceListFragment extends ListFragment {

    public ReferenceListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_reference_list, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ReferenceService service = ServiceLocator.getReferenceService();

        // TODO: use a custom adapter that builds a view tailored for the reference.
        setListAdapter(new ReferenceArrayAdapter(
                getActivity(),
                service.retrieveAllReferences()));
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent(v.getContext(), ReferenceActivity.class);
        intent.putExtra("index", position); // TODO: use an id?

        startActivity(intent);
    }
}
