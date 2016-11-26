// TODO

1. Preconditions
-----------------

Methods in com.google.common.base.Preconditions:
```java
checkNotNull(T reference)
checkNotNull(T reference, Object errorMessage)
checkNotNull(T reference, String errorMessageTemplate, @Nullable Object... errorMessageArgs)
```

sequentially correspond to methods in java.util.Objects:

```java
requireNonNull(T obj)
requireNonNull(T obj, String message)
requireNonNull(T obj, Supplier<String> messageSupplier)
```

Note1: com.google.common.base.Preconditions allow message to be templated in 'printf' style
Note2: jdk do not supply other precoditions available in guava:
```java
public static void checkArgument(boolean expression, String errorMessageTemplate, Object... errorMessageArgs) {
  if (!expression) {
    throw new IllegalArgumentException(format(errorMessageTemplate, errorMessageArgs));
  }
} 

public static void checkState(boolean expression, String errorMessageTemplate,  Object... errorMessageArgs) {
  if (!expression) {
    throw new IllegalStateException(format(errorMessageTemplate, errorMessageArgs));
  }
}
```

2. Optional
-----------------

3. List/Map builder methods
-----------------

4. Joiner 
-----------------

5. Ordering
-----------------

6. Predicate/Function interfaces
-----------------

7. Build in Predicates/Functions
-----------------

8. FluentIterable
-----------------

9. CompletableFuture and ListenableFuture
-----------------

10. Mutimap inline implementation
-----------------
 

More info
https://www.reddit.com/r/java/comments/217rnf/what_is_obsolete_in_guava_since_java_8/
https://groups.google.com/forum/#!topic/guava-discuss/fEdrMyNa8tA/discussion
https://github.com/OpenGamma/OG-Commons/blob/master/modules/collect/src/main/java/com/opengamma/collect/Guavate.java
