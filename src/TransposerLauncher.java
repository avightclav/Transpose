import com.sun.org.apache.xerces.internal.impl.io.ASCIIReader;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.CmdLineParser;
import sun.misc.IOUtils;
import sun.nio.ch.IOUtil;

import java.io.*;
import java.util.Scanner;


public class TransposerLauncher {
    @Argument(metaVar = "ifile", usage = "Input file name")
    private File ifile;

    @Option(name = "-o", metaVar = "ofile", usage = "Output file name")
    private File ofile;

    @Option(name = "-a", metaVar = "width", usage = "The width of each field")
    private int width;

    @Option(name = "-t", metaVar = " cutoff", usage = "Cut to width")
    private boolean cutoff;

    @Option(name = "-r", metaVar = "align", usage = "Aligning by right side")
    private boolean align;

    public static void main(String[] args) {
        new TransposerLauncher().launch(args);
    }

    private void launch(String[] args) {
        CmdLineParser parser = new CmdLineParser(this);
        try {
            parser.parseArgument(args);
            if ((cutoff || align) && (width == 0))
                width = 10;
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("java -jar transpose.jar file -o ofile -a width -t cutoff -r align");
            parser.printUsage(System.err);
            return;
        }
        Transposer transposer = new Transposer(width, align, cutoff);
        try {
            Reader reader = (ifile == null) ? new InputStreamReader(System.in) : new FileReader(ifile);
            Writer writer  = (ofile == null) ? new OutputStreamWriter(System.out) : new FileWriter(ofile);
            transposer.transpose(reader, writer);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
