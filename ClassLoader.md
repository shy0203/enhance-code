# ClassLoader

Java의 모든 코드는 런타임 시 JVM에 링크된다. 즉, 모든 클래스는 그 클래스가 참조되는 순간에 동적으로 JVM에 링크된다. 이러한 동적인 클래스 로딩은 자바의  ClassLoader에 의해 이루어진다.  

<br/>  

## ClassLoader 구조 (Java 8)

* **Bootstrap ClassLoader**는 최상위 클래스 로더로써 `jre/lib/rt.jar`에 담긴 JDK 클래스 파일을 로딩한다.
* **Extension ClassLoader**는 `jre/lib/ext` 폴더나 `java.ext.dirs` 환경변수로 지정된 폴더에 있는 클래스 파일을 로딩한다. 
* **Application ClassLoader**는 `$CLASSPATH` 속성값으로 지정된 폴더에 있는 클래스를 로딩한다.   

<br/>

## Delegegation Priciple

ClassLoader의 원칙 중 하나는 **위임**이다. 클래스를 로딩할 때 클래스 로딩을 상위 클래스로더로 위임하는 것이다. 

```java
public class HelloWorld {
     public static void main(String[] args) {
        System.out.println("HelloWorld");
     }
  }
```

위와 같은 코드가 있을 때, 먼저 JVM은 모든 클래스가 상속 받고 있는 Object 클래스를 로딩한다.   클래스 로딩은 Application ClassLoader -> Extension ClassLoader -> Bootstrap ClassLoader 로 위임된다. 따라서 가장 먼저 Bootstrap ClassLoder가 Object 클래스를 찾아 클래스를 로딩한다.   
만약 Bootstrap ClassLoader에서 찾을 수 없는 클래스라면 Extension ClassLoader로 위임되어 해당 클래스를 찾고, Extension ClassLoader에서도 못 찾으면 Application ClassLoader로 위임된다.   
Application ClassLoader에서도 클래스를 찾을 수 없는 경우 `ClassNotFound Exception`이 발생된다.

<br/>  

## getContextClassLoader()

`getContextClassLoader()`를 이용해 `java.lang.ClassLoader`의 인스턴스를 획득할 수 있다.  
ClassLoader 인스턴스는 클래스를 로딩할 뿐만 아니라 다른 리소스도 읽을 수 있는 기능을 제공한다. 

### ClassLoader.getResource()

classpath 내 해당 이름으로 된 파일을 찾아 그 URL을 리턴해준다.  

### ClassLoader.getParent()
상위 클래스 로더를 리턴해준다. 
 
--- 
#### Reference
[Java ClassLoader 훑어보기](https://homoefficio.github.io/)  
