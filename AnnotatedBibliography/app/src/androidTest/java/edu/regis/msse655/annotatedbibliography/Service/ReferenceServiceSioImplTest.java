package edu.regis.msse655.annotatedbibliography.service;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.test.suitebuilder.annotation.MediumTest;

import java.util.List;

import edu.regis.msse655.annotatedbibliography.model.Reference;

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

        assertEquals(1, serviceInstance1.retrieveAllReferences().size());  // expected to contain 1 reference
        assertTrue(0 != reference1.getId()); // confirm the reference was assigned a new id.

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

}
