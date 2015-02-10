package cn.tonghu.fileencryption.utils;

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

import cn.tonghu.fileencryption.global.GlobalVariable;

/**
 * Created by tonghu on 2/5/15.
 */
public class EncryptUtils {
    public static final String EXTERNAL_ROOT_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();

    public static boolean encrypt(File file) {
        //先复制到加密目录
        //遍历修改所有子文件，修改文件名并加密
        if (file == null || !file.exists()) {
            return true;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(file.getParentFile().getAbsolutePath().replace(EXTERNAL_ROOT_PATH, GlobalVariable.getSecriteRootDictionary().getAbsolutePath())
                + File.separator
                + file.getName());
        File dstFile = new File(sb.toString());
        boolean isSuccess = file.renameTo(dstFile);
        if (isSuccess) {
            renameFileAndEncrypt(dstFile);
        }
        return true;
    }


    public static boolean unEncrypt(File file) {
        if (file == null || !file.exists()) {
            return true;
        }
//        StringBuilder sb = new StringBuilder();
//        sb.append(file.getParentFile().getAbsolutePath()
//                .replace(GlobalVariable.getSecriteRootDictionary().getAbsolutePath(), EXTERNAL_ROOT_PATH))
//            .append(File.separator)
//            .append(file.getName());
        File dstFile = new File(file.getParentFile().getAbsolutePath().replace(GlobalVariable.getSecriteRootDictionary().getAbsolutePath(), EXTERNAL_ROOT_PATH)
            , file.getName());
        boolean isSuccess = file.renameTo(dstFile);
        if (isSuccess) {
            renameFileAndUnEncrypt(dstFile);
        }



        //先复制到原目录
        //遍历所有子文件，还原用户名并解密
//        if (file.isDirectory()) {
//            for (File f : file.listFiles()) {
//                unEncrypt(f);
//            };
//        } else if (file.isFile()) {
////            String absolutePath = file.getAbsolutePath();
//            sb.append(file.getParentFile().getAbsolutePath().replace(GlobalVariable.getSecriteRootDictionary().getAbsolutePath(), EXTERNAL_ROOT_PATH));
//            sb.append(File.separator);
//            sb.append(file.getName().replace(".", ""));
//            File dstFile = new File(sb.toString());
//            boolean isSuccess = file.renameTo(dstFile);
//            if (isSuccess) {
////                encryptOrUnFile(dstFile);
//            }
//            LogUtils.i("path: " + file.getAbsolutePath()
//                    + ", unencryptionpath: " + sb.toString());
//            return true;
//        }
        return true;
    }


    private static void renameFileAndEncrypt(File file) {
        if (file.isFile()) {
            File dstFile = new File(file.getParentFile().getPath(), "." + file.getName());
            file.renameTo(dstFile);
            encryptOrUnFile(dstFile);
        } else if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                renameFileAndEncrypt(files[i]);
            }
        }
    }

    private static void renameFileAndUnEncrypt(File file) {
        if (file.isFile()) {
            File dstFile = new File(file.getParentFile().getPath(), file.getName().replaceFirst(".", ""));
            file.renameTo(dstFile);
            encryptOrUnFile(dstFile);
        } else if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                renameFileAndUnEncrypt(files[i]);
            }
        }
    }



    private static boolean encryptOrUnFile(File file) {
        if (file == null || !file.exists() || file.isDirectory()) {
            return true;
        }

        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
            int read = randomAccessFile.read();
            randomAccessFile.seek(0);
            randomAccessFile.write(255 - read);
            randomAccessFile.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

}
