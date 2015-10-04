package edu.regis.msse655.annotatedbibliography.service;

import android.content.Context;
import android.util.Log;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import edu.regis.msse655.annotatedbibliography.model.Reference;
import edu.regis.msse655.annotatedbibliography.model.ReferenceFilter;

/**
 * An IReferenceService implementation that persists references in a serialized data file.
 */
public class ReferenceServiceSioImpl implements IReferenceService {

    private static final String FILENAME = "ReferenceList.sio";
    private final String filename;
    private List<Reference> referenceList;
    private Context context;

    public ReferenceServiceSioImpl(Context context) {
        this(context, FILENAME);
        readFile();
    }

    /**
     * A package private constructor to facility unit testing.
     * @param context
     * @param filename
     */
    ReferenceServiceSioImpl(Context context, String filename) {
        this.referenceList = null;
        this.context = context;
        this.filename = filename;
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

    private void writeFile() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(context.openFileOutput(filename, Context.MODE_PRIVATE));
            oos.writeObject(referenceList);
            oos.flush();
            oos.close();
        } catch (Exception e) {
            Log.e("ReferenceServiceSioImpl", e.getMessage());
        }
    }

    /**
     * A kind of an ugly way to generate a unique id.
     * @return
     */
    private int findMaxId() {
        int id = 0;
        for (Reference reference : referenceList) {
            id = Math.max(id, reference.getId());
        }
        return id;
    }

    /**
     * Sorts the list of references and returns a new list with only the two most recent references.
     * @return
     */
    private List<Reference> filterRecent() {
        List<Reference> sortedReferenceList = new ArrayList(referenceList);

        Collections.sort(sortedReferenceList, new Comparator<Reference>() {
            @Override
            public int compare(Reference lhsReference, Reference rhsReference) {
                long lhs = lhsReference.getDateModified();
                long rhs = rhsReference.getDateModified();
                return lhs > rhs ? -1 : (lhs == rhs ? 0 : 1);
            }
        });
        return Arrays.asList(sortedReferenceList.get(0), sortedReferenceList.get(1));
    }

    private List<Reference> filterFavorites() {
        List<Reference> favorites = new ArrayList<>();
        for (Reference reference : referenceList) {
            if (reference.isFavorite()) {
                favorites.add(reference);
            }
        }
        return favorites;
    }

    @Override
    public List<Reference> retrieveReferences(ReferenceFilter filter) {
        // TODO: implement recent and favorites flags in the reference.
        switch(filter) {
            case RECENT:
                // this version of the service returns the 2 most recently modified References for RECENT
                return Collections.unmodifiableList(filterRecent());
            case FAVORITES:
                return Collections.unmodifiableList(filterFavorites());
            case ALL:
            default:
                return Collections.unmodifiableList(referenceList);
        }
    }

    @Override
    public List<Reference> retrieveAllReferences() {
        return retrieveReferences(ReferenceFilter.ALL);
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

    @Override
    public Reference create(Reference reference) {
        // assume reference is not already in the list
        int newId = findMaxId() + 1;
        reference.setId(newId); // assign a unique id
        reference.setDateModified(System.currentTimeMillis());
        referenceList.add(reference);
        writeFile();
        return reference;
    }

    @Override
    public Reference update(Reference reference) {
        // find the existing reference and replace it.
        for (int i = 0; i < referenceList.size(); i++) {
            Reference target = referenceList.get(i);
            if (target.getId() == reference.getId()) {
                reference.setDateModified(System.currentTimeMillis());
                referenceList.set(i, reference);
                writeFile();
                break;
            }
        }
        return reference;
    }

    @Override
    public Reference delete(Reference reference) {
        // find the existing reference and remove it.
        for (int i = 0; i < referenceList.size(); i++) {
            Reference target = referenceList.get(i);
            if (target.getId() == reference.getId()) {
                referenceList.remove(i);
                writeFile();
                break;
            }
        }
        return reference;
    }
}
