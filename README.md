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


## 差異比較
若不使用任何套件，直接手動裝飾物件時，程式碼可能會擠在一行，或者形成金字塔的形狀。以上這2種狀況都讓程式碼難以閱讀：
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

若使用PointWave，則可以利用lambda表達式，將程式碼「攤平」，此時程式碼相當容易閱讀：
```java
Object decorated = PointWave.decoratee(decoratee)
    .decorated(decoratee -> decorator1Factory.create(decoratee, param1))
    .decorated(decoratee -> Decorator2.createInstance(decoratee, param2))
    .decorated(decoratee -> new Decorator3(decoratee, param3))
    .complete();
```

使用區域變數的方式也能達到類似的可讀性：
```java
Object decorated = decoratee;
decorated = decorator1Factory.create(decorated, param1);
decorated = Decorator2.createInstance(decorated, param2);
decorated = new Decorator3(decorated, param3);
```

但是透過適當的函數名稱，PointWave的API更能表達其意圖：
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

- 抽象化`Decoratee`為介面
- 重構為函數式風格
- 導入`Github Actions`，用於建構與測試
- 撰寫`Javadoc`
  
## Note
- 以`Git Flow`管理本專案
- 增量式開發