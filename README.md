# Pidove: alternative to the Java Streams API

I've long been a fan of Functional interfaces in JDK 8 but I felt that the Streams API incorporated a
few mistakes.  In particular it added a lot of complexity in the name of parallel processing, yet, the parallel
processing implementation doesn't automatically scale itself to match the workload and CPU cores.

*Pidove* in particular implements most of the functions that the Streams API defines on the
*Stream* object as static methods that act on *Iterable*.  Python (including the *itertools* and *collections*) and
the OCAML standard library (particularly *seq*) are major inspirations.  *Pidove* also reuses parts of the stream
facility that I like,  like *Collector*,  which really has nothing wrong with it.

## Sane closing

Closing files and other resources is a problem in other stream libraries.  Pidove's viewpoint
is that is wrong to close the Iterable (or Stream) object because the Iterable is a factory for
Iterators and that any resources associated with the Iterator are created when the Iterable
creates the Iterator.  To this end, pidove provides a forEach() method that closes the
Iterator if it implements AutoClosable,  and methods such as collect() and asList() do the same.
As applicable,  methods such as concat() and map() pass on close() calls to the Iterators
that they contain.

## Sample code

```java
    over("bushfire").forEach(System.out::println);
    sum(filter(x->x%2==0,List.of(5,3,4,19,75,6)));
    collect(groupingBy(word->word.charAt(0)),words);
```

