package src;
import java.util.LinkedList;
import java.util.List;

public class PagingSystem {
    private List<Page> pageTable;      
    private int[] frames;              
    private List<Integer> references;  
    private final Object lock = new Object();

    public PagingSystem(int numPages, int numFrames, List<Integer> references) {
        this.pageTable = new LinkedList<>();
        for (int i = 0; i < numPages; i++) {
            this.pageTable.add(new Page());
        }
        this.frames = new int[numFrames];
        for (int i = 0; i < numFrames; i++) {
            this.frames[i] = -1;  
        }
        this.references = references;
    }

    public boolean accessPage(int pageNumber) {
        Page page = pageTable.get(pageNumber);
        if (page.inMemory) {
            page.referenceByte |= 0b10000000;
            return true;
        } else {
            return false;
        }
    }
    public void handlePageFault(int pageNumber) {
        int freeFrame = -1;
        for (int i = 0; i < frames.length; i++) {
            if (frames[i] == -1) {
                freeFrame = i;
                break;
            }
        }
    
        if (freeFrame != -1) {
            frames[freeFrame] = pageNumber;
            Page page = pageTable.get(pageNumber);
            page.inMemory = true;
            page.frame = freeFrame;
            page.referenceByte |= 0b10000000;
        } else {
            int pageToReplace = choosePageToReplace();
            replacePage(pageToReplace, pageNumber);
        }
    }
    public int choosePageToReplace() {
        int minByteValue = Integer.MAX_VALUE;
        int pageIndexToReplace = -1;
        for (int i = 0; i < pageTable.size(); i++) {
            Page page = pageTable.get(i);
            if (page.inMemory && page.referenceByte < minByteValue) {
                minByteValue = page.referenceByte;
                pageIndexToReplace = i;
            }
        }
        return pageIndexToReplace;
    }
    public void replacePage(int oldPageNumber, int newPageNumber) {
        Page oldPage = pageTable.get(oldPageNumber);
        int frame = oldPage.frame;
        oldPage.inMemory = false;
        oldPage.frame = -1;
        oldPage.referenceByte = 0;
    
        Page newPage = pageTable.get(newPageNumber);
        newPage.inMemory = true;
        newPage.frame = frame;
        newPage.referenceByte |= 0b10000000;
    
        frames[frame] = newPageNumber;
    }
    public void agePages() {
        for (Page page : pageTable) {
            if (page.inMemory) {
                page.referenceByte >>>= 1;  
            }
        }
    }
    public int simulate() {
        final int[] pageFaults = {0};  

        Thread updateThread = new Thread(() -> {
            for (int ref : references) {
                synchronized (lock) {
                    if (!accessPage(ref)) {
                        handlePageFault(ref);
                        pageFaults[0]++;
                    }
                }
                try {
                    Thread.sleep(2);  
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread agingThread = new Thread(() -> {
            for (int ref : references) {
                synchronized (lock) {
                    agePages();
                }
                try {
                    Thread.sleep(1);  
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        updateThread.start();
        agingThread.start();

        try {
            updateThread.join();  
            agingThread.join();   
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return pageFaults[0];
    }
}

