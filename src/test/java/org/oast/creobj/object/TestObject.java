package org.oast.creobj.object;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.*;

public class TestObject {

    // Primitives
    private byte b;
    private short s;
    private int i;
    private long l;
    private float f;
    private double d;
    private boolean bool;
    private char c;

    // Inner class
    private TestObjectInner testObjectInner;

    // Common classes
    private String str;
    private Date date;
    private Integer iNumber;

    // Arrays
    private byte[] bArray;
    private short[] sArray;
    private int[] iArray;
    private long[] lArray;
    private float[] fArray;
    private double[] dArray;
    private boolean[] boolArray;
    private char[] cArray;
    private String[] strArray;

    // Collections
    // Interfaces
    private List<String> strList;
    private Map<String, String> strMap;
    private Set<String> strSet;

    // Concrete classes
    private HashMap<String, String> hashMap;
    private ArrayList<String> arrayList;
    private HashSet<String> hashSet;

    private TestEnum testEnum;


    // Copy constructor
    public TestObject(TestObject obj) {
        // primitives
        this.b = obj.b;
        this.s = obj.s;
        this.i = obj.i;
        this.l = obj.l;
        this.f = obj.f;
        this.d = obj.d;
        this.bool = obj.bool;
        this.c = obj.c;

        // inner class
        this.testObjectInner = new TestObjectInner(obj.testObjectInner);

        // commons
        this.str = obj.str;
        this.date = new Date(obj.date.getTime());
        this.iNumber = obj.iNumber;

        // arrays
        this.bArray = Arrays.copyOf(obj.bArray, obj.bArray.length);
        this.sArray = Arrays.copyOf(obj.sArray, obj.sArray.length);
        this.iArray = Arrays.copyOf(obj.iArray, obj.iArray.length);
        this.lArray = Arrays.copyOf(obj.lArray, obj.lArray.length);
        this.fArray = Arrays.copyOf(obj.fArray, obj.fArray.length);
        this.dArray = Arrays.copyOf(obj.dArray, obj.dArray.length);
        this.boolArray = Arrays.copyOf(obj.boolArray, obj.boolArray.length);
        this.cArray = Arrays.copyOf(obj.cArray, obj.cArray.length);
        this.strArray = Arrays.copyOf(obj.strArray, obj.strArray.length);

        // collections
        this.strList = copyList(obj.strList);
        this.strMap = copyMap(obj.strMap);
        this.strSet = copySet(obj.strSet);
        this.arrayList = (ArrayList<String>) copyList(obj.arrayList);
        this.hashSet = (HashSet<String>) copySet(obj.hashSet);
        this.hashMap = (HashMap<String, String>) copyMap(obj.hashMap);

        // enum
        this.testEnum = obj.testEnum;
    }

    public Set<String> getStrSet() {
        return strSet;
    }

    public void setStrSet(Set<String> strSet) {
        this.strSet = strSet;
    }

    private <T> Set<T> copySet(Set<T> set) {
        Set<T> result = new HashSet<>();
        result.addAll(set);
        return result;
    }

    private <K, V> Map<K, V> copyMap(Map<K, V> map) {
        Map<K, V> result = new HashMap<>();
        if (map.size() != 0) result.putAll(map);
        return result;
    }

    private <T> List<T> copyList(List<T> list) {
        List<T> result = new ArrayList<>();
        for (T t : list) {
            result.add(t);
        }
        return result;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
