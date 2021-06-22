package com.github.chinhung.pointwave;

import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static com.github.chinhung.pointwave.Spy.SpyAssertions.assertThat;

public class DecorateeTest {

    @Test
    public void testDecorateOrder() {
        final Spy spy1 = new Spy();
        final Spy spy2 = new Spy();
        final Spy spy3 = new Spy();
        final Function<Spy, Function<Spy, Spy>> with = (spy) -> (decoratee) -> {
            decoratee.decorator = spy;
            spy.decoratee = decoratee;
            return spy;
        };
        final Function<Spy, Spy> withSpy2 = with.apply(spy2);
        final Function<Spy, Spy> withSpy3 = with.apply(spy3);

        PointWave.decoratee(spy1)
                .decorated(withSpy2)
                .decorated(withSpy3)
                .complete();

        assertThat(spy1).decorateeIsEmpty();
        assertThat(spy1).decoratorIs(spy2);
        assertThat(spy2).decorateeIs(spy1);
        assertThat(spy2).decoratorIs(spy3);
        assertThat(spy3).decorateeIs(spy2);
        assertThat(spy3).decoratorIsEmpty();
    }
}
