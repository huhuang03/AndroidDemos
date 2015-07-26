package cn.tonghu.fileencryption.data;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.tonghu.fileencryption.utils.LogUtils;

/**
 * Created by tonghu on 2/5/15.
 */
public class FileData {
    private File rootFile;
    private boolean showHideFile = false;

    public FileData(File rootFile) {
        this.rootFile = rootFile;
    }

    public FileData(File rootFile, boolean showHideFile) {
        this.rootFile = rootFile;
        this.showHideFile = showHideFile;
    }

    public List<File> listFiles() {
        List<File> rst = new ArrayList<>();
        if (rootFile != null) {
            if (rootFile.isFile()) {
                rst.add(rootFile);
            } else if (rootFile.isDirectory()){
                rst.addAll(Arrays.asList(rootFile.listFiles(
//                    new FilenameFilter() {
//                        @Override
//                        public boolean accept(File dir, String filename) {
//                            LogUtils.i(filename);
//                            return !filename.startsWith(".");
//                        }
//                    }
                )));
            }
        }
        return rst;
    }

}
