package io.github.chinhung.pointwave;

import org.junit.jupiter.api.Test;

import java.util.function.Function;

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

        Spy.SpyAssertions.assertThat(spy1).decorateeIsEmpty();
        Spy.SpyAssertions.assertThat(spy1).decoratorIs(spy2);
        Spy.SpyAssertions.assertThat(spy2).decorateeIs(spy1);
        Spy.SpyAssertions.assertThat(spy2).decoratorIs(spy3);
        Spy.SpyAssertions.assertThat(spy3).decorateeIs(spy2);
        Spy.SpyAssertions.assertThat(spy3).decoratorIsEmpty();
    }
}
