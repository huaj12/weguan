package com.juzhai.dpsdk.utils;

import java.lang.reflect.Array;

public class ArrayUtils {
	public static Object expand(Object array, int newSize) {
		if (array == null) {
			return null;
		}
		Class c = array.getClass();
		if (c.isArray()) {
			int len = Array.getLength(array);
			if (len >= newSize) {
				return array;
			} else {
				Class cc = c.getComponentType();
				Object newArray = Array.newInstance(cc, newSize);
				System.arraycopy(array, 0, newArray, 0, len);
				return newArray;
			}
		} else {
			throw new ClassCastException("need  array");
		}
	}
}
