package com.dbs.sae.training.nerddinner.domain;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ValidationGeneratorTest {

    TestObject gns;

    @Before
    public void setUp() throws Exception {
        gns = new TestObject();
        gns.setPropertyOne("P1");

        List<InnerTestObject> innderObjects = new ArrayList<>();
        innderObjects.add(createInnerObject(1, "test1"));
        innderObjects.add(createInnerObject(2, "test2"));
        gns.setInnerObjects(innderObjects);
    }

    @Test
    public void ShouldApplyNotEmptyAnnotations() throws JSONException {
        ResourceBundle rb = ResourceBundle.getBundle("ValidationMessages", new Locale("es-ES"));
        ValidationGenerator vg = new ValidationGenerator(rb);
        JSONObject validationRules = vg.getValidationRules(gns);
        JSONObject messages = validationRules.getJSONObject("messages");
        JSONObject rules = validationRules.getJSONObject("rules");

        assertNotNull(messages);
        JSONObject messagePropertyOne = messages.getJSONObject("propertyOne");
        assertNotNull(messagePropertyOne);
        assertEquals(
                rb.getString("org.hibernate.validator.constraints.NotEmpty.message"),
                messagePropertyOne.getString("required"));
        JSONObject innerPropertyOne = messages.getJSONObject("innerObjects[0].innerPropertyOne");
        assertNotNull(innerPropertyOne);
        assertEquals(
                rb.getString("org.hibernate.validator.constraints.NotEmpty.message"),
                messagePropertyOne.getString("required"));


        assertNotNull(rules);
        JSONObject rulePropertyOne = rules.getJSONObject("propertyOne");
        JSONObject ruleInnerPropertyOne = rules.getJSONObject("innerObjects[0].innerPropertyOne");

        assertNotNull(rulePropertyOne);
        assertNotNull(ruleInnerPropertyOne);

        assertTrue(rulePropertyOne.getBoolean("required"));
        assertTrue(ruleInnerPropertyOne.getBoolean("required"));
    }

    @Test
    public void ShouldApplySizeAnnotations() throws JSONException {
        ResourceBundle rb = ResourceBundle.getBundle("ValidationMessages", new Locale("es-ES"));
        ValidationGenerator vg = new ValidationGenerator(rb);
        JSONObject validationRules = vg.getValidationRules(gns);
        JSONObject rules = validationRules.getJSONObject("rules");
        JSONObject rulesPropertyOne = rules.getJSONObject("propertyOne");
        JSONArray rangelength = rulesPropertyOne.getJSONArray("rangelength");
        assertEquals(rangelength.get(0), 0);
        assertEquals(rangelength.get(1), 10);
    }


    private InnerTestObject createInnerObject(int index, String val) {
        InnerTestObject gnd = new InnerTestObject();
        gnd.setInnerPropertyOne(val);
        gnd.setInnerPropertyTwo(index);
        return gnd;
    }
}

