package edu.regis.msse655.annotatedbibliography.service;

import java.util.List;

import edu.regis.msse655.annotatedbibliography.model.Reference;

/**
 * A data repository that provides persistence for Reference objects.
 */
public interface ReferenceService {

    /**
     * Retrieves every Reference object stored in the System.
     * @return a list of all Reference objects in the system.
     */
    List<Reference> retrieveAllReferences();

    /**
     * Retrieves the nth Reference object in the system.
     * @param index
     * @return The requested Reference object.
     */
    Reference retrieveReference(int index); // TODO: consider using an ID instead of an index.
}
