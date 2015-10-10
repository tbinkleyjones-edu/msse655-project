package edu.regis.msse655.annotatedbibliography.model;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.test.suitebuilder.annotation.MediumTest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * A test case for unit testing the Reference object.
 */
public class ReferenceTest extends ApplicationTestCase<Application> {

    public ReferenceTest() {
        super(Application.class);
    }

    /**
     * Verify serialization by writing/reading an object to/from a byte array
     * @throws Exception
     */
    @MediumTest
    public void testSerializationShouldProduceEqualObjects() throws Exception {
        Reference original = new Reference();

        ByteArrayOutputStream baOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream oOutputStream = new ObjectOutputStream(baOutputStream);
        oOutputStream.writeObject(original);
        oOutputStream.close();

        ByteArrayInputStream baInputStream = new ByteArrayInputStream(baOutputStream.toByteArray());
        ObjectInputStream oInputStream = new ObjectInputStream(baInputStream);
        Reference clone = (Reference) oInputStream.readObject();

        assertEquals(original, clone);
    }
}
