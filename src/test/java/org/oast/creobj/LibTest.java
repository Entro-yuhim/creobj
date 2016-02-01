package org.oast.creobj;

import org.junit.Assert;
import org.junit.Test;
import org.oast.creobj.object.TestObject;

public class LibTest {

    @Test
    public void testCopyConstructor() throws Exception {
        ObjectGenerator objectGenerator = new ObjectGenerator(true);

        TestObject testObject = objectGenerator.createObject(TestObject.class);
        TestObject testObjectCopy = new TestObject(testObject);

        Assert.assertTrue(Checker.checkObjectCopy(testObject, testObjectCopy));
    }

}
