package nerddinner.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Arrays;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NerdValidationResultTests {

    @Autowired
    private Validator validator;

    @Test
    public void ShouldBeAbleToRunValidationServerSide() {
        TestObject to = new TestObject();
        InnerTestObject tio = new InnerTestObject();
        to.setInnerObjects(Arrays.asList(tio));
        Set<ConstraintViolation<TestObject>> result = validator.validate(to);

    }
}
