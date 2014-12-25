package com.relicum.libscommands;

import com.google.common.collect.ImmutableList;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.net.URL;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * User: Austin Date: 4/22/13 Time: 11:47 PM (c) lazertester
 */

// Code for this taken and slightly modified from
// https://github.com/ddopson/java-class-enumerator
public class ClassGetter {
    public static List<String> disabled = ImmutableList.of("Give", "Enchant", "Item", "SpawnMob");
    public static ArrayList<Class<?>> getClassesForPackage(JavaPlugin plugin, String pkgname) {
        ArrayList<Class<?>> classes = new ArrayList<>();
        // String relPath = pkgname.replace('.', '/');

        // Get a File object for the package
        CodeSource src = plugin.getClass().getProtectionDomain().getCodeSource();
        if (src != null) {
            URL resource = src.getLocation();
            resource.getPath();
            processJarfile(resource, pkgname, classes);
        }
        ArrayList<String> names = new ArrayList<>();
        AtomicReference<ArrayList<Class<?>>> classi = new AtomicReference<>(new ArrayList<>());
        classes.stream().filter(classy -> !disabled.contains(classy.getSimpleName())).forEach(classy -> {
            names.add(classy.getSimpleName());
            classi.get().add(classy);
        });
        classes.clear();
        Collections.sort(names, String.CASE_INSENSITIVE_ORDER);
        for (String s : names)
            for (Class<?> classy : classi.get()) {
                if (!disabled.contains(classy.getSimpleName())) {
                    if (classy.getSimpleName().equals(s)) {
                        classes.add(classy);
                        break;
                    }
                }
            }
        return classes;
    }

    private static Class<?> loadClass(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Unexpected ClassNotFoundException loading class '" + className + "'");
        }
    }

    private static void processJarfile(URL resource, String pkgname, ArrayList<Class<?>> classes) {
        String relPath = pkgname.replace('.', '/');
        String resPath = resource.getPath().replace("%20", " ");
        String jarPath = resPath.replaceFirst("[.]jar[!].*", ".jar").replaceFirst("file:", "");
        JarFile jarFile;
        try {
            jarFile = new JarFile(jarPath);
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException reading JAR File '" + jarPath + "'", e);
        }
        Enumeration<JarEntry> entries = jarFile.entries();
        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            String entryName = entry.getName();
            String className = null;
            if (entryName.endsWith(".class") && entryName.startsWith(relPath)
                    && entryName.length() > (relPath.length() + "/".length())) {
                className = entryName.replace('/', '.').replace('\\', '.').replace(".class", "");
            }
            if (className != null) {
                classes.add(loadClass(className));
            }
        }
    }
}
