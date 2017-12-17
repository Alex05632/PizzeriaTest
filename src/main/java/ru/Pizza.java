package ru;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class Pizza {
    private static final Logger LOG = LoggerFactory.getLogger(Pizza.class);
    public boolean getPizza(String jsonIngredients) {
       return getPizzaWithIngredient(getRecipe(jsonIngredients), SchemaXLS
               .getSchemaXLS()
               .getSchema());
    }

    private boolean getPizzaWithIngredient(List<String> listIngredients, Map<String, Map<String, String>> schema) {
        boolean ret = true;
        if (listIngredients.isEmpty()) return false;
        if (schema.isEmpty()) return false;
        for (int i = 0; i < listIngredients.size()-1; i++) {
            for (int j = 1+i; j < listIngredients.size(); j++) {
                System.out.println(i+" "+j+"; "+listIngredients.get(i) +" " +listIngredients.get(j) +" " +schema.get(listIngredients.get(i)).get(listIngredients.get(j)));
                System.out.println(schema.get(listIngredients.get(i))+ "!!!" +schema.get(listIngredients.get(j))+"\n________");
                if (!Boolean.parseBoolean(schema.get(listIngredients.get(i)).get(listIngredients.get(j)))) {
                    return false;
                }
            }
        }
        return ret;
    }

    private List<String> getRecipe(String json) {
        JSONParser parser = new JSONParser();
        JSONObject object = null;
        try {
            object = (JSONObject) parser.parse(json);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (List<String>) object.get("ingredients");
    }
}
