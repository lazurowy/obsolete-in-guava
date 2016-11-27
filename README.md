// TODO introduction

Optional
-----------------

JDK 8+ java.util.Optional and guava 10+ com.google.common.base.Optional are extremely similar, but unfortunetly incompatible. They do not shear common supertype and conversion between them generate lot of boilerplace code.

Main differences:
| com.google.common.base.Optional      | java.util.Optional                                               |
| ------------------------------------ | ---------------------------------------------------------------- |
| implements java.io.Serializable      |                                                                  |
|                                      | has primitive-specialized versions: java.util.OptionalInt, java.util.OptionalLong, java.util.OptionalDouble |
| method asSet                         | 		                                                          |
| static util presentInstances         | 		                                                          |
|                                      | additional methods: ifPresent, filter, flatMap and orElseThrow   |
|                                      | additional jdk 9 methods: ifPresentOrElse, stream, or            |

Corresponding methods
| com.google.common.base.Optional<T>                    | java.util.Optional<T>                                                                 | Note            |
|-------------------------------------------------------|---------------------------------------------------------------------------------------|-----------------|
| static <T> Optional<T> absent()                       | static <T> Optional<T> empty()                                                        |                 |
| static <T> Optional<T> of(T reference)                | static <T> Optional<T> of(T value)                                                    |                 |
| static <T> Optional<T> fromNullable(@Nullable T ref)  | static <T> Optional<T> ofNullable(T value)                                            |                 |
|                                                       | void ifPresent(Consumer<? super T> consumer)                                          |                 |
| boolean isPresent()                                   | boolean isPresent()                                                                   |                 |
| T get()                                               | T get()                                                                               | if there is no value present guava throws IllegalStateException, jdk throws NoSuchElementException |
| T or(T defaultValue)                                  | T orElse(T other)                                                                     |                 |
| T orNull()                                            |                                     	                                                | method exists in guava mainly because of 'or' method is overloaded and passing null to it require casting it to proper type |
| T or(Supplier<? extends T> supplier)                  | T orElseGet(Supplier<? extends T> other)                                              | guava throws NullPointerException if suppier returns null, while jdk just return empty Optional; guava allways check if suppier is not null, while jdk do it only when optional value is not present |
|                                                       | <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier) throws X |                 |
| Optional<T> or(Optional<? extends T> secondChoice)    |                                                                                       |                 |
| Optional<U> transform(Function<? super T, U> function)| Optional<U> map(Function<? super T, ? extends U> mapper)                              | guava throws NullPointerException if transforming function returns null, while jdk just return empty Optional |
|                                                       | Optional<T> filter(Predicate<? super T> predicate)                                    |                 |
|                                                       | Optional<U> flatMap(Function<? super T, Optional<U>> mapper)                          |                 |
| boolean equals(@Nullable Object object)               | boolean equals(Object obj)                                                            |                 |
| int hashCode()                                        | int hashCode()                                                                        |                 |
| String toString()                                     | String toString()                                                                     |                 |
| static <T> Iterable<T> presentInstances               |                                      	                                                | handy when dealing with Iterator of Optional |
|                                                       | Stream<T> stream()                                     	                            | jdk 9+ only, handy when deling with Stream of Optional |
| Set<T> asSet()                                        |                                      	                                                |                 |
|                                                       | Optional<T> or(Supplier<? extends Optional<? extends T>> supplier)                    | jdk 9+ only     |
|                                                       | void ifPresentOrElse(Consumer<? super T> action, Runnable emptyAction)                | jdk 9+ only     |

Conversion between  java.util.Optional and com.google.common.base.Optional
```java
java.util.Optional<?> jdkOptional;
com.google.common.base.Optional<?> guavaOptional;

// some intialization

jdkOptional = guavaOptional
		.transform(java.util.Optional::of)
		.or(java.util.Optional::empty);

guavaOptional = jdkOptional
		.map(com.google.common.base.Optional::of)
		.orElseGet(com.google.common.base.Optional::absent);
```


2. List/Map builder methods
-----------------

// TODO

3. Preconditions
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

4. Joiner 
-----------------
// TODO

5. Ordering
-----------------
// TODO

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
