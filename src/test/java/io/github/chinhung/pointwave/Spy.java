package io.github.chinhung.pointwave;

import org.junit.jupiter.api.Assertions;

public class Spy {

    public Spy decoratee;
    public Spy decorator;

    static class SpyAssertions {

        private Spy spy;

        SpyAssertions(Spy spy) {
            this.spy = spy;
        }

        void decorateeIsEmpty() {
            Assertions.assertNull(spy.decoratee);
        }

        void decorateeIs(Spy excepted) {
            Assertions.assertEquals(excepted, spy.decoratee);
        }

        void decoratorIsEmpty() {
            Assertions.assertNull(spy.decorator);
        }

        void decoratorIs(Spy excepted) {
            Assertions.assertEquals(excepted, spy.decorator);
        }

        static SpyAssertions assertThat(Spy spy) {
            return new SpyAssertions(spy);
        }
    }
}
