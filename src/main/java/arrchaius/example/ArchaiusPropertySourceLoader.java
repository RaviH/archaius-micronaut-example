package arrchaius.example;

import com.netflix.config.ConfigurationManager;
import io.micronaut.context.env.AbstractPropertySourceLoader;
import io.micronaut.context.env.PropertySource;
import io.micronaut.core.io.ResourceLoader;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

public class ArchaiusPropertySourceLoader extends AbstractPropertySourceLoader {

    static int counter = 0;
    @Override
    public boolean isEnabled() {
        return isConfigurationManagerClassPresent();
    }

    private static boolean isConfigurationManagerClassPresent() {

        try {
            final Class<ConfigurationManager> configurationManagerClass = ConfigurationManager.class;
            return true;
        } catch (Throwable e) {
            return false;
        }
    }

    @Override
    public int getOrder() {

        return AbstractPropertySourceLoader.DEFAULT_POSITION;
    }


    /**
     * Load a {@link PropertySource} for the given {@link Environment}.
     *
     * @param resourceName    The resourceName of the resource to load
     * @param resourceLoader  The {@link ResourceLoader} to retrieve the resource
     * @param environmentName The environment name to load. Null if the default environment is to be used
     * @return An optional of {@link PropertySource}
     */
    @Override
    public Optional<PropertySource> load(final String resourceName, final ResourceLoader resourceLoader, @Nullable final String environmentName) {

        counter++;
        if (isEnabled() && counter > 1) {

            return Optional.of(new ArchaiusPropertySource());
        }

        return Optional.empty();
    }

    /**
     * @param name     The name
     * @param input    The input stream
     * @param finalMap The map with all the properties processed
     * @throws IOException If the input stream doesn't exist
     */
    @Override
    protected void processInput(final String name, final InputStream input, final Map<String, Object> finalMap) throws IOException {

        Properties props = new Properties();
        props.load(input);
        for (Map.Entry<Object, Object> entry : props.entrySet()) {
            finalMap.put(entry.getKey().toString(), entry.getValue());
        }
    }
}
