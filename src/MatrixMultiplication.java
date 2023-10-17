//Daniel Gomez Rey - 202122586

import java.io.*;
import java.util.*;

public class MatrixMultiplication {

    private int rowsA;
    private int colsA;
    private int colsB;
    private int pageSize;
    private List<String> pageReferences;

    public MatrixMultiplication(int rowsA, int colsA, int colsB, int pageSize) {
        this.rowsA = rowsA;
        this.colsA = colsA;
        this.colsB = colsB;
        this.pageSize = pageSize;
        this.pageReferences = new ArrayList<>();
    }

    public void generatePageReferences() {
        for (int i = 0; i < rowsA; i++) {
            for (int j = 0; j < colsB; j++) {
                for (int k = 0; k < colsA; k++) {
                    // Acceder a elemtno A[i][k]
                    pageReferences.add("A " + getPageNumber(i, k));
                    // Acceder a elemtno B[k][j]
                    pageReferences.add("B " + getPageNumber(k, j));
                }
            }
        }
    }

    public void saveReferencesToFile(String filename) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        writer.write("TP: " + pageSize + "  ");
        writer.write("NF: " + rowsA + "  ");
        writer.write("NC1: " + colsA + "  ");
        writer.write("NC2: " + colsB + "  ");
        writer.write("NR: " + pageReferences.size() + "  ");
        writer.write("NP: " + getTotalPages() + "\n");

        for (String ref : pageReferences) {
            writer.write(ref + "\n");
        }

        writer.close();
    }

    private int getPageNumber(int row, int col) {
        int position = (row * colsA + col) * 4; // 4 bytes por cada integer
        return position / pageSize;
    }

    private int getTotalPages() {
        int totalElements = 3 * rowsA * colsA; // Para las matrices A, B, y C
        int totalBytes = totalElements * 4; // 4 bytes por cada integer
        int totalPages = totalBytes / pageSize;
        if (totalBytes % pageSize != 0) {
            totalPages++; // Anadir pagina adicional si no es multiplo del tamaño de pagina
        }
        return totalPages;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("---- Menú ----");
            System.out.println("1. Generación de las referencias");
            System.out.println("2. Calcular el número de fallas de página");
            System.out.println("q. Salir");
            System.out.print("Seleccione una opción: ");
            String choice = scanner.next();

            switch (choice) {
                case "1":
                    System.out.println("Ingrese el tamaño de página:");
                    int pageSize = scanner.nextInt();

                    System.out.println("Ingrese el número de filas de la matriz A:");
                    int rowsA = scanner.nextInt();

                    System.out.println("Ingrese el número de columnas de la matriz A (y filas de la matriz B):");
                    int colsA = scanner.nextInt();

                    System.out.println("Ingrese el número de columnas de la matriz B:");
                    int colsB = scanner.nextInt();

                    MatrixMultiplication matrixMul = new MatrixMultiplication(rowsA, colsA, colsB, pageSize);
                    matrixMul.generatePageReferences();

                    System.out.println("Ingrese el nombre del archivo para guardar las referencias:");
                    String filename = scanner.next();

                    try {
                        matrixMul.saveReferencesToFile(filename);
                        System.out.println("Referencias guardadas exitosamente en: " + filename);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;

                case "2":
                    // TODO: Implementar la lógica para calcular el número de fallas de página
                    System.out.println("Esta opción aún no está implementada.");
                    break;

                case "q":
                    System.out.println("Saliendo...");
                    return;

                default:
                    System.out.println("Opción no reconocida. Intente de nuevo.");
            }
        }
    }
}
