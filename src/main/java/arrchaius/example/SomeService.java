package arrchaius.example;

import io.micronaut.context.annotation.Prototype;
import io.micronaut.context.annotation.Value;

@Prototype
public class SomeService {

    @Value("${foo:NOT SET}")
    private String foo;

    public String getFoo() {

        return String.format("Property value from micronaut: %s", foo);
    }
}