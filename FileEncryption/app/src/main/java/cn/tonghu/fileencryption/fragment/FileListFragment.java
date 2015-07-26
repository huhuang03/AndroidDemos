package cn.tonghu.fileencryption.fragment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AbsListView;
import android.widget.ListView;

import java.io.File;

import cn.tonghu.fileencryption.R;
import cn.tonghu.fileencryption.adapter.FileListAdapter;
import cn.tonghu.fileencryption.base.BaseListAdapter;
import cn.tonghu.fileencryption.data.FileData;
import cn.tonghu.fileencryption.global.GlobalVariable;
import cn.tonghu.fileencryption.utils.LogUtils;

/**
 * Created by tonghu on 2/5/15.
 */
public class FileListFragment extends ListFragment implements AbsListView.MultiChoiceModeListener {
    public static final String EXTRA_ROOT_FILE = "root_file";
    protected File rootFile;
    protected BaseListAdapter<File> adapter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        getListView().setMultiChoiceModeListener(this);
        if (getArguments() != null) {
            rootFile = (File) getArguments().getSerializable(EXTRA_ROOT_FILE);
        } else {
            if (getType() == TYPE_ENCRYPTION) {
                rootFile = Environment.getExternalStorageDirectory();
            } else if (getType() == TYPE_UNENCRYPTION) {
                rootFile = GlobalVariable.getSecriteRootDictionary();
//                setListAdapter(new FileListAdapter(
//                        new FileData(GlobalVariable.getSecriteRootDictionary()).listFiles()));
            }
        }
        adapter = new FileListAdapter(new FileData(rootFile).listFiles());
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        File file = (File) getListView().getItemAtPosition(position);
        if (file.isDirectory()) {
            getActivity().getSupportFragmentManager().beginTransaction().
                    addToBackStack(getTag()).
                    replace(R.id.content,
                            createByRootFile(file, getType())).commit();
        } else {
            MimeTypeMap mime = MimeTypeMap.getSingleton();
            int index = file.getName().lastIndexOf('.') + 1;
            String ext = file.getName().substring(index).toLowerCase();
            String type = mime.getMimeTypeFromExtension(ext);

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(file), type);
            try
            {
                getActivity().startActivity(intent);
            }
            catch(ActivityNotFoundException ex)
            {
                ex.printStackTrace();

            }
        }
    }

    public static final int TYPE_ENCRYPTION = 1;
    public static final int TYPE_UNENCRYPTION = 2;

    public static FileListFragment createByRootFile(File file, int type) {
        FileListFragment rst = null;
        if (type == TYPE_ENCRYPTION) {
            rst = new EncryptionFileListFragment();
        } else if (type == TYPE_UNENCRYPTION) {
            rst = new UnEncryptionFileListFragment();
        }
        Bundle argument = new Bundle();
        argument.putSerializable(EXTRA_ROOT_FILE, file);
        rst.setArguments(argument);
        return rst;
    }

    protected int getType() {
        return TYPE_ENCRYPTION;
    }

    @Override
    public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
        setSubtitle(mode);
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return true;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {

    }

    @Override
    public void onResume() {
        super.onResume();
        notifyData();
    }

    protected void setSubtitle(ActionMode mode) {
        final int checkedCount = getListView().getCheckedItemCount();
        switch (checkedCount) {
            case 0:
                mode.setSubtitle(null);
                break;
            case 1:
                mode.setSubtitle("One item selected");
                break;
            default:
                mode.setSubtitle("" + checkedCount + " items selected");
                break;
        }
    }

    protected void notifyData() {
        adapter.notifyWithData(new FileData(rootFile).listFiles());
    }

}
