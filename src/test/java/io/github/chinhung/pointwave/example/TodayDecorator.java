package io.github.chinhung.pointwave.example;

public class TodayDecorator extends HelloWorld {

    private final HelloWorld decoratee;
    private final String today;

    public TodayDecorator(final HelloWorld decoratee, final String today) {
        this.decoratee = decoratee;
        this.today = today;
    }

    @Override
    public String toString() {
        return decoratee.toString() + " Today is " + today + "!";
    }
}
