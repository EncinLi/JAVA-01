import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

public class HelloClassLoader extends ClassLoader {

    private final String suffix = ".xlass";

    public static void main(final String[] args) {
        try {
            final Class<?> clazz = new HelloClassLoader().findClass("Hello");
            final Method hello = clazz.getDeclaredMethod("hello");
            hello.invoke(clazz.newInstance());
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Class<?> findClass(final String name) throws ClassNotFoundException {
        byte[] bytes = new byte[255];
        try {
            bytes = processData(name);
        } catch (final IOException e) {
            e.printStackTrace();
        }

        return defineClass(name, bytes, 0, bytes.length);
    }

    private byte[] processData(final String name) throws IOException {
        try (final InputStream inputStream = getClass().getClassLoader().getResourceAsStream(name + suffix);
             final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();) {
            int byteValue;

            while ((byteValue = inputStream.read()) != -1) {
                outputStream.write(255 - byteValue);
            }
            return outputStream.toByteArray();
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
