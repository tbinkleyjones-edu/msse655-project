package edu.regis.msse655.annotatedbibliography;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import edu.regis.msse655.annotatedbibliography.components.ReferenceArrayAdapter;
import edu.regis.msse655.annotatedbibliography.model.Reference;
import edu.regis.msse655.annotatedbibliography.model.ReferenceFilter;
import edu.regis.msse655.annotatedbibliography.service.DemoDataGenerator;
import edu.regis.msse655.annotatedbibliography.service.IReferenceService;
import edu.regis.msse655.annotatedbibliography.service.ServiceLocator;

/**
 * A fragment that display a list of Reference objects. When an item is selected,
 * an Intent is sent to start a ReferenceActivity.
 */
public class ReferenceListActivityFragment extends ListFragment {

    private ReferenceArrayAdapter arrayAdapter;

    public ReferenceListActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_reference_list, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        IReferenceService service = ServiceLocator.getReferenceService();

        // TODO: use a custom adapter that builds a view tailored for the reference.
        arrayAdapter = new ReferenceArrayAdapter(
                getActivity(),
                new ArrayList(service.retrieveReferences(ReferenceFilter.ALL)));
        setListAdapter(arrayAdapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        Reference reference = (Reference)getListAdapter().getItem(position);

        Intent intent = new Intent(v.getContext(), ReferenceActivity.class);
        intent.putExtra("index", reference.getId());

        startActivity(intent);
    }

    public void filterList(ReferenceFilter filter) {
        IReferenceService service = ServiceLocator.getReferenceService();
        List<Reference> references = service.retrieveReferences(filter);
        arrayAdapter.clear();
        arrayAdapter.addAll(references);
    }

    public void addDemoData() {
        IReferenceService service = ServiceLocator.getReferenceService();
        List<Reference> demoReferences = DemoDataGenerator.createReferences();
        for (Reference reference : demoReferences) {
            service.create(reference);
        }
        arrayAdapter.clear();
        arrayAdapter.addAll(service.retrieveAllReferences());
    }

    public void deleteAllReferences() {
        IReferenceService service = ServiceLocator.getReferenceService();
        service.clear();
        arrayAdapter.clear();
        arrayAdapter.addAll(service.retrieveAllReferences());
    }
}
