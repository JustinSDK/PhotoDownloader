package cc.openhome;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URL;

public class Img {
    private String url;
    public Img(String url) {
        this.url = url;
    }
    
    public String fileName() {
        return Regex.matchOne(url, Regex.jpgPattern) + ".jpg";
    }
    
    public byte[] toBytes() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
                ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            byte[] bytes = new byte[1024];
            int len = 0;
            while ((len = in.read(bytes)) != -1) {
                out.write(bytes, 0, len);
            }
            return out.toByteArray();
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }    
}
