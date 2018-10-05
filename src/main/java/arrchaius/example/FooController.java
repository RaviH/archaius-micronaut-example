package arrchaius.example;

import com.netflix.config.DynamicPropertyFactory;
import io.micronaut.context.annotation.Value;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

import javax.inject.Inject;

@Controller("/foo")
public class FooController {

    @Value("${foo:NOT SET}")
    private String p;

    @Inject
    EventRegistrar eventRegistrar;

    @Get("/")
    public String index() {

        return String.format("Property value from micronaut: %s, from archaius: %s", p, getValueFromArchaius());
    }

    private String getValueFromArchaius() {
        return DynamicPropertyFactory.getInstance().getStringProperty("foo", "NOT SET").get();
    }
}
