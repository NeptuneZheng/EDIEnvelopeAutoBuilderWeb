package service.tools;

import pojo.User;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectTest {
    public  static void main(String[] args) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        System.out.println("current class loader: " + classLoader);
        System.out.println("parent class loader: " + classLoader.getParent());
        System.out.println("grandparent class loader: " + classLoader.getParent().getParent());
        System.out.println("------------------------------");
        User user = testClassLoad();
        System.out.println(user.toString());

    }

    public static User testClassLoad() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Class clazz = loader.loadClass("pojo.User");

        Constructor constructor = clazz.getDeclaredConstructor((Class []) null);
        User user = (User) constructor.newInstance();

        Method method = clazz.getMethod("setUserName",String.class);
        method.invoke(user,"Neptune");

        Field field = clazz.getDeclaredField("password");
        field.setAccessible(true); // make the private resource to be accessible
        field.set(user,"123456");

        return user;

    }
}
