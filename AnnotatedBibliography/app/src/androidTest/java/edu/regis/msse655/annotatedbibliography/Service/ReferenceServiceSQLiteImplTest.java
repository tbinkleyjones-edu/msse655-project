package edu.regis.msse655.annotatedbibliography.service;

import android.app.Application;
import android.content.Context;
import android.test.ApplicationTestCase;
import android.test.suitebuilder.annotation.MediumTest;

import java.util.List;

import edu.regis.msse655.annotatedbibliography.model.Reference;
import edu.regis.msse655.annotatedbibliography.model.ReferenceFilter;

/**
 * A test case to verify ReferenceServiceSioImpl.
 */
public class ReferenceServiceSQLiteImplTest extends ApplicationTestCase<Application> {

    // use an in memory database for each run to avoid deleting production data, and to avoid side effects between test runs.
    private static class ReferenceServiceInMemorySQLiteImpl extends ReferenceServiceSQLiteImpl {
        protected ReferenceServiceInMemorySQLiteImpl(Context context) {
            super(context, null);
        }
    }

    public ReferenceServiceSQLiteImplTest() {
        super(Application.class);
    }

    /**
     * Verify the service creates, stores, updates, and deletes reference objects.
     *
     * @throws Exception
     */
    @MediumTest
    public void testServiceOperations() throws Exception {

        List<Reference> testReferences = DemoDataGenerator.createReferences();

        // multiple service instances are used in this test to confirm that the service is not
        // just reading objects from memory.
        ReferenceServiceSQLiteImpl serviceInstance = new ReferenceServiceInMemorySQLiteImpl(getContext());
        assertEquals(0, serviceInstance.retrieveAllReferences().size());  // expect to be empty initially

        Reference reference1 = testReferences.get(0);
        assertEquals(-1, reference1.getId()); // confirm the reference does not have an id yet.
        serviceInstance.create(reference1);
        long createTime = reference1.getDateModified();

        assertEquals(1, serviceInstance.retrieveAllReferences().size());  // expected to contain 1 reference
        assertTrue(0 != reference1.getId()); // confirm the reference was assigned a new id.
        assertTrue(createTime > Long.MIN_VALUE);

        // confirm that reference1 was persisted
        List<Reference> referenceList2 = serviceInstance.retrieveAllReferences();
        assertEquals(1, referenceList2.size());  // expected to contain 1 reference

        Reference reference2 = referenceList2.get(0);
        assertFalse(reference1 == reference2);  // shouldn't be the same java object
        assertEquals(reference1, reference2);   // but should be equal

        // try again with just the reference id
        reference2 = serviceInstance.retrieveReference(reference1.getId());
        assertFalse(reference1 == reference2);  // shouldn't be the same java object
        assertEquals(reference1, reference2);   // but should be equal

        // add a few other references to confirm more than one item is persisted
        for (int i = 1; i < testReferences.size(); i++) {
            serviceInstance.create(testReferences.get(i));
        }

        assertEquals(testReferences.size(), serviceInstance.retrieveAllReferences().size());  // expected to contain all test references
        reference2 = null;
        referenceList2 = null;

        // confirm a reference can be updated
        List<Reference> referenceList3 = serviceInstance.retrieveAllReferences();
        assertEquals(testReferences.size(), referenceList3.size());  // expected to contain all test references

        reference1.setAuthors("new authors");
        serviceInstance.update(reference1);

        List<Reference> referenceList4 = serviceInstance.retrieveAllReferences();
        assertEquals(testReferences.size(), referenceList4.size());  // expected to contain all test references

        Reference reference4 = referenceList4.get(0);
        assertEquals("new authors", reference4.getAuthors());
        long updateTime = reference1.getDateModified();
        assertTrue(updateTime > createTime);

        // confirm a reference can be deleted
        serviceInstance.delete(reference4);
        referenceList4 = null;
        reference4 = null;

        List<Reference> referenceList5 = serviceInstance.retrieveAllReferences();
        assertEquals(testReferences.size() - 1, referenceList5.size());  // expected to contain all test references

        for (int i = 0; i < referenceList5.size(); i++) {
            assertFalse(referenceList5.get(i).getId() == reference1.getId());
        }

        // confirm clear deletes all references
        serviceInstance.clear();
        referenceList5 = null;

        assertEquals(0, serviceInstance.retrieveAllReferences().size());  // expect to be empty
    }

    /**
     * Verify service filtering.
     *
     * @throws Exception
     */
    @MediumTest
    public void testServiceFiltering() throws Exception {

        List<Reference> testReferences = DemoDataGenerator.createReferences();

        ReferenceServiceSQLiteImpl serviceInstance = new ReferenceServiceInMemorySQLiteImpl(getContext());

        // add all the test references
        for (int i = 0; i < testReferences.size(); i++) {
            serviceInstance.create(testReferences.get(i));
            // sleep for a tiny bit so that each reference gets a different time stamp.
            Thread.sleep(1);
        }
        assertEquals(4, serviceInstance.retrieveAllReferences().size());

        // by default the demo data has two favorites.
        assertEquals(2, serviceInstance.retrieveReferences(ReferenceFilter.FAVORITES).size());

        // the two recent reference should be the last two references in the test list.
        List<Reference> recentReferences = serviceInstance.retrieveReferences(ReferenceFilter.RECENT);
        assertEquals(2, recentReferences.size());
        assertEquals(testReferences.get(3), recentReferences.get(0));
        assertEquals(testReferences.get(2), recentReferences.get(1));

        // favorite all of the references
        for (Reference reference : testReferences) {
            reference.setFavorite(true);
            serviceInstance.update(reference);
        }

        assertEquals(4, serviceInstance.retrieveReferences(ReferenceFilter.FAVORITES).size());

        // favorite non of the references
        for (Reference reference : testReferences) {
            reference.setFavorite(false);
            serviceInstance.update(reference);
        }

        assertEquals(0, serviceInstance.retrieveReferences(ReferenceFilter.FAVORITES).size());

        // favorite only the first and third reference
        testReferences.get(0).setFavorite(true);
        serviceInstance.update(testReferences.get(0));
        Thread.sleep(1); // again, sleep so that the updated references do not have the same timestamp
        testReferences.get(2).setFavorite(true);
        serviceInstance.update(testReferences.get(2));

        assertEquals(2, serviceInstance.retrieveReferences(ReferenceFilter.FAVORITES).size());

        // verify that the first and third references are the two recent references.
        recentReferences = serviceInstance.retrieveReferences(ReferenceFilter.RECENT);
        assertEquals(2, recentReferences.size());
        assertEquals(testReferences.get(2), recentReferences.get(0));
        assertEquals(testReferences.get(0), recentReferences.get(1));

    }

}
