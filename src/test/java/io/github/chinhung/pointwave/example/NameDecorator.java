package io.github.chinhung.pointwave.example;

public class NameDecorator extends HelloWorld {

    private final HelloWorld decoratee;
    private final String name;

    public NameDecorator(final HelloWorld decoratee, final String name) {
        this.decoratee = decoratee;
        this.name = name;
    }

    @Override
    public String toString() {
        return decoratee.toString() + " " + name + "!";
    }
}
