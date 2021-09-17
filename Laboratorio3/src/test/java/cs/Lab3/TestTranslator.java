package cs.Lab3;

import org.testng.annotations.Test;
import org.testng.Assert;
import java.io.IOException;

public class TestTranslator {
    @Test
    public void testTranslate() throws IOException {
        Translator translator = new Translator();
        String textTranslated = translator.translate(Main.fromLang, Main.toLang, "Hello, World");
        Assert.assertEquals(textTranslated, "Hola Mundo");
    }
}
