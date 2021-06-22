package io.github.chinhung.pointwave.example;

import io.github.chinhung.pointwave.PointWave;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HelloWorldTest {

    @Test
    public void exampleTest() {
        HelloWorld decorated = PointWave.decoratee(new HelloWorld())
            .decorated(decoratee -> new NameDecorator(decoratee, "John Doe"))
            .decorated(decoratee -> new TodayDecorator(decoratee, "Friday"))
            .complete();

        assertEquals("Hello World! John Doe! Today is Friday!", decorated.toString());
    }
}
