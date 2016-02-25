package org.oast.creobj.object;

public class TestObjectInner {

    private int k;

    public TestObjectInner(TestObjectInner testObjectInner) {
        this.k = testObjectInner.k;
    }

    public int getK() {
        return k;
    }

    public void setK(int k) {
        this.k = k;
    }

    @Override
    public String toString() {
        return "TestObjectInner{" +
                "k=" + k +
                '}';
    }

}
