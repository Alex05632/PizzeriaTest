package ru.test;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.Pizza;
import ru.test.utils.HelperUtil;

import java.util.Arrays;
import java.util.Collection;

@RunWith(value = Parameterized.class)
public class PositiveTest {
    private static final Logger LOG = LoggerFactory.getLogger(PositiveTest.class);
    public String fileName;

    public PositiveTest(String fileName) {
        this.fileName = fileName;
    }

    @Parameterized.Parameters
    public static Collection<String> params() {
        return Arrays.asList("1true.json", "2true.json", "3true.json");
    }

    @Test
    public void test() {
        Pizza pizza = new Pizza();
        LOG.info("fileName" + fileName);
        Assert.assertTrue(pizza.getPizza(HelperUtil.getRecipe(fileName)));
    }
}
