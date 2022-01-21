package com.zjc.overwrite.servicesload;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.ServiceConfigurationError;

public final class ServicesLoadFromResources<S> extends ClassLoader {

    private String path = "services/";
    private final Class<S> service;

    private Map<String, Class<?>> classMap = null;


    public ServicesLoadFromResources(Class<S> svc) {
        service = Objects.requireNonNull(svc, "Service interface cannot be null");
    }

    @Override
    public Class<?> loadClass(String name) {
        if (classMap == null) {
            try {
                loadClass();
            } catch (IOException e) {
                return null;
            }
        }
        return classMap.get(name);
    }

    public void loadClass() throws IOException {
        String fullName = path + service.getName();
        ArrayList<String> names = new ArrayList<>();
        Enumeration<URL> configs = Thread.currentThread().getContextClassLoader().getSystemResources(fullName);
        while (configs.hasMoreElements()) {
            parse(configs.nextElement(), names);
        }
        classMap = new HashMap<>();
        names.forEach(c -> {
            try {
                Class cl = Class.forName(c);
                if (service.isAssignableFrom(cl)) {
                    classMap.put(c, cl);
                }

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

    }

    private ArrayList<String> parse(URL u, ArrayList<String> names)
            throws ServiceConfigurationError {

        try (InputStream in = u.openStream();
             BufferedReader r = new BufferedReader(new InputStreamReader(in, "utf-8"))
        ) {
            int lc = 1;
            while ((lc = parseLine(r, lc, names)) >= 0) ;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return names;
    }

    private int parseLine(BufferedReader r, int lc,
                          List<String> names)
            throws IOException, ServiceConfigurationError {
        String ln = r.readLine();
        if (ln == null) {
            return -1;
        }
        int ci = ln.indexOf('#');
        if (ci >= 0) ln = ln.substring(0, ci);
        ln = ln.trim();
        int n = ln.length();
        if (n != 0) {
            if ((ln.indexOf(' ') >= 0) || (ln.indexOf('\t') >= 0))
                return 0;
            int cp = ln.codePointAt(0);
            if (!Character.isJavaIdentifierStart(cp))
                return 0;
            for (int i = Character.charCount(cp); i < n; i += Character.charCount(cp)) {
                cp = ln.codePointAt(i);
                if (!Character.isJavaIdentifierPart(cp) && (cp != '.'))
                    return 0;
            }
            names.add(ln);
        }
        return lc + 1;
    }
}
