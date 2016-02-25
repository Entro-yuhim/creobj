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
        return new HashSet<>(set);
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

    public byte getB() {
        return b;
    }

    public void setB(byte b) {
        this.b = b;
    }

    public short getS() {
        return s;
    }

    public void setS(short s) {
        this.s = s;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public long getL() {
        return l;
    }

    public void setL(long l) {
        this.l = l;
    }

    public float getF() {
        return f;
    }

    public void setF(float f) {
        this.f = f;
    }

    public double getD() {
        return d;
    }

    public void setD(double d) {
        this.d = d;
    }

    public boolean isBool() {
        return bool;
    }

    public void setBool(boolean bool) {
        this.bool = bool;
    }

    public char getC() {
        return c;
    }

    public void setC(char c) {
        this.c = c;
    }

    public TestObjectInner getTestObjectInner() {
        return testObjectInner;
    }

    public void setTestObjectInner(TestObjectInner testObjectInner) {
        this.testObjectInner = testObjectInner;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getiNumber() {
        return iNumber;
    }

    public void setiNumber(Integer iNumber) {
        this.iNumber = iNumber;
    }

    public byte[] getbArray() {
        return bArray;
    }

    public void setbArray(byte[] bArray) {
        this.bArray = bArray;
    }

    public short[] getsArray() {
        return sArray;
    }

    public void setsArray(short[] sArray) {
        this.sArray = sArray;
    }

    public int[] getiArray() {
        return iArray;
    }

    public void setiArray(int[] iArray) {
        this.iArray = iArray;
    }

    public long[] getlArray() {
        return lArray;
    }

    public void setlArray(long[] lArray) {
        this.lArray = lArray;
    }

    public float[] getfArray() {
        return fArray;
    }

    public void setfArray(float[] fArray) {
        this.fArray = fArray;
    }

    public double[] getdArray() {
        return dArray;
    }

    public void setdArray(double[] dArray) {
        this.dArray = dArray;
    }

    public boolean[] getBoolArray() {
        return boolArray;
    }

    public void setBoolArray(boolean[] boolArray) {
        this.boolArray = boolArray;
    }

    public char[] getcArray() {
        return cArray;
    }

    public void setcArray(char[] cArray) {
        this.cArray = cArray;
    }

    public String[] getStrArray() {
        return strArray;
    }

    public void setStrArray(String[] strArray) {
        this.strArray = strArray;
    }

    public List<String> getStrList() {
        return strList;
    }

    public void setStrList(List<String> strList) {
        this.strList = strList;
    }

    public Map<String, String> getStrMap() {
        return strMap;
    }

    public void setStrMap(Map<String, String> strMap) {
        this.strMap = strMap;
    }

    public HashMap<String, String> getHashMap() {
        return hashMap;
    }

    public void setHashMap(HashMap<String, String> hashMap) {
        this.hashMap = hashMap;
    }

    public ArrayList<String> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<String> arrayList) {
        this.arrayList = arrayList;
    }

    public HashSet<String> getHashSet() {
        return hashSet;
    }

    public void setHashSet(HashSet<String> hashSet) {
        this.hashSet = hashSet;
    }

    public TestEnum getTestEnum() {
        return testEnum;
    }

    public void setTestEnum(TestEnum testEnum) {
        this.testEnum = testEnum;
    }
}
