package cc.openhome;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws IOException {
        Path urlFilePath = Paths.get("D:\\workspace\\macis.txt");

        Files.lines(urlFilePath)
             .map(Page::new)
             .forEach(page -> {
                 downloadImgs(page, urlFilePath.getParent().resolve(page.title()));
             });
    }
    
    public static void downloadImgs(Page page, Path toPath) {
        page.forEachImg(img -> {
            try {
                if (!Files.exists(toPath)) {
                    Files.createDirectory(toPath);
                }
                Files.write(toPath.resolve(img.fileName()), img.toBytes());
            } catch (IOException ex) {
                throw new UncheckedIOException(ex);
            }
        });
    }
}
