package ru.test.utils;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;

public class HelperUtil {
    private static final Logger LOG = LoggerFactory.getLogger(HelperUtil.class);
    public static String getRecipe(String fileName) {
        String json = "";
        try (FileReader reader = new FileReader("src/test/resources/jsons/" + fileName)) {
            json = new JSONParser().parse(reader).toString();
        } catch (ParseException | IOException e) {
            LOG.error("Ошибка чтения json-файла",e);
            Assert.fail("Ошибка чтения json-файла");
        }
        return json;
    }
}
