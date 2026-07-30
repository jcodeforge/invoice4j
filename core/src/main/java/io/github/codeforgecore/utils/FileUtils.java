package io.github.codeforgecore.utils;

import jakarta.activation.MimetypesFileTypeMap;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public abstract class FileUtils {

    public static boolean writeInputStreamToFile(InputStream inputStream, File file) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);

            byte[] buffer = new byte[4096];
            int readingBytes;

            while ((readingBytes = inputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, readingBytes);
            }

            fileOutputStream.flush();
            inputStream.close();
            fileOutputStream.close();

            return true;

        } catch (IOException ignored) { }

        return false;
    }

    public static List<String> loadFilesByFilter(String path, String filter) {
        File dir = new File(path);

        String[] fileNames = dir.list(new FilenameFilter() {
            @Override
            public boolean accept(File f, String name) {
                return name.endsWith(filter);
            }
        });

        if (fileNames == null) {
            return new ArrayList<>();
        }

        List<String> files = new ArrayList<>();

        for (String fileName : fileNames){
            files.add(path + File.separator + fileName);
        }

        return files;
    }

    public static boolean move(String source, String target) {
        try {
            Files.move(Paths.get(source),Paths.get(target), StandardCopyOption.REPLACE_EXISTING);
            return true;

        } catch (IOException e) {
            return false;
        }
    }

    public static String readFromFile(File file) {
        StringBuilder resultStringBuilder = new StringBuilder();
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line = bufferedReader.readLine();
            while (line != null) {
                resultStringBuilder.append(line).append("\n");
                line = bufferedReader.readLine();
            }
            bufferedReader.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return resultStringBuilder.toString();
    }

    public static File writeToTempFile(String s, String fileName) {
        File localTempDir = new File(System.getProperty("java.io.tmpdir"));
        File localTempFile = new File(localTempDir.getAbsolutePath() +
                File.separator + fileName);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(localTempFile));
            writer.write(s);
            writer.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return localTempFile;
    }

    public static ImageIcon loadImageIconFromResources(ClassLoader loader, String icon) {
        try {
            InputStream is = loader.getResourceAsStream(icon);
            if (is != null) {
                return new ImageIcon(ImageIO.read(is));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public static String getFileExtension(File file) {
        if (file == null) {
            return null;
        }
        String fileName = file.getName();
        int lastIndex = fileName.lastIndexOf(".");
        if (lastIndex >= 0) {
            return fileName.substring(lastIndex);
        }
        return "";
    }

    public static String getMimeType(File file) {
        MimetypesFileTypeMap fileTypeMap = new MimetypesFileTypeMap();
        return fileTypeMap.getContentType(file.getName());
    }

    public static File writeToFile(String path, byte[] data) {
        File outputFile = new File(path);
        try {
            try (FileOutputStream outputStream = new FileOutputStream(outputFile)) {
                outputStream.write(data);
            }
            return outputFile.exists() && outputFile.length() > 0 ? outputFile : null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public static boolean copyFile(File src, File dest) {
        try {
            return copyFile(new FileInputStream(src), new FileOutputStream(dest));
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }

        return false;
    }

    public static boolean copyFile(InputStream src, File dest) {
        try {
            return copyFile(src, new FileOutputStream(dest));
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }

        return false;
    }

    public static boolean copyFile(InputStream src, OutputStream dest) {
        try {
            try {
                byte[] buffer = new byte[1024];

                int bytesRead;
                while ((bytesRead = src.read(buffer)) > 0) {
                    dest.write(buffer, 0, bytesRead);
                }
                return true;
            } finally {
                src.close();
                dest.close();
            }
        } catch (Exception e){
            e.printStackTrace(System.err);
        }

        return false;
    }

    public static boolean deleteDir(File dir) {
        if (dir.exists()) {
            File[] files = dir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        deleteDir(file);
                    } else {
                        file.delete();
                    }
                }
            }
        }
        return dir.delete();
    }

    public static String getProtectionDomain(Class<?> clazz) {
        try {
            URL url = clazz.getProtectionDomain().getCodeSource().getLocation();
            return Paths.get(url.toURI()).toString();
        } catch (URISyntaxException e) {
            return "";
        }
    }
}
