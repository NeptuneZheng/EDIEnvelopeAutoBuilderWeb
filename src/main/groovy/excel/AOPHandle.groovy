package excel

import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method

class AOPHandle implements InvocationHandler {
    private Object target

    public AOPHandle(Object target) {
        this.target = target;
    }
    Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        println("reset the Cell background color")
        Object recv = method.invoke(target, args)
        return recv;
    }
}
