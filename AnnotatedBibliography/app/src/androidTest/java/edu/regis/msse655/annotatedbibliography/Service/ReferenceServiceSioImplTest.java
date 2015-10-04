package edu.regis.msse655.annotatedbibliography.service;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.test.suitebuilder.annotation.MediumTest;

import java.util.List;

import edu.regis.msse655.annotatedbibliography.model.Reference;
import edu.regis.msse655.annotatedbibliography.model.ReferenceFilter;

/**
 * A test case to verify ReferenceServiceSioImpl.
 */
public class ReferenceServiceSioImplTest  extends ApplicationTestCase<Application> {

    // use a unique file name for each run to avoid deleting production data, and to avoid side effects between test runs.
    String testFilename;

    public ReferenceServiceSioImplTest() {
        super(Application.class);
        this.testFilename = "test-file-" + System.currentTimeMillis();
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        getContext().deleteFile(testFilename);
    }

    @Override
    protected void tearDown() throws Exception {
        getContext().deleteFile(testFilename);
        super.tearDown();
    }

    /**
     * Verify the service creates, stores, updates, and deletes reference objects.
     * @throws Exception
     */
    @MediumTest
    public void testServiceOperations() throws Exception {

        List<Reference> testReferences = DemoDataGenerator.createReferences();

        // multiple service instances are used in this test to confirm that the service is not
        // just reading objects from memory.
        ReferenceServiceSioImpl serviceInstance1 = new ReferenceServiceSioImpl(getContext(), testFilename);
        assertEquals(0, serviceInstance1.retrieveAllReferences().size());  // expected to be empty initially

        Reference reference1 = testReferences.get(0);
        assertEquals(0, reference1.getId()); // confirm the reference does not have an id yet.
        serviceInstance1.create(reference1);
        long createTime = reference1.getDateModified();

        assertEquals(1, serviceInstance1.retrieveAllReferences().size());  // expected to contain 1 reference
        assertTrue(0 != reference1.getId()); // confirm the reference was assigned a new id.
        assertTrue(createTime > Long.MIN_VALUE);

        serviceInstance1 = null;

        // confirm that reference1 was persisted
        ReferenceServiceSioImpl serviceInstance2 = new ReferenceServiceSioImpl(getContext(), testFilename);
        List<Reference> referenceList2 = serviceInstance2.retrieveAllReferences();
        assertEquals(1, referenceList2.size());  // expected to contain 1 reference

        Reference reference2 = referenceList2.get(0);
        assertFalse(reference1 == reference2);  // shouldn't be the same java object
        assertEquals(reference1, reference2);   // but should be equal

        // add a few other references to confirm more than one item is persisted
        for (int i = 1; i < testReferences.size(); i++) {
            serviceInstance2.create(testReferences.get(i));
        }

        assertEquals(testReferences.size(), serviceInstance2.retrieveAllReferences().size());  // expected to contain all test references
        reference2 = null;
        referenceList2 = null;
        serviceInstance2 = null;

        // confirm a reference can be updated
        ReferenceServiceSioImpl serviceInstance3 = new ReferenceServiceSioImpl(getContext(), testFilename);
        List<Reference> referenceList3 = serviceInstance3.retrieveAllReferences();
        assertEquals(testReferences.size(), referenceList3.size());  // expected to contain all test references

        reference1.setAuthors("new authors");
        serviceInstance3.update(reference1);
        serviceInstance3 = null;

        ReferenceServiceSioImpl serviceInstance4 = new ReferenceServiceSioImpl(getContext(), testFilename);
        List<Reference> referenceList4 = serviceInstance4.retrieveAllReferences();
        assertEquals(testReferences.size(), referenceList4.size());  // expected to contain all test references

        Reference reference4 = referenceList4.get(0);
        assertEquals("new authors", reference4.getAuthors());
        long updateTime = reference1.getDateModified();
        assertTrue(updateTime > createTime );

        // confirm a reference can be deleted
        serviceInstance4.delete(reference4);
        serviceInstance4 = null;
        referenceList4 = null;
        reference4=null;

        ReferenceServiceSioImpl serviceInstance5 = new ReferenceServiceSioImpl(getContext(), testFilename);
        List<Reference> referenceList5 = serviceInstance5.retrieveAllReferences();
        assertEquals(testReferences.size() - 1, referenceList5.size());  // expected to contain all test references

        for (int i = 0; i < referenceList5.size(); i++) {
            assertFalse(referenceList5.get(i).getId() == reference1.getId());
        }
    }

    /**
     * Verify service filtering.
     * @throws Exception
     */
    @MediumTest
    public void testServiceFiltering() throws Exception {

        List<Reference> testReferences = DemoDataGenerator.createReferences();

        ReferenceServiceSioImpl serviceInstance = new ReferenceServiceSioImpl(getContext(), testFilename);

        // add all the test references
        for (int i = 0; i < testReferences.size(); i++) {
            serviceInstance.create(testReferences.get(i));
        }
        assertEquals(4, serviceInstance.retrieveAllReferences().size());

        // by default the demo data has two favorites.
        assertEquals(2, serviceInstance.retrieveReferences(ReferenceFilter.FAVORITES).size());

        // the two recent reference should be the last two references in the test list.
        List<Reference> recentReferences = serviceInstance.retrieveReferences(ReferenceFilter.RECENT);
        assertEquals(2, recentReferences.size());
        assertEquals(testReferences.get(3),recentReferences.get(0));
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
        testReferences.get(2).setFavorite(true);
        serviceInstance.update(testReferences.get(2));

        assertEquals(2, serviceInstance.retrieveReferences(ReferenceFilter.FAVORITES).size());

        // verify that the first and third references are the two recent references.
        recentReferences = serviceInstance.retrieveReferences(ReferenceFilter.RECENT);
        assertEquals(2, recentReferences.size());
        assertEquals(testReferences.get(2), recentReferences.get(0));
        assertEquals(testReferences.get(0),recentReferences.get(1));

    }

}
