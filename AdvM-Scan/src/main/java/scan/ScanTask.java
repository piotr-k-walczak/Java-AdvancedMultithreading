package scan;

import dnl.utils.text.table.TextTable;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.stream.Stream;

public class ScanTask extends RecursiveAction {

    private final Map<String, Long> scannedFiles;
    private final Map<String, Long> scannedFolders;
    private final String directoryPath;
    private final Runnable onEnd;
    private int fileCount = 0;
    private int folderCount = 0;

    public ScanTask(String directoryPath, Runnable onEnd) {
        this.directoryPath = directoryPath;
        this.scannedFiles = new ConcurrentHashMap<>();
        this.scannedFolders = new ConcurrentHashMap<>();
        this.onEnd = onEnd;
    }

    public static void scan(String directoryPath, Runnable onFinished) {
        ForkJoinTask.invokeAll(new ScanTask(directoryPath, onFinished));
    }

    @Override
    protected void compute() {
        System.out.println("Started scan of " + directoryPath);
        File f = new File(directoryPath);
        if (f.listFiles() != null) {
            Stream.of(f.listFiles())
                    .forEach(file -> {
                        if (file.isDirectory()) {
                            folderCount++;
                            scannedFolders.put(
                                    file.getName(),
                                    ForkJoinTask.invokeAll(
                                                    List.of(new ScanFolderTask(file)))
                                            .stream()
                                            .map(ForkJoinTask::join)
                                            .reduce(0L, Long::sum)
                            );
                        } else {
                            fileCount++;
                            scannedFiles.put(file.getName(), file.length());
                        }
                    });
            onEnd.run();
            print();
        }
    }

    private void print() {
        System.out.println("\n\n");
        printTable(scannedFiles, List.of("Files", "Size"));
        printTable(scannedFolders, List.of("Folders", "Size"));
    }

    private void printTable(Map<String, Long> datas, List<String> names) {
        String[][] data = datas.entrySet().stream()
                .map(e -> List.of(e.getKey(), (double) e.getValue() / 1024 + " KB").toArray(new String[]{}))
                .toList()
                .toArray(new String[][]{});
        TextTable textTable = new TextTable(names.toArray(new String[]{}), data);
        textTable.printTable();
    }
}
