package org.tango.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ReflectionScanner {

    private final Class<?> classToScan;
    final Map<Class<?>, Set<Method>> methods = new HashMap<Class<?>, Set<Method>>();
    final Map<Class<?>, Set<Field>> fields = new HashMap<Class<?>, Set<Field>>();

    public ReflectionScanner(final Class<?> classToScan) {
        this.classToScan = classToScan;
        scanAnnotatedFields();
        scanAnnotatedMethods();
    }

    public Set<Method> getMethodsAnnotatedWith(final Class<?> annototation) {
        return methods.get(annototation);
    }

    public Set<Field> getFieldsAnnotatedWith(final Class<?> annototation) {
        return fields.get(annototation);
    }

    private void scanAnnotatedMethods() {

        for (final Method method : classToScan.getDeclaredMethods()) {
            final Annotation[] annotations = method.getAnnotations();
            for (final Annotation annotation : annotations) {
                if (methods.containsKey(annotation.annotationType())) {
                    methods.get(annotation.annotationType()).add(method);
                } else {
                    final Set<Method> list = new HashSet<Method>();
                    list.add(method);
                    methods.put(annotation.annotationType(), list);
                }
            }
        }
    }

    private void scanAnnotatedFields() {

        for (final Field field : classToScan.getDeclaredFields()) {
            final Annotation[] annotations = field.getAnnotations();
            for (final Annotation annotation : annotations) {
                if (fields.containsKey(annotation.annotationType())) {
                    fields.get(annotation.annotationType()).add(field);
                } else {
                    final Set<Field> list = new HashSet<Field>();
                    list.add(field);
                    fields.put(annotation.annotationType(), list);
                }
            }
        }
    }
}
