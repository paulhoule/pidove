package com.ontology2.pidove;

import com.ontology2.pidove.util.Janus;
import com.ontology2.pidove.util.Null;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.ontology2.pidove.util.Null.*;
import static com.ontology2.pidove.util.Partial.partialR;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TestCoalesce {
    @Test
    public void c2() {
        assertNull(coalesce(null,null));
        assertEquals(7, coalesce(7, 15));
        assertEquals(15, coalesce(null, 15));
        assertEquals(7, coalesce(7, null));
    }

    @Test
    public void c3() {
        assertNull(coalesce(null,null, null));
        assertEquals(7, coalesce(7, 15, 64));
        assertEquals(15, coalesce(null, 15, 64));
        assertEquals(64, coalesce(null, null, 64));
    }

    @Test
    public void c5() {
        assertEquals(7, coalesce(7, 15, 64, 155, 768));
        assertEquals(15, coalesce(null, 15, 64, 155, 768));
        assertEquals(64, coalesce(null, null, 64, 155, 768));
        assertEquals(155, coalesce(null, null, null, 155, 768));
        assertEquals(768, coalesce(null, null, null, null, 768));
        assertNull(coalesce(null,null, null, null, null));
    }

    @Test
    public void testSafe() {
        assertEquals(7, safe("fingers", String::length));
        assertNull(safe(null, String::length));
        assertEquals(7.0, safe("fingers", String::length, Integer::doubleValue));
        assertNull(safe(null, String::length, Integer::doubleValue));
        assertNull(safe("boom!", x -> null, Integer::doubleValue));
        //
        // note that ambiguity problem with method references turns up here;  you can't do the
        // same thing I do with String::repeat with String::replace because String::replace,  like
        // many of the methods in String,  comes in different forms
        //
        assertEquals(27,safe("number 09",partialR(String::repeat, 3),String::length));

        var j =new Janus<Object>("wow");
        safe0("system",j.consumer());
        assertEquals("system", j.supplier().get());
        safe0(null, j.consumer());
        assertEquals("system", j.supplier().get());

        safe0("wow", x->x.replace('o','0'), j.consumer());
        assertEquals("w0w", j.supplier().get());

        var threeFold = asList(safe("system",
                x->x.replace('s','-'),
                x->x.repeat(2),
                x->x.split("-")
        ));
        assertEquals(List.of("","y","tem","y","tem"), threeFold);

        safe0("ウィキペディア日本語版",String::getBytes,x->x.length,j.consumer());
        assertEquals(33, j.supplier().get());
        safe0("ウィキペディア日本語版",String::getBytes,x->null,j.consumer());
        assertEquals(33, j.supplier().get());
        safe0("ウィキペディア日本語版",x->(byte[]) null,x->x.length,j.consumer());
        assertEquals(33, j.supplier().get());
        safe0((String) null,String::getBytes,x->x.length,j.consumer());
        assertEquals(33, j.supplier().get());
    }
}
