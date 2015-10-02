package cc.openhome;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import static java.util.stream.Collectors.joining;

public class Page {
    private final String url;
    private String content;

    public Page(String url) {
        this.url = url;
    }

    public String hostUrl() {
        return "http://" + Regex.matchOne(url, Regex.hostPattern);
    }

    public String content() {
        if (content == null) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(url).openStream()))) {
                content = reader.lines().collect(joining("\n"));
            } catch (IOException ex) {
                throw new UncheckedIOException(ex);
            }
        }
        return content;
    }

    public String title() {
        return Regex.matchOne(content(), Regex.pageTitlePattern);
    }

    public List<String> imgSrcList() {
        List<String> lt = new ArrayList<>();
        Matcher matcher = Regex.srcPattern.matcher(content());
        while (matcher.find()) {
            lt.add(hostUrl() + matcher.group(1).replace(" ", "%20"));
        }
        return lt;
    }

    public void forEachImg(Consumer<Img> consumer) {
        imgSrcList().stream()
                .map(Img::new)
                .forEach(consumer);
    }
}
