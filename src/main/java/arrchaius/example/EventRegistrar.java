package arrchaius.example;

import com.netflix.config.ConfigurationManager;
import io.micronaut.context.ApplicationContext;
import io.micronaut.context.annotation.Context;

import javax.inject.Inject;

@Context
public class EventRegistrar {

    @Inject
    public EventRegistrar(ApplicationContext applicationContext) {

        ConfigurationManager.getConfigInstance().addConfigurationListener(new ArchaiusConfigurationListener(applicationContext));
    }
}
