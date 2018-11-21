/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  org.apache.tools.ant.Project
 *  org.apache.tools.ant.taskdefs.Copy
 *  org.apache.tools.ant.taskdefs.Delete
 *  org.apache.tools.ant.taskdefs.Expand
 *  org.apache.tools.ant.taskdefs.Zip
 *  org.apache.tools.ant.types.FileSet
 */
package com.thoughtworks.twist.ssl.utils;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.UUID;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Copy;
import org.apache.tools.ant.taskdefs.Delete;
import org.apache.tools.ant.taskdefs.Expand;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.FileSet;

public class FileUtils {
    private static File tmpDir;

    public static String read(File file) {
        try {
            return FileUtils.read(new FileInputStream(file));
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static String read(InputStream in) {
        return FileUtils.read(new InputStreamReader(in, Charset.forName("UTF-8")));
    }

    public static String read(URL url) {
        try {
            return FileUtils.read(url.openStream());
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String read(Reader in) {
        StringBuffer buffer;
        buffer = new StringBuffer();
        try {
            while (in.ready()) {
                buffer.append((char)in.read());
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            FileUtils.close(in);
        }
        return buffer.toString();
    }

    public static /* varargs */ void writeLines(File file, String ... lines) {
        StringBuffer contents = new StringBuffer();
        for (String line : lines) {
            contents.append(line).append("\n");
        }
        FileUtils.write(file, contents.toString());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void write(File file, String text) {
        BufferedWriter w = null;
        try {
            try {
                w = new BufferedWriter(new FileWriter(file));
                w.append(text);
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        finally {
            FileUtils.close(w);
        }
    }

    public static void close(Closeable c) {
        if (c != null) {
            try {
                c.close();
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void closeQuietly(Closeable c) {
        if (c != null) {
            try {
                c.close();
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void rm_rf(File f) {
        Delete delete = new Delete();
        delete.setProject(new Project());
        delete.setFailOnError(true);
        delete.setQuiet(true);
        if (f.isDirectory()) {
            delete.setDir(f);
        } else {
            delete.setFile(f);
        }
        delete.execute();
    }

    public static void cp_rf(File srcDir, File destDir) {
        Copy copy = new Copy();
        copy.setProject(new Project());
        copy.setFailOnError(true);
        copy.setTodir(destDir);
        copy.setOverwrite(true);
        FileSet set = new FileSet();
        set.setDir(srcDir);
        set.appendIncludes(new String[]{"*", "**/*", "**/*.*"});
        copy.addFileset(set);
        copy.execute();
    }

    public static void zip(File destZipFile, File srcDir) {
        if (destZipFile.exists()) {
            throw new RuntimeException("Attempted to overwrite an existing zipfile: " + destZipFile);
        }
        Zip zip = new Zip();
        zip.setProject(new Project());
        zip.setBasedir(srcDir);
        zip.setDestFile(destZipFile);
        zip.execute();
    }

    public static void unzip(File srcZip, File outputDir) {
        FileUtils.mkdir_p(outputDir);
        Expand unzip = new Expand();
        unzip.setProject(new Project());
        unzip.setOverwrite(true);
        unzip.setSrc(srcZip);
        unzip.setDest(outputDir);
        unzip.execute();
    }

    public static File tmpDir(String subDir) {
        return new File(FileUtils.tmpDir(), subDir);
    }

    public static File tmpDir() {
        if (tmpDir == null) {
            tmpDir = new File(System.getProperty("java.io.tmpdir"), UUID.randomUUID().toString());
        }
        return tmpDir;
    }

    public static void mkdir_p(File dir) {
        if ((dir = dir.getAbsoluteFile()).exists() && dir.isDirectory()) {
            return;
        }
        if (!dir.mkdirs()) {
            throw new RuntimeException("Could not create directory " + dir);
        }
    }
}

