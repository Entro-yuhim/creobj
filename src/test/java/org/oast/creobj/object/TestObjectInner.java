package org.oast.creobj.object;

public class TestObjectInner {

    private int k;

    public TestObjectInner(TestObjectInner testObjectInner) {
        this.k = testObjectInner.k;
    }

    @Override
    public String toString() {
        return "TestObjectInner{" +
                "k=" + k +
                '}';
    }

}
