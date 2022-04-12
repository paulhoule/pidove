# Pidove: alternative to the Java Streams API

I've long been a fan of Functional interfaces in JDK 8 but I felt that the Streams API incorporated a
few mistakes.  In particular it added a lot of complexity in the name of parallel processing, yet, the parallel
processing implementation doesn't automatically scale itself to match the workload and CPU cores.

*Pidove* in particular implements most of the functions that the Streams API defines on the
*Stream* object as static methods that act on *Iterable*.  Python (including the *itertools* and *collections*) and
the OCAML standard library (particularly *seq*) are major inspirations.  *Pidove* also reuses parts of the stream
facility that I like,  like *Collector*,  which really has nothing wrong with it.

## Sample code

```java
import static com.ontology2.pidove.checked.Iterables.*;

over("bushfire").forEach(System.out::println);
sum(filter(x-> x%2 == 0,List.of(5,3,4,19,75,6)));
collect(groupingBy(word-> word.charAt(0)), words);
```

