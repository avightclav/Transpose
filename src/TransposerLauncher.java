import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.CmdLineParser;
import java.io.File;
import java.io.IOException;


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
            transposer.transpose(ifile, ofile);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
