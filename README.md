# PointWave [![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
使用Decorator模式時的工具套件，可有效提升程式碼的可讀性。

## 範例
首先呼叫`PointWave.decoratee`初始化被裝飾的物件，再依序呼叫`decorated`方法，將裝飾器函數傳入。最後呼叫`complete`方法，此時便會依序將物件裝飾後返回：
```java
Object decorated = PointWave.decoratee(decoratee)
    .decorated(decoratee -> decorator1Factory.create(decoratee, param1))
    .decorated(decoratee -> Decorator2.createInstance(decoratee, param2))
    .decorated(decoratee -> new Decorator3(decoratee, param3))
    .complete();
```
裝飾器函數使用lambda表達式來表達，參數為被裝飾物件，返回值為裝飾完畢後的物件。

裝飾的順序是先傳入的先裝飾。以此範例來說，最內層是`decoratee`，第二層是`Decorator1`，第三層是`Decorator2`，最外層是`Decorator3`。

### 支援泛型
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

decorated.toString(); // Hello World! John Doe! Today is Friday!
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