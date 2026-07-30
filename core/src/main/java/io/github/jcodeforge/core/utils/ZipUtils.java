package io.github.jcodeforge.core.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public abstract class ZipUtils {

    public static void unzip(File zipFile, String destDirPath) {
        File destDir = new File(destDirPath);
        if (!destDir.exists()) {
            destDir.mkdir();
        }

        try (FileInputStream fis = new FileInputStream(zipFile);
             ZipInputStream zis = new ZipInputStream(fis)) {
            ZipEntry entry = zis.getNextEntry();

            while (entry != null) {
                String entryPath = destDirPath + File.separator + entry.getName();
                if (!entry.isDirectory()) {
                    // if the entry is a file, extracts it
                    extractFile(zis, entryPath);
                } else {
                    // if the entry is a directory, make the directory
                    File dir = new File(entryPath);
                    dir.mkdir();
                }
                zis.closeEntry();
                entry = zis.getNextEntry();
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    private static void extractFile(ZipInputStream zipIn, String filePath) {
        try (FileOutputStream fos = new FileOutputStream(filePath);
             BufferedOutputStream bos = new BufferedOutputStream(fos)) {
            byte[] buffer = new byte[4096];
            int len;
            while ((len = zipIn.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    public static boolean zipFile(String sourcePath, String toLocation) {
        int bufferSize = 4096;

        File sourceFile = new File(sourcePath);
        try {
            BufferedInputStream origin = null;
            FileOutputStream dest = new FileOutputStream(toLocation);
            ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));
            if (sourceFile.isDirectory()) {
                zipSubDir(out, sourceFile, sourceFile.getParent().length());
            } else {
                byte data[] = new byte[bufferSize];
                FileInputStream fi = new FileInputStream(sourceFile);
                origin = new BufferedInputStream(fi, bufferSize);
                ZipEntry entry = new ZipEntry(sourceFile.getName());
                out.putNextEntry(entry);
                int count;
                while ((count = origin.read(data, 0, bufferSize)) != -1) {
                    out.write(data, 0, count);
                }
            }
            out.close();
        } catch (Exception e) {
            e.printStackTrace(System.err);
            return false;
        }
        return true;
    }

/*
 *
 * Zips a subfolder
 *
 */

    private static void zipSubDir(ZipOutputStream out, File subDir, int basePathLength)
            throws IOException {
        int bufferSize = 2048;

        File[] fileList = subDir.listFiles();

        BufferedInputStream origin;
        for (File file : fileList) {
            if (file.isDirectory()) {
                zipSubDir(out, file, basePathLength);
            } else {
                byte[] bytes = new byte[bufferSize];
                String unmodifiedFilePath = file.getPath();
                String relativePath = unmodifiedFilePath.substring(basePathLength);

                FileInputStream fis = new FileInputStream(unmodifiedFilePath);
                origin = new BufferedInputStream(fis, bufferSize);
                ZipEntry entry = new ZipEntry(relativePath);
                out.putNextEntry(entry);
                int count;
                while ((count = origin.read(bytes, 0, bufferSize)) != -1) {
                    out.write(bytes, 0, count);
                }
                origin.close();
            }
        }
    }
}