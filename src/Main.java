package src;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("===== Sistema de Paginación =====");
            System.out.println("1. Generación de las referencias.");
            System.out.println("2. Calcular el número de fallas de página.");
            System.out.println("3. Salir.");
            System.out.print("Seleccione una opción: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    generateReferences();
                    break;
                case 2:
                    calculatePageFaults();
                    break;
                case 3:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, seleccione nuevamente.");
            }
        }
    }

    private static void generateReferences() {
        System.out.print("Ingrese tamaño de página: ");
        int pageSize = scanner.nextInt();

        System.out.print("Ingrese número de filas de la matriz 1: ");
        int nf1 = scanner.nextInt();

        System.out.print("Ingrese número de columnas de la matriz 1 y número de filas de la matriz 2: ");
        int nc1 = scanner.nextInt();

        System.out.print("Ingrese número de columnas de la matriz 2: ");
        int nc2 = scanner.nextInt();

        MatrixMultiplication matrixMul = new MatrixMultiplication(nf1, nc1, nc2, pageSize);
        List<Reference> references = matrixMul.generatePageReferences();

        System.out.print("Ingrese nombre del archivo para guardar las referencias: ");
        String fileName = scanner.next();

        FileUtils.saveReferencesToFile(references, "./tests/"+fileName, pageSize, nf1, nc1, nc2);

        System.out.println("Referencias guardadas exitosamente en " + fileName);
    }


    private static void calculatePageFaults() {
        System.out.print("Ingrese número de marcos de página: ");
        int numFrames = scanner.nextInt();
    
        System.out.print("Ingrese nombre del archivo de referencias: ");
        String fileName = scanner.next();
    
        List<Integer> references = FileUtils.readReferencesFromFile("./tests/"+fileName);
    
        int numPages = (references.get(references.size() - 1) + 1); 
        PagingSystem pagingSystem = new PagingSystem(numPages, numFrames, references);
    
        int totalPageFaults = pagingSystem.simulate();
    
        System.out.println("Total de fallas de página: " + totalPageFaults);
    }
    
    
}
