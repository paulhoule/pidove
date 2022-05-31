package com.ontology2.pidove.util;

/**
 * A typed collection of values.
 *
 * Tuples in Java are different from tuples in Python because Java tuples are typed and because of this,  this
 * class defines remarkably little functionality.  We could implement a positional get() but it would be no
 * fun because it would have to return Object,  unless we had special subtypes for Tuples where all the members
 * have the same type
 *
 * Pair and Trios are the only Tuple types defined.  More could be added but beyond a certain point it is
 * probably makes sense to make custom record types with meaningful names.
 *
 */
public sealed interface  Tuple<First,Last> permits Pair,Trio {
    int size();
    First first();
    Last last();
}
