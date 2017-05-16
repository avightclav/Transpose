import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static junit.framework.Assert.assertEquals;

public class TransposerTest {
    private static File txtFile;
    private static File divFile;
    private static File emptyFile;
    private static File outFile;

    private static FileReader txtReader;
    private static FileReader divReader;
    private static FileReader emptyReader;

    private static FileWriter outWriter;

    @Before
    public void Before() throws IOException {
        txtFile = new File("test/text.txt");
        divFile = new File("test/diversion.txt");
        emptyFile = new File("test/empty.txt");

        txtReader = new FileReader(txtFile);
        divReader = new FileReader(divFile);
        emptyReader = new FileReader(emptyFile);

        outFile = new File("test/out.txt");
        outWriter = new FileWriter(outFile);
    }

    @After
    public void After() throws IOException {
        System.gc();
        outFile.delete();
    }

    private void assertFileContent(String expectedContent, File file) throws FileNotFoundException {
        String reader = new BufferedReader(new FileReader(file)).lines().collect(Collectors.joining("\n"));
        assertEquals(expectedContent, reader);
    }


    @Test
    public void emptyFileTest() throws IOException {
        new Transposer(3, true, true).transpose(emptyReader, outWriter);
        assertFileContent("", outFile);

    }

    @Test
    public void widthTest() throws IOException {
        new Transposer(3, false, false).transpose(divReader, outWriter);
        assertFileContent("How Even As  When You And\n" +
                "do  if  I   it's won't keep\n" +
                "I   you admire dark let a  \n" +
                "get don't you outside anybody padlock\n" +
                "close notice on  your out on \n" +
                "to  the house your\n" +
                "you subway door", outFile);
    }

    @Test
    public void alignTest() throws IOException {
        new Transposer(8, true, false).transpose(txtReader, outWriter);
        assertFileContent("  AaaBbb   MmmNnn       67\n" +
                "  ZzzAaa        C\n" +
                "       4", outFile);

    }

    @Test
    public void cutoffTest() throws IOException {
        new Transposer(2, false, true).transpose(txtReader, outWriter);
        assertFileContent("Aa Mm 67\n" +
                "Zz C \n" +
                "4 ", outFile);

    }

    @Test
    public void cutoffAlignTest() throws IOException {
        new Transposer(2, true, true).transpose(txtReader, outWriter);
        assertFileContent("Aa Mm 67\n" +
                "Zz  C\n" +
                " 4", outFile);

    }

}