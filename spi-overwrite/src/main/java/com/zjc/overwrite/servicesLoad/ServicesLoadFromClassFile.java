package com.zjc.overwrite.servicesLoad;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class ServicesLoadFromClassFile<S> extends ClassLoader {

    private String path = "E:\\classs";
    private final Class<S> service;

    private Map<String, Class<?>> classMap = null;


    public ServicesLoadFromClassFile(Class<S> svc) {
        service = Objects.requireNonNull(svc, "Service interface cannot be null");
    }

    public Class<?> getClass(String name) {
        if (classMap == null) {
            try {
                initClassMap();
            } catch (IOException e) {
                return null;
            }
        }
        return classMap.get(name);
    }

    public void initClassMap() throws IOException {
        classMap = new HashMap<>();

        File file = new File(path);
        if (file.exists() && file.isDirectory()) {

            File[] files = file.listFiles();
            for (File f : files) {
                if (!f.isDirectory()) {
                    String name = f.getName();
                    name = name.substring(0, name.indexOf("."));
                    String fullName = "com.zjc.overwrite.example.services." + name;
                    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                    try (DataInputStream bi = new DataInputStream(new FileInputStream(f))) {
                        byte[] buf = new byte[4096];
                        for (int bytesRead = bi.read(buf); bytesRead >= 0; bytesRead = bi.read(buf)) {
                            buffer.write(buf, 0, bytesRead);
                        }
                    }
                    byte[] classData = buffer.toByteArray();
                    Class<?> cl = defineClass(name, classData, 0, classData.length);
                    if (service.isAssignableFrom(cl)) {
                        classMap.put(fullName, cl);
                    }
                }

            }
        }

    }


}
