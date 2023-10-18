package src;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
    public static void saveReferencesToFile(List<Reference> references, String fileName, int pageSize, int nf1, int nc1, int nc2) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            int npMat1 = (int) Math.ceil((double) nf1 * nc1 * 4 / pageSize);
            int npMat2 = (int) Math.ceil((double) nc1 * nc2 * 4 / pageSize);
            int npMat3 = (int) Math.ceil((double) nf1 * nc2 * 4 / pageSize);
            int totalNP = npMat1 + npMat2 + npMat3;

            
            writer.write("TP=" + pageSize);
            writer.newLine();
            writer.write("NF=" + nf1);
            writer.newLine();
            writer.write("NC1=" + nc1);
            writer.newLine();
            writer.write("NC2=" + nc2);
            writer.newLine();
            writer.write("NR=" + references.size());
            writer.newLine();
            writer.write("NP=" + totalNP);
            writer.newLine();
    
            for (Reference ref : references) {
                writer.write(ref.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar las referencias en el archivo: " + e + ". Por favor, intente nuevamente.");
        }
    }
    

    public static List<Integer> readReferencesFromFile(String fileName) {
        List<Integer> references = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(fileName));
            for (String line : lines) {
                if (!line.contains("=")) {
                    String[] parts = line.split(",");
                    if (parts.length >= 2) { 
                        references.add(Integer.parseInt(parts[1].trim()));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return references;
    }
    
 
    
    
}
