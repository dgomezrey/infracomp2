package src;
import java.util.ArrayList;
import java.util.List;

public class MatrixMultiplication {
    private int nf1;
    private int nc1;
    private int nc2;
    private int pageSize;
    private int[][] mat1, mat2, mat3;

    public MatrixMultiplication(int nf1, int nc1, int nc2, int pageSize) {
        this.mat1 = new int[nf1][nc1];
        this.mat2 = new int[nc1][nc2];
        this.mat3 = new int[nf1][nc2];
        this.nf1 = nf1;
        this.nc1 = nc1;
        this.nc2 = nc2;
        this.pageSize = pageSize;
    }


    public List<Reference> generatePageReferences() {
        List<Reference> references = new ArrayList<>();
    
        for (int i = 0; i < this.nf1; i++) {
            for (int j = 0; j < this.nc2; j++) {
                for (int k = 0; k < this.nc1; k++) {
                    references.add(new Reference("A", i, k, getPageReference("A", i, k, this.nc1), getOffset("A", i, k, this.nc1)));
                    references.add(new Reference("B", k, j, getPageReference("B", k, j, this.nc2), getOffset("B", k, j, this.nc2)));
                }
                references.add(new Reference("C", i, j, getPageReference("C", i, j, this.nc2), getOffset("C", i, j, this.nc2)));
            }
        }
        return references;
    }
    

    private int getPageReference(String matrixLabel, int i, int j, int M) {
        int linearIndex;
        if (matrixLabel.equals("A")) {
            linearIndex = i * M + j;
        } else if (matrixLabel.equals("B")) {
            linearIndex = nf1 * nc1 + i * M + j;  
        } else {  
            linearIndex = nf1 * nc1 + nc1 * nc2 + i * M + j;  
        }
        int address = linearIndex * 4; //4 bytes por entero
        return address / pageSize; 
    }
    
    private int getOffset(String matrixLabel, int i, int j, int M) {
        int linearIndex;
        if (matrixLabel.equals("A")) {
            linearIndex = i * M + j;
        } else if (matrixLabel.equals("B")) {
            linearIndex = nf1 * nc1 + i * M + j;  
        } else {  
            linearIndex = nf1 * nc1 + nc1 * nc2 + i * M + j;  
        }
        int address = linearIndex * 4;  
        return address % pageSize;  
    }
    

}
