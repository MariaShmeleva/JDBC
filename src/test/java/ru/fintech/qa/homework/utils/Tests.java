package ru.fintech.qa.homework.utils;

import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Arrays;

public class Tests {
    DBClient dbClient;
    @BeforeAll
    public static void beforeAll() {
        BeforeUtils.createData();
    }
    @BeforeEach
    public void beforeEach() {
        dbClient = new DBClient();
    }
    @AfterEach
    public void afterEach() {
        dbClient.close();
    }
    @Test
    public void test1() {
       Assertions.assertEquals(10, dbClient.executeCount("select count(*) totalCount from public.animal", "totalCount") );
    }
    @Test
    public void test2() {
        for (int i=1; i<11; i++) {
            Assertions.assertEquals(0, dbClient.executeUpdate(String.format("INSERT INTO public.animal (id) VALUES(%d)", i)));
        }
    }
    @Test
    public void test3() {
        Assertions.assertEquals( 0, dbClient.executeUpdate("INSERT INTO public.workman (id, \"name\", age, \"position\") VALUES(1, null, 23, 1);"));
    }
    @Test
    public void test4() {
        dbClient.executeUpdate("INSERT INTO public.places (id, \"row\", place_num, \"name\") VALUES(6, 6, 085, 'Загон 6');");
        Assertions.assertEquals(6,dbClient.executeCount("select count(*) totalCount from public.places", "totalCount" ));
    }

    @Test
    public void test5() {
        ArrayList<String> arrayList = dbClient.executeQueryColumn( "name", "select  \"name\" from public.zoo" );
        Assertions.assertEquals(3, arrayList.size());
        Assertions.assertTrue(arrayList.containsAll(Arrays.asList("Центральный", "Северный", "Западный")));
    }

}
