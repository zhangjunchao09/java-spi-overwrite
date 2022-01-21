package com.zjc.overwrite.servicesload;

import com.zjc.overwrite.parseclass.Handle.ClassInfoHandle;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
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


    public ServicesLoadFromClassFile(Class<S> svc, String path) {
        service = Objects.requireNonNull(svc, "Service interface cannot be null");
        this.path = path == null ? this.path : path;
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
                    String fullName;
                    try (FileInputStream fis = new FileInputStream(f)) {
                        ClassInfoHandle classInfoHandle = new ClassInfoHandle();
                        classInfoHandle.read(fis);
                        String classPath = classInfoHandle.getClassInfo().getThisClass();
                        fullName = classPath.replaceAll("/", ".");
                    }
                    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                    try (BufferedInputStream bi = new BufferedInputStream(new FileInputStream(f))) {
                        byte[] buf = new byte[4096];
                        for (int bytesRead = bi.read(buf); bytesRead >= 0; bytesRead = bi.read(buf)) {
                            buffer.write(buf, 0, bytesRead);
                        }
                    }
                    byte[] classData = buffer.toByteArray();
                    Class<?> cl = defineClass(fullName, classData, 0, classData.length);
                    if (service.isAssignableFrom(cl)) {
                        classMap.put(fullName, cl);
                    }
                }

            }
        }

    }


}
