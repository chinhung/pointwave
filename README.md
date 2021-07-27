# PointWave
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://github.com/chinhung/pointwave/blob/master/LICENSE)
![Coverage](https://img.shields.io/badge/coverage-100%25-brightgreen)
![Maven Central](https://img.shields.io/maven-central/v/io.github.chinhung/pointwave)

Helps you to improve the readability of your code when you are using the Decorator pattern.

Makes your code much more readable!

## How to use
For Gradle, add the following dependency in `build.gradle`：
```groovy
dependencies {
    implementation 'io.github.chinhung:pointwave:1.0.0'
}
```

For Maven, add the following dependency in `pom.xml`：
```xml
<dependency>
    <groupId>io.github.chinhung</groupId>
    <artifactId>pointwave</artifactId>
    <version>1.0.0</version>
</dependency>
```

If you use other build tool, please visit the [maven central](https://mvnrepository.com/artifact/io.github.chinhung/pointwave).

## Tutorial
First, call the static method `PointWave.decoratee` to initialize the decoratee. And then, call the method `decorated` with the decorator function as the argument in lambda expression, one by one. The last step is to call `complete` method, which returns the decorated object:
```java
Object decorated = PointWave.decoratee(decoratee)
    .decorated(decoratee -> decorator1Factory.create(decoratee, param1))
    .decorated(decoratee -> Decorator2.createInstance(decoratee, param2))
    .decorated(decoratee -> new Decorator3(decoratee, param3))
    .complete();
```
- the decorator function is a function which receives the decoratee and returns the decorated object
- the decorator functions will be applied following the "First In, First Out" rule (for this example, the inner layer of the decorated object is `decoratee`, the second layer is `Decorator1`, the third layer is `Decorator2` and the outer layer is `Decorator3`)
- provides generic support

## Example
```java
public class HelloWorld {

    @Override
    public String toString() {
        return "Hello World!";
    }
}
```

```java
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
```

```java
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
```

```java
HelloWorld decorated = PointWave.decoratee(new HelloWorld())
    .decorated(decoratee -> new NameDecorator(decoratee, "John Doe"))
    .decorated(decoratee -> new TodayDecorator(decoratee, "Friday"))
    .complete();

assertEquals("Hello World! John Doe! Today is Friday!", decorated.toString());
```


## Why use PointWave

### Without using PointWave

The code would be difficult to read if you decorate the object manually without PointWave in the following situations:
1. a single line code
2. deeply nested code

```java
Object decorated = new Decorator3(Decorator2.createInstance(decorator1Factory.create(decoratee, param1), param2), param3);
```

```java
Object decorated = new Decorator3(
    Decorator2.createInstance(
        decorator1Factory.create(
            decoratee, 
            param1
        ), 
        param2
    ), 
    param3
);
```

We could improve the readability of the code by using a temporary local variable:

```java
Object decorated = decoratee;
decorated = decorator1Factory.create(decorated, param1);
decorated = Decorator2.createInstance(decorated, param2);
decorated = new Decorator3(decorated, param3);
```

However, the code style is imperative.

### With using PointWave

The code style is continuation and familiar to the builder pattern, which is more meaningful. Also, an appropriately designed api brings more intention of these codes for the reader:
```java
Object decorated = PointWave.decoratee(decoratee)
    .decorated(decoratee -> decorator1Factory.create(decoratee, param1))
    .decorated(decoratee -> Decorator2.createInstance(decoratee, param2))
    .decorated(decoratee -> new Decorator3(decoratee, param3))
    .complete();
```

Even more, the intention would be more clear if we named the decorator functions appropriately:
```java
Function withDecorator1 = decoratee -> decorator1Factory.create(decoratee, param1);
Function withDecorator2 = decoratee -> Decorator2.createInstance(decorated, param2);
Function withDecorator3 = decoratee -> new Decorator3(decorated, param3);

Object decorated = PointWave.decoratee(decoratee)
    .decorated(withDecorator1)
    .decorated(withDecorator2)
    .decorated(withDecorator3)
    .complete();
```

## Todo

- abstract `Decoratee` as interface
- refactor the code to functional style
- use `Github Actions` to build and test the code
- write`Javadoc`
  
## Note
- use `Git Flow` in this project
- incremental delivery