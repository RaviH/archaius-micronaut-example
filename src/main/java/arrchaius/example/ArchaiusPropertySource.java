package arrchaius.example;

import com.netflix.config.DynamicPropertyFactory;
import com.netflix.config.jmx.ConfigJMXManager;
import com.netflix.config.jmx.ConfigMBean;
import io.micronaut.context.env.PropertySource;
import io.micronaut.context.env.SystemPropertiesPropertySource;
import org.apache.commons.configuration.AbstractConfiguration;

import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import static com.netflix.config.ConfigurationManager.getConfigInstance;

public class ArchaiusPropertySource implements PropertySource {

    /**
     * The position of the loader.
     */
    public static final int POSITION = SystemPropertiesPropertySource.POSITION - 100;


    private static final String ARCHAIUS = "archaius";

    /**
     * Constant for Environment property source.
     */
    private static final String NAME = ARCHAIUS;


    private final String name = NAME;
    private final Map map;

    /**
     * Default constructor.
     */
    public ArchaiusPropertySource() {

        this.map = getAllPropertiesAsMap();
    }

    /**
     * @return The name of the property source
     */
    @Override
    public String getName() {
        return ARCHAIUS;
    }

    /**
     * Get a property value of the given key.
     *
     * @param key The key
     * @return The value
     */
    @Override
    public Object get(final String key) {

        return DynamicPropertyFactory.getInstance().getStringProperty(key, "not found").get();
    }

    public Map<String, String> getAllPropertiesAsMap() {

        final AbstractConfiguration config = getConfigInstance();
        final ConfigMBean configMBean = ConfigJMXManager.registerConfigMbean(config);
        final Properties properties = (Properties) configMBean.obtainProperties();
        return properties.stringPropertyNames().stream().collect(Collectors.toMap(prop -> prop, properties::getProperty));
    }

    @Override
    public Iterator<String> iterator() {

        Iterator i = map.keySet().iterator();

        return new Iterator<String>() {
            @Override
            public boolean hasNext() {
                return i.hasNext();
            }

            @Override
            public String next() {
                return i.next().toString();
            }
        };
    }

    @Override
    public String toString() {
        return getName();
    }
}
