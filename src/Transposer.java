import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Transposer {
    private final int width;
    private final boolean cutoff;
    private final boolean align;

    Transposer(int width, boolean align, boolean cutoff) {
        this.width = width;
        this.align = align;
        this.cutoff = cutoff;
    }

    public void transpose(Reader inputStream, Writer writer) throws IOException {
        write(this.read(inputStream), writer);
    }

    private List<List<String>> read(Reader inputStream) throws IOException {
        List<List<String>> list = new ArrayList<>();
        BufferedReader reader = new BufferedReader(inputStream);
        String tempString = reader.readLine();
        String align = ((this.align) || (width == 0))  ? "" : "-";

        while (tempString != null) {
            List<String> words = Arrays.asList(tempString.split("[ ]+"));
            int i = 0;
            String widthStr = width == 0 ? "" : Integer.toString(width);
            for (String w : words) {
                w = String.format("%" + align + widthStr + "s", w);
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

    private static void write(List<List<String>> writeList, Writer writer) throws IOException {
        for (List<String> l : writeList) {
            StringBuilder sb = new StringBuilder();
            for (String s : l) {
                sb.append(s);
                if (s != l.get(l.size() - 1))
                    sb.append(" ");
            }
            writer.write(sb.toString());
            if (l != writeList.get(writeList.size() - 1))
                writer.append("\n");
        }
        writer.close();
    }
}
