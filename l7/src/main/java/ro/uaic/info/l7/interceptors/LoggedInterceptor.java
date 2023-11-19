package ro.uaic.info.l7.interceptors;

import ro.uaic.info.l7.beans.UserSessionBean;
import ro.uaic.info.l7.interfaces.Logged;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.*;

@Logged
@Interceptor
public class LoggedInterceptor implements Serializable {
    private static final Logger logger = Logger.getLogger(LoggedInterceptor.class.getName());
    @Inject
    private UserSessionBean userSessionBean;
    static {
        try {
            FileHandler fileHandler = new FileHandler("C:\\Users\\Gheorghita\\Downloads\\l7\\src\\main\\resources\\logs\\logs.txt");
            logger.addHandler(fileHandler);
            fileHandler.setFormatter(new CustomFormatter());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @AroundInvoke
    public Object logMethodEntry(InvocationContext invocationContext) throws Exception {
        logger.log(Level.INFO, "Entering method: " + invocationContext.getMethod().getName());
        logger.log(Level.INFO, "Username: " + userSessionBean.readUsername());
        try {
            return invocationContext.proceed();
        } finally {
            logger.log(Level.INFO, "Exiting method: " + invocationContext.getMethod().getName());
        }
    }

    private static class CustomFormatter extends SimpleFormatter {
        @Override
        public String format(LogRecord record) {
            return String.format(
                    "[%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS.%1$tL][%2$s] %3$s%n",
                    record.getMillis(),
                    record.getLevel(),
                    formatMessage(record)
            );
        }
    }
}
