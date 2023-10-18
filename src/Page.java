package src;
import java.util.LinkedList;
import java.util.List;

class Page {
    boolean inMemory;       
    int frame;              
    byte referenceByte;   

    public Page() {
        this.inMemory = false;
        this.frame = -1;
        this.referenceByte = 0;
    }
}


