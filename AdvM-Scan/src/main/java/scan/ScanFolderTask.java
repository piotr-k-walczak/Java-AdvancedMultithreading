package scan;

import java.io.File;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Stream;

public class ScanFolderTask extends RecursiveTask<Long> {

    private final File file;

    public ScanFolderTask(File path) {
        this.file = path;
    }

    @Override
    protected Long compute() {
        if (file.listFiles() != null) {
            return Stream.of(file.listFiles()).mapToLong(f -> {
                        if (f.isDirectory()) {
                            return ForkJoinTask.invokeAll(List.of(new ScanFolderTask(f)))
                                    .stream()
                                    .map(ForkJoinTask::join)
                                    .reduce(0L, Long::sum);
                        } else {
                            return f.length();
                        }
                    })
                    .reduce(0L, Long::sum);
        }
        return 0L;
    }
}
