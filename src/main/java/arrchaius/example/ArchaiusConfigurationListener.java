package arrchaius.example;

import com.google.common.collect.ImmutableMap;
import com.netflix.config.DynamicPropertyFactory;
import io.micronaut.context.ApplicationContext;
import io.micronaut.runtime.context.scope.refresh.RefreshEvent;
import org.apache.commons.configuration.event.ConfigurationEvent;
import org.apache.commons.configuration.event.ConfigurationListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArchaiusConfigurationListener implements ConfigurationListener {

    private static final Logger logger = LoggerFactory
            .getLogger(ArchaiusConfigurationListener.class);

    private ApplicationContext applicationContext;

    public ArchaiusConfigurationListener(final ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * Notifies this listener about a manipulation on a monitored configuration
     * object.
     *
     * @param event the event describing the manipulation
     */
    @Override
    public void configurationChanged(final ConfigurationEvent event) {

        if (!event.isBeforeUpdate()) {

            final String propertyName = event.getPropertyName();
            final Object propertyValue = event.getPropertyValue();
            final ImmutableMap<String, Object> properties = ImmutableMap.of(propertyName, propertyValue);
            logger.trace("Publishing a refresh event for property name: [{}] with value: [{}]", propertyName, propertyValue);
            applicationContext.publishEvent(new RefreshEvent(properties));
            final String foo = applicationContext.getBean(SomeService.class).getFoo();
            logger.info("---FOO value: {}", foo);
            logger.info("---DPF value: {}", DynamicPropertyFactory.getInstance().getStringProperty("foo", "NOT SET"));
        }
    }
}
