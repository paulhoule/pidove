package com.ontology2.pidove.iterables;

import java.io.InputStream;
import java.util.function.Supplier;

@FunctionalInterface
interface SupplierOfInputStream extends Supplier<InputStream> {
}
