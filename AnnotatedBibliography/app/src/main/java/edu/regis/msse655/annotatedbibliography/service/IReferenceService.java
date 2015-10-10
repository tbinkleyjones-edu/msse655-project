package edu.regis.msse655.annotatedbibliography.service;

import java.util.List;

import edu.regis.msse655.annotatedbibliography.model.Reference;
import edu.regis.msse655.annotatedbibliography.model.ReferenceFilter;

/**
 * A data repository that provides persistence for Reference objects.
 */
public interface IReferenceService {

    /**
     * Retrieves every Reference object stored in the System matching the ReferenceFilter.
     * @param filter
     * @return a list of Reference objects in the system.
     */
    List<Reference> retrieveReferences(ReferenceFilter filter);

    /**
     * Retrieves every Reference object stored in the System. Equivalent to retrieveReferences(ReferenceFilter.ALL)
     * @return a list of all Reference objects in the system.
     */
    List<Reference> retrieveAllReferences();

    /**
     * Retrieves a Reference object by Id.
     * @param id
     * @return The requested Reference object.
     */
    Reference retrieveReference(int id);

    /**
     * Add an unmanaged Reference object to the list of references managed by the ReferenceService.
     * The reference's modified date will be updated as part of this operation.
     * @param reference
     * @return
     */
    Reference create(Reference reference);

    /**
     * Inform the ReferenceService of changes to a managed Reference object.
     * The reference's modified date will be updated as part of this operation.
     * @param reference
     * @return
     */
    Reference update(Reference reference);

    /**
     * Remove a managed Reference object from the list of references managed by the ReferenceService
     * @param reference
     * @return
     */
    Reference delete(Reference reference);

    /**
     * Remove all managed Reference objects from the list of references managed by the ReferenceService
     */
    void clear();
}
