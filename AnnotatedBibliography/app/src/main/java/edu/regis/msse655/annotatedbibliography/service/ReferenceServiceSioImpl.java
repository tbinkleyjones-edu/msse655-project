package edu.regis.msse655.annotatedbibliography.service;

import android.content.Context;
import android.util.Log;

import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import edu.regis.msse655.annotatedbibliography.model.Reference;
import edu.regis.msse655.annotatedbibliography.model.ReferenceFilter;

/**
 * An IReferenceService implementation that persists references in a serialized data file.
 */
public class ReferenceServiceSioImpl implements IReferenceService {

    private final String filename = "ReferenceList.sio";
    private List<Reference> referenceList;
    private Context context;

    public ReferenceServiceSioImpl(Context context) {
        referenceList = null;
        this.context = context;
        readFile();
    }

    private void readFile() {
        try {
            ObjectInputStream ois = new ObjectInputStream(context.openFileInput(filename));
            referenceList = (List<Reference>) ois.readObject();
            ois.close();
        } catch (Exception e) {
            Log.e("ReferenceServiceSioImpl", e.getMessage());
            referenceList = new ArrayList<>();
        }
    }

    @Override
    public List<Reference> retrieveReferences(ReferenceFilter filter) {
        return referenceList;
    }

    @Override
    public Reference retrieveReference(int id) {
        for(Reference reference : referenceList) {
            if( reference.getId() == id) {
                return reference;
            }
        }
        return null;
    }
}
