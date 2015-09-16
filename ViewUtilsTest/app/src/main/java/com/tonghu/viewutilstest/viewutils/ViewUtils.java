package com.tonghu.viewutilstest.viewutils;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author york
 * @date 9/16/15
 * @since 1.0.0
 */
public class ViewUtils {
    public static void inject(final Object obj, View view) {
        try {
            Class<?> targetClass = obj.getClass();
            Field[] fields = targetClass.getFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(ViewById.class)) {
                    field.setAccessible(true);
                    field.set(obj, view.findViewById(field.getAnnotation(ViewById.class).value()));
                }
            }
            Method[] methods = targetClass.getMethods();
            for (final Method method : methods) {
                if (method.isAnnotationPresent(OnClick.class)) {
                    int viewId = method.getAnnotation(OnClick.class).value();
                    View clickView = view.findViewById(viewId);
                    clickView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                method.invoke(obj);
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void inject(final Object obj, Activity activity) {
        try {
            Class<?> targetClass = obj.getClass();
            Field[] fields = targetClass.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(ViewById.class)) {
                    Log.i("tonghu", "ViewUtils, inject(L57): ");
                    field.setAccessible(true);
                    field.set(obj, activity.findViewById(field.getAnnotation(ViewById.class).value()));
                }
            }

            Method[] methods = targetClass.getDeclaredMethods();
            for (final Method method : methods) {
                if (method.isAnnotationPresent(OnClick.class)) {
                    int viewId = method.getAnnotation(OnClick.class).value();
                    View clickView = activity.findViewById(viewId);
                    clickView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                method.setAccessible(true);
                                method.invoke(obj);
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
