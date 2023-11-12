package ro.uaic.info.l3.interceptors;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

public class GroupSelectionSelectorInterceptor {
    private static int methodInvocationCount = 0;

    @AroundInvoke
    public Object monitorMethodInvocation(InvocationContext context) throws Exception {
        methodInvocationCount++;
        System.out.println("Method " + context.getMethod().getName() + " was called. Total invocations: " + methodInvocationCount);
        return context.proceed();
    }

    public static int getMethodInvocationCount() {
        return methodInvocationCount;
    }
}
