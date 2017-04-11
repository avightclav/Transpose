import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Transposer {
    int width;
    boolean cutoff;
    boolean align;

    Transposer(int width, boolean align, boolean cutoff) {
        this.width = width;
        this.align = align;
        this.cutoff = cutoff;
    }

    public void transpose(File inputFile, File outputFile) throws IOException {
        write(this.read(inputFile), outputFile);
    }

    private List<List<String>> read(File inputFile) throws IOException {
        List<List<String>> list = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        String tempString = reader.readLine();
        String align = this.align ? "" : "-";

        while (tempString != null) {
            List<String> words = Arrays.asList(tempString.split("[ ]+"));
            int i = 0;
            for (String w : words) {
                w = String.format("%" + align + width + "s", w);
                if (cutoff)
                    w = w.substring(0, width);
                if (list.size() <= i) {
                    list.add(new ArrayList<String>());
                }
                list.get(i).add(w);
                i++;
            }
            tempString = reader.readLine();
        }
        return list;
    }

    private static void write(List<List<String>> writeList, File outputFile) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
        int i = 0;

        for (List l : writeList) {
            StringBuilder sb = new StringBuilder();
            for (String s : writeList.get(i)) {
                sb.append(s + " ");
            }
            i++;
            writer.write(sb.toString());
            writer.newLine();
        }
        writer.close();
    }

    public static void main(String[] args) {
        File ifile = new File("text.txt");
        File outfile = new File("toxt.txt");
        Transposer trans = new Transposer(0, false, false);
        try {
            trans.transpose(ifile, outfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
