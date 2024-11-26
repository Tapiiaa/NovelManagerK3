package com.example.novelmanager.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

public class CompressionUtil {

    public static byte[] compress(String data) throws IOException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream(data.length());
        try (GZIPOutputStream gzip = new GZIPOutputStream(byteStream)) {
            gzip.write(data.getBytes("UTF-8"));
        }
        return byteStream.toByteArray();
    }
}
