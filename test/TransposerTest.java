import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.*;
import java.util.stream.Stream;

import static junit.framework.Assert.assertEquals;

public class TransposerTest {

    private static FileReader div;
    private static FileReader txt;

    @BeforeClass
    public static void Initial() throws FileNotFoundException {
        div = new FileReader(new File("test/diversion.txt"));
        txt = new FileReader(new File("test/text.txt"));
    }

    private void assertFileContent(String expectedContent, File file) throws FileNotFoundException {
        Stream<String> reader = new BufferedReader(new FileReader(file)).lines();
        String content = reader.reduce((s1, s2) -> s1 + "\n" + s2).get();
        assertEquals(expectedContent, content);
    }

    @Test
    public void Transpose() {
        try {
            new Transposer(8, false, false).transpose(new FileReader(new File("../test/text.txt")), new File("tuxt.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            assertFileContent(new String("AaaBbb   MmmNnn   67      \n" +
                    "ZzzAaa   C       \n" +
                    "4       "), new File("tuxt.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void Transposer1() throws IOException {
        File file = new File("taxt.txt");
        new Transposer(3, false, false).transpose(div, file);
        String s = "How Even As  When You And\n" +
                "do  if  I   it's won't keep\n" +
                "I   you admire dark let a  \n" +
                "get don't you outside anybody padlock\n" +
                "close notice on  your out on \n" +
                "to  the house your\n" +
                "you subway door";
        assertFileContent(s, file);
    }
}