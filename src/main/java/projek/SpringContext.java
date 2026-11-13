package projek;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Titik akses tunggal ke Spring container.
 * Container dibuat sekali, lalu bean diambil lewat get(...).
 */
public final class SpringContext {

    private static AnnotationConfigApplicationContext context;

    private SpringContext() {
    }

    public static synchronized AnnotationConfigApplicationContext getContext() {
        if (context == null) {
            context = new AnnotationConfigApplicationContext(AppConfig.class);
        }
        return context;
    }

    public static <T> T get(Class<T> type) {
        return getContext().getBean(type);
    }

    public static synchronized void close() {
        if (context != null) {
            context.close();
            context = null;
        }
    }
}
