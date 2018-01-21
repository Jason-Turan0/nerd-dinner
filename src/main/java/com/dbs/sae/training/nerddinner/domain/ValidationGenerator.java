package com.dbs.sae.training.nerddinner.domain;

import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.validation.constraints.Size;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ValidationGenerator {

    private static final Set<Class<?>> PRIMITIVE_TYPES = getPrimitiveTypes();
    private static List<Class<?>> SUPPORTED_ANNOTATIONS = getSupportedAnnotations();
    private final ResourceBundle resourceBundle;

    public ValidationGenerator(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

    private static Field GetField(Class<?> c, String fieldName) {
        try {
            return c.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean isIterable(Class<?> returnType) {
        return Iterable.class.isAssignableFrom(returnType);
    }

    private static void addValidatorForAnnotation(JSONObject validatorsForMethod, Annotation a) {

    }

    private static boolean isSupportedAnnotation(Class<? extends Annotation> aClass) {
        return SUPPORTED_ANNOTATIONS.contains(aClass);
    }

    private static boolean isPrimitiveType(Class<?> type) {
        return PRIMITIVE_TYPES.contains(type) || type.isPrimitive();
    }

    private static List<Class<?>> getSupportedAnnotations() {
        return Arrays.asList(
                NotEmpty.class,
                Size.class
        );
    }

    private static Set<Class<?>> getPrimitiveTypes() {
        Set<Class<?>> ret = new HashSet<>();
        ret.add(Boolean.class);
        ret.add(Character.class);
        ret.add(Byte.class);
        ret.add(Short.class);
        ret.add(Integer.class);
        ret.add(Long.class);
        ret.add(Float.class);
        ret.add(Double.class);
        ret.add(String.class);
        return ret;
    }

    private static Object GetFieldValue(Method m, Object value) {
        if (value == null) throw new RuntimeException("");
        try {
            return m.invoke(value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public JSONObject getValidationRules(Object withValidation) {
        try {
            List<FieldPath> paths = new ArrayList<>();
            SearchForPathsWithAnnotations(withValidation, paths, "");

            JSONObject rules = new JSONObject();
            for (FieldPath fp : paths) {
                JSONObject validations = new JSONObject();
                for (Annotation a : fp.Annotations) {
                    validations.put(getValidationNameForAnnotation(a), getAnnotationValue(a));
                }
                rules.put(fp.Path, validations);
            }
            JSONObject jsonMessages = new JSONObject();
            for (FieldPath fp : paths) {
                JSONObject messages = new JSONObject();
                for (Annotation a : fp.Annotations) {
                    messages.put(getValidationNameForAnnotation(a), getAnnotationMessage(a));
                }
                jsonMessages.put(fp.Path, messages);
            }

            JSONObject validateParam = new JSONObject()
                    .put("rules", rules)
                    .put("messages", jsonMessages);

            return validateParam;
        } catch (JSONException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private String getAnnotationMessage(Annotation a) {
        if (a instanceof NotEmpty) {
            NotEmpty ne = (NotEmpty) a;
            return getMessageFromResourceBundle(ne.message());
        } else if (a instanceof Size) {
            Size s = (Size) a;
            String message = getMessageFromResourceBundle(s.message());
            return String.format(message, s.min(), s.max());
        } else {
            throw new NotImplementedException();
        }
    }

    private String getMessageFromResourceBundle(String message) {
        String key = message.replace("{", "").replace("}", "");
        if (this.resourceBundle.containsKey(key)) {
            return this.resourceBundle.getString(key);
        } else {
            return message;
        }
    }

    private Object getAnnotationValue(Annotation a) {
        if (a instanceof NotEmpty) {
            return true;
        } else if (a instanceof Size) {
            Size s = (Size) a;
            JSONArray ja = new JSONArray();
            ja.put(s.min());
            ja.put(s.max());
            return ja;
        } else {
            throw new NotImplementedException();
        }
    }

    private String getValidationNameForAnnotation(Annotation a) {
        if (a instanceof NotEmpty) {
            return "required";
        } else if (a instanceof Size) {
            return "rangelength";
        } else {
            throw new NotImplementedException();
        }
    }

    private void SearchForPathsWithAnnotations(Object withValidation, List<FieldPath> paths, String parentPath) {
        if (withValidation == null) return;
        List<Method> methods = Arrays
                .stream(withValidation.getClass().getMethods())
                .filter(m -> m.getName().startsWith("get") && fieldExistsForMethod(m))
                .collect(Collectors.toList());
        Class<?> c = withValidation.getClass();
        String acessorPath = parentPath.trim().isEmpty() ? "" : parentPath + ".";
        for (Method m : methods) {
            Class<?> returnType = m.getReturnType();
            String name = m.getName().replace("get", "");
            String fieldName = name.substring(0, 1).toLowerCase() + name.substring(1);
            Field f = GetField(c, fieldName);
            List<Annotation> validationAnnotations = Arrays.stream(f.getAnnotations()).filter(a -> isSupportedAnnotation(a.annotationType())).collect(Collectors.toList());
            if (isPrimitiveType(returnType)) {
                String currentPath = String.format("%s%s", acessorPath, fieldName);
                paths.add(new FieldPath(currentPath, validationAnnotations));
            } else if (isIterable(returnType)) {
                List<Object> items = StreamSupport.stream(((Iterable<Object>) GetFieldValue(m, withValidation)).spliterator(), false).collect(Collectors.toList());
                for (Integer i = 0; i < items.size(); i++) {
                    Object item = items.get(i);
                    String currentPath = String.format("%s%s[%s]", acessorPath, fieldName, i);
                    if (item == null || isPrimitiveType(item.getClass())) continue;
                    SearchForPathsWithAnnotations(item, paths, currentPath);
                }
            } else {
                String currentPath = String.format("%s%s", acessorPath, fieldName);
                Object item = GetFieldValue(m, withValidation);
                SearchForPathsWithAnnotations(item, paths, currentPath);
            }
        }

    }

    private boolean fieldExistsForMethod(Method m) {
        String name = m.getName().replace("get", "");
        if (name.isEmpty()) return false;
        String fieldName = name.substring(0, 1).toLowerCase() + name.substring(1);
        try {
            return m.getDeclaringClass().getDeclaredField(fieldName) != null;
        } catch (NoSuchFieldException e) {
            return false;
        }
    }

    private class FieldPath {
        public String Path;
        public List<Annotation> Annotations;

        public FieldPath(String path, List<Annotation> annotations) {
            this.Path = path;
            this.Annotations = annotations;
        }
    }

}
