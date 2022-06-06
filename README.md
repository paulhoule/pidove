# Pidove: alternative to the Java Streams API

I've long been a fan of Lambdas and Functional interfaces introduced in JDK 8 but I felt that the Streams API added
significant complexity with insufficient return.  Particularly,  it is impossible to add new operators to the stream
API.  Although the Stream API's approach of building a collection of stream operators allows the use of compiler
techniques to globally optimize a pipeline,  it has insufficient visibility into the functions that it takes as
arguments to methods like `map` and `filter` to optimize as well as a relational database.  The Streams API fails to
deliver efficient and scalable parallelism and,  in the effort of doing so, neglects useful order-dependent operators such as 
[pairwise](https://paulhoule.github.io/pidove/apidocs/com/ontology2/pidove/iterables/Iterables.html#pairwise(java.lang.Iterable)) 
and [zip](https://paulhoule.github.io/pidove/apidocs/com/ontology2/pidove/iterables/Iterables.html#zip(java.lang.Iterable,java.lang.Iterable))

Instead of creating a new interface for Streams *Pidove* implements a [set of static methods](https://paulhoule.github.io/pidove/apidocs/com/ontology2/pidove/iterables/Iterables.html) that act on
[Iterable](https://docs.oracle.com/en/java/javase/18/docs/api/java.base/java/lang/Iterable.html).  *Pidove* offers
nearly all the methods that the Stream API does and is compatible with [Collectors](https://docs.oracle.com/en/java/javase/18/docs/api/java.base/java/util/stream/Collectors.html)
from the Stream API.  In addition to some unique operators such as [a windowing operator](https://paulhoule.github.io/pidove/apidocs/com/ontology2/pidove/iterables/Iterables.html#window(int,java.util.stream.Collector,java.lang.Iterable)),  *pidove* also implements a operators of methods 
from Python's [itertools](https://docs.python.org/3.11/library/itertools.html) such as [accumulate](https://paulhoule.github.io/pidove/apidocs/com/ontology2/pidove/iterables/Iterables.html#accumulate(java.util.function.BinaryOperator,java.lang.Iterable))
and [product](https://paulhoule.github.io/pidove/apidocs/com/ontology2/pidove/iterables/Iterables.html#product(java.lang.Iterable,java.lang.Iterable)).  Other
Pythonisms include an implementation of [defaultdict](https://paulhoule.github.io/pidove/apidocs/com/ontology2/pidove/util/DefaultMap.html) as well
as uniform [indexing](https://paulhoule.github.io/pidove/apidocs/com/ontology2/pidove/iterables/Iterables.html#at(int,java.lang.Iterable))
and [length](https://paulhoule.github.io/pidove/apidocs/com/ontology2/pidove/iterables/Iterables.html#len(java.lang.CharSequence)) for common types
as well as a [range](https://paulhoule.github.io/pidove/apidocs/com/ontology2/pidove/iterables/Iterables.html#range(long)) generator.

Unlike the Stream API,  *pidove* has a [sane strategy for closing resources](https://paulhoule.github.io/pidove/apidocs/com/ontology2/pidove/iterables/TidyIterable.html). 
So long as an [Iterable](https://docs.oracle.com/en/java/javase/18/docs/api/java.base/java/lang/Iterable.html)
returns an [Iterator](https://docs.oracle.com/en/java/javase/18/docs/api/java.base/java/util/Iterator.html) that implements
[AutoCloseable](https://docs.oracle.com/en/java/javase/18/docs/api/java.base/java/lang/AutoCloseable.html), *pidove* will
close it when an operation like [collect](https://paulhoule.github.io/pidove/apidocs/com/ontology2/pidove/iterables/Iterables.html#collect(java.util.stream.Collector,java.lang.Iterable)),
[forEach](https://paulhoule.github.io/pidove/apidocs/com/ontology2/pidove/iterables/TidyIterable.html#forEach(java.util.function.Consumer)),
or [asMap](https://paulhoule.github.io/pidove/apidocs/com/ontology2/pidove/iterables/Iterables.html#asMap(java.lang.Iterable))
completes on a derived [Iterable](https://docs.oracle.com/en/java/javase/18/docs/api/java.base/java/lang/Iterable.html).


In addition to a powerful set of operators on [Iterable](https://docs.oracle.com/en/java/javase/18/docs/api/java.base/java/lang/Iterable.html),
*pidove* includes static methods for [composing functions](https://paulhoule.github.io/pidove/apidocs/com/ontology2/pidove/util/Composer.html),
[partial application](https://paulhoule.github.io/pidove/apidocs/com/ontology2/pidove/util/Partial.html),
[handling nulls](https://paulhoule.github.io/pidove/apidocs/com/ontology2/pidove/util/Null.html),
and [controlling exceptions](https://paulhoule.github.io/pidove/apidocs/com/ontology2/pidove/util/DuctTape.html) and more.
All in a library with no dependencies other than the Java standard libraries.

## Sample code

```java
    over("bushfire").forEach(System.out::println);
    sum(filter(x->x%2==0,List.of(5,3,4,19,75,6)));
    collect(groupingBy(word->word.charAt(0)),words);
```

