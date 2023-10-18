package src;
public class Reference {
    String matrixLabel;  
    int i;  
    int j;  
    int virtualPage;
    int offset;

    public Reference(String matrixLabel, int i, int j, int virtualPage, int offset) {
        this.matrixLabel = matrixLabel;
        this.i = i;
        this.j = j;
        this.virtualPage = virtualPage;
        this.offset = offset;
    }
    
    @Override
    public String toString() {
        return String.format("[%s-%d-%d],%d,%d", matrixLabel, i, j, virtualPage, offset);
    }
}
