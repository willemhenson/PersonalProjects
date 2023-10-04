package test.structural;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;


enum AccessType {PUBLIC, PROTECTED, PRIVATE}


public class ClassMemberHelper {

	public static boolean isAccessType(int mods, AccessType type) {
		if (type == AccessType.PUBLIC) {
			if (Modifier.isPublic(mods)) return true;
		} else if (type == AccessType.PROTECTED) {
			if (Modifier.isProtected(mods)) return true;
		} else if (type == AccessType.PRIVATE) {
			if (Modifier.isPrivate(mods)) return true;
		}
		return false;
	}

    public static boolean fieldAccessIsAccessType(String fieldName, Field[] fields, AccessType type){
		for (int i = 0; i < fields.length; i++) {
			if (fields[i].getName().equals(fieldName)) {
				if (isAccessType(fields[i].getModifiers(), type)) return true;
			}
		}
		return false;
	}

    public static boolean fieldExists(String fieldName, Field[] fields) {
		for(int i = 0; i < fields.length; i++){
			if (fields[i].getName().equals(fieldName)) {
				return true;
			}
		}
		return false;
	}

	public static boolean fieldIsFinal(String fieldName, Field[] fields) {
		for (int i = 0; i < fields.length; i++) {
			if (fields[i].getName().equals(fieldName)) {
				if (Modifier.isFinal(fields[i].getModifiers())) return true;
			}
		}
		return false;
	}

	public static boolean fieldIsStatic(String fieldName, Field[] fields) {
		for (int i = 0; i < fields.length; i++) {
			if (fields[i].getName().equals(fieldName)) {
				if (Modifier.isStatic(fields[i].getModifiers())) return true;
			}
		}
		return false;
	}

	public static boolean hasSerialVersionUID(Field[] fields) {
		return fieldExists("serialVersionUID", fields);
	}

    public static Field[] getFields(String className){
		try {
			return Class.forName(className).getDeclaredFields();
		} catch (ClassNotFoundException e) { return null; }
	}

	public static Method[] getMethods(String className){
		try {
			return Class.forName(className).getDeclaredMethods();
		} catch (ClassNotFoundException e) { return null; }
	}

    public static boolean methodAccessIsAccessType(String methodName, Method[] methods, AccessType type){
		for (int i = 0; i < methods.length; i++) {
			if (methods[i].getName().equals(methodName)) {
				if (isAccessType(methods[i].getModifiers(), type)) return true;
			}
		}
		return false;
	}

	public static boolean methodIsStatic(String methodName, Method[] methods) {
		for (int i = 0; i < methods.length; i++) {
			if (methods[i].getName().equals(methodName)) {
				if (Modifier.isStatic(methods[i].getModifiers())) return true;
			}
		}
		return false;
	}

	public static int methodCount(String methodName, Method[] methods){
		int found = 0;
		for(int i = 0; i < methods.length; i++){
			if (methods[i].getName().equals(methodName)) {
				++found;
			}
		}
		return found;
	}

	public static boolean methodExists(String methodName, Method[] methods) {
		return methodCount(methodName, methods) > 0;
	}

	public static boolean classHasPublicStaticFinalSerialVersionUID(String fqcn) {
        Class clazz = null;
		Field field = null;
		try {
			clazz = Class.forName(fqcn);
            field = clazz.getDeclaredField("serialVersionUID");
		} catch (ClassNotFoundException e) {
			return false;
		} catch (NoSuchFieldException e) {
			return false;
		}
        if (!((Class)field.getType()).equals(long.class)) return false;
        if (!isAccessType(field.getModifiers(), AccessType.PRIVATE)) return false;
        return Modifier.isFinal(field.getModifiers()) &&  Modifier.isStatic(field.getModifiers());
	}

	public static boolean serialVersionUIDIsLong(Field[] fields) {
		for (int i = 0; i < fields.length; i++) {
			if (fields[i].getName().equals("serialVersionUID")) {
				if (fields[i].getType().equals(Long.TYPE)) return true;
			}
		}
		return false;
	}

}