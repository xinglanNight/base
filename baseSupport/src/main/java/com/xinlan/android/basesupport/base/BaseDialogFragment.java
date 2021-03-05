package com.xinlan.android.basesupport.base;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class BaseDialogFragment extends DialogFragment {
    protected Context mContext;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void show(FragmentManager fm, String tag) {
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(this, tag);
        // 这里把原来的commit()方法换成了commitAllowingStateLoss()
        // 解决Can not perform this action after onSaveInstanceState with DialogFragment
        ft.commitAllowingStateLoss();
        //解决java.lang.IllegalStateException: Fragment already added
        fm.executePendingTransactions();
    }

}
