package org.oast.creobj;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.oast.creobj.generator.EnumGenerator;
import org.oast.creobj.generator.StringGenerator;
import org.oast.creobj.generator.UtilDateGenerator;
import org.oast.creobj.object.*;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

/**
 * Created by bandr on 22.02.2016.
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest({StringGenerator.class, ObjectGenerator.class, EnumGenerator.class, UtilDateGenerator.class})
public class ObjectGeneratorTest {
    @Test
    public void testStringGenerator() throws Exception {
        //given
        final UUID mock = mock(UUID.class);
        mockStatic(UUID.class);
        given(UUID.randomUUID()).willReturn(mock);
        given(mock.toString()).willReturn("GeneratedUUID");

        ObjectGenerator generator = new ObjectGenerator();

        //when
        Object o = generator.generateObject(TestString.class);

        //then
        assertEquals("Assert that correct class is generated", TestString.class, o.getClass());
        assertEquals("GeneratedUUID", ((TestString) o).getField1());
    }

    @Test
    public void testNumberGenerator() throws Exception {
        //given
        final Random mock = mock(Random.class);
        PowerMockito.whenNew(Random.class).withAnyArguments().thenReturn(mock);
        prepareRandomForNumberGeneration(mock);

        ObjectGenerator generator = new ObjectGenerator();

        //when
        Object o = generator.generateObject(TestPrimitives.class);

        //then
        assertEquals("Assert that correct class is generated", TestPrimitives.class, o.getClass());
        TestPrimitives object = (TestPrimitives) o;
        assertEquals(Long.MAX_VALUE, object.getaLong());
        assertEquals(Float.MAX_VALUE, object.getaFloat());
        assertEquals(Double.MAX_VALUE, object.getaDouble());
        assertTrue(Boolean.TRUE == object.isaBoolean());

        assertEquals(Byte.MAX_VALUE, object.getaByte());
        assertEquals(Short.MAX_VALUE, object.getaShort());
        assertEquals(Integer.MAX_VALUE, object.getAnInt());
    }

    private void prepareRandomForNumberGeneration(Random mock) {
        given(mock.nextLong()).willReturn(Long.MAX_VALUE);
        given(mock.nextFloat()).willReturn(Float.MAX_VALUE);
        given(mock.nextDouble()).willReturn(Double.MAX_VALUE);
        given(mock.nextBoolean()).willReturn(Boolean.TRUE);
        given(mock.nextInt(eq(Byte.MAX_VALUE + 1))).willReturn((int) Byte.MAX_VALUE);
        given(mock.nextInt(eq(Short.MAX_VALUE + 1))).willReturn((int) Short.MAX_VALUE);
        given(mock.nextInt()).willReturn(Integer.MAX_VALUE);
    }

    @Test
    public void testEnumGenerator() throws Exception {
        //given
        final Random mock = mock(Random.class);
        PowerMockito.whenNew(Random.class).withAnyArguments().thenReturn(mock);
        given(mock.nextInt(TestEnum.class.getEnumConstants().length)).willReturn(1);

        ObjectGenerator generator = new ObjectGenerator();

        //when
        Object o = generator.generateObject(TestEnumField.class);

        //then
        assertEquals("Assert that correct class is generated", TestEnumField.class, o.getClass());
        TestEnumField object = (TestEnumField) o;
        assertSame(TestEnum.TWO, object.getField());
    }

    @Test
    public void testUtilDateGenerator() throws Exception {
        //given
        Date mock = mock(Date.class);
        PowerMockito.whenNew(Date.class).withAnyArguments().thenReturn(mock);

        ObjectGenerator generator = new ObjectGenerator();

        //when
        Object o = generator.generateObject(TestUtilDate.class);

        //then
        assertEquals("Assert that correct class is generated", TestUtilDate.class, o.getClass());
        TestUtilDate date = (TestUtilDate) o;
        assertEquals(mock, date.getUtilDate());
    }

    @Test
    public void testGeneralObjectGeneration() throws Exception {
        //given
        final Random randomMock = mock(Random.class);
        PowerMockito.whenNew(Random.class).withAnyArguments().thenReturn(randomMock);
        prepareRandomForNumberGeneration(randomMock);
        given(randomMock.nextInt(Character.MAX_VALUE + 1)).willReturn(1);

        final UUID mockUUID = mock(UUID.class);
        mockStatic(UUID.class);
        given(UUID.randomUUID()).willReturn(mockUUID);
        given(mockUUID.toString()).willReturn("GeneratedUUID");

        Date mockDate = mock(Date.class);
        PowerMockito.whenNew(Date.class).withAnyArguments().thenReturn(mockDate);

        ObjectGenerator generator = new ObjectGenerator();

        //when
        Object o = generator.generateObject(TestObject.class);
        //then
        assertEquals("Assert that correct class is generated", TestObject.class, o.getClass());
        TestObject object = (TestObject) o;

        assertEquals("Verification of long generation failed", Long.MAX_VALUE, object.getL());
        assertEquals("Verification of float generation failed", Float.MAX_VALUE, object.getF());
        assertEquals("Verification of double generation failed", Double.MAX_VALUE, object.getD());
        assertTrue("Verification of boolean generation failed", Boolean.TRUE == object.isBool());

        assertEquals("Verification of byte generation failed", Byte.MAX_VALUE, object.getB());
        assertEquals("Verification of short generation failed", Short.MAX_VALUE, object.getS());
        assertEquals("Verification of int generation failed", Integer.MAX_VALUE, object.getI());
        assertEquals("Verification of char generation failed", (char) 1, object.getC());

        assertEquals("Verification of inner object class failed", Integer.MAX_VALUE, object.getTestObjectInner().getK());

        assertEquals("Verification of string generation failed", "GeneratedUUID", object.getStr());
        assertEquals("Verification of java.util.Date generation failed", mockDate, object.getDate());

        assertEquals("Verification of Integer generation failed", (Integer) Integer.MAX_VALUE, object.getiNumber());
    }
}
