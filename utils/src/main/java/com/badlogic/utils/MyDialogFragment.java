package com.badlogic.utils;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MyDialogFragment extends DialogFragment {

    private String from = "";
    public MyDialogFragment(String from_){
        from = from_;
        if (from == null) {
            from = "";
        }
    }

    public MyDialogFragment(){

    }

    public class MDialog extends Dialog{
        public MDialog(@NonNull Context context) {
            super(context);
        }

        public MDialog(@NonNull Context context, int themeResId) {
            super(context, themeResId);
        }

        @Override
        public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {
            ///-return super.onKeyDown(keyCode, event);
            return true;
        }
        @Override
        public boolean onKeyUp(int keyCode, @NonNull KeyEvent event) {
            if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                ///todo 监听返回键
                MyDialogFragment.this.dismiss();
            }
            ////-return super.onKeyUp(keyCode, event);
            return true;
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        return new MDialog(requireContext(), getTheme());

//        return super.onCreateDialog(savedInstanceState).set;
        /*AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.acitity_webdown, null);
        return builder.create();*/
    }

//    public void show(FragmentManager manager, String videoId,String tag) {
//        Bundle args = new Bundle();
//        args.putSerializable("videoId", videoId);
//        ListDownDialogFragment fragment = new ListDownDialogFragment();
//        fragment.setArguments(args);
//        fragment.show(manager, tag);
//    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ///todo 设置主题 0 无效
        setStyle(DialogFragment.STYLE_NO_FRAME,0);
    }

    @Override
    public void onStart() {
        super.onStart();
        Window win = getDialog().getWindow();
        win.setGravity(Gravity.BOTTOM);

        ///todo 设置进出动画
        ///win.setWindowAnimations(R.style.AnimaDialogAdd);

        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager manager = (WindowManager) getContext().getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        manager.getDefaultDisplay().getMetrics(metrics);

        ///todo 设置尺寸
        WindowManager.LayoutParams params = win.getAttributes();
        params.width = metrics.widthPixels;
        params.height = ActionBar.LayoutParams.WRAP_CONTENT;
        win.setAttributes(params);
        //-----

    }

    @Override
    public void onResume() {
        super.onResume();
        //动画显示
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_list, container, false);
        return null;
    }

    protected volatile boolean cancel = false;
    private String videoId = "";
    private SeekBar seekBar ;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            videoId = getArguments().getString("videoId","");
        }
        if (videoId == null || videoId.equals("")) {
            dismissAllowingStateLoss();
            Toast.makeText(this.getContext(),"000",Toast.LENGTH_SHORT).show();
            return;
        }

    }

    /*@Override
    public void show(FragmentManager manager, String tag) {
        try{
            super.show(manager,tag);
        }catch (IllegalStateException ignore){
        }
    }*/

    @Override
    public void show(FragmentManager manager, String tag) {
        try {
//            mDismissed = false;
//            mShownByMe = true;
            if (false) {
                super.show(manager,tag);
            }
            ///--tag = this + "";
            if (!this.isAdded() && null == manager.findFragmentByTag(tag)) {
                FragmentTransaction ft = manager.beginTransaction();
                ft.add(this, tag);
                ft.commitAllowingStateLoss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showNow(@NonNull FragmentManager manager, @Nullable String tag) {
        try {
            if (false) {
                super.showNow(manager,tag);
            }
            ///--tag = this + "";
            if (!this.isAdded() && null == manager.findFragmentByTag(tag)) {
                FragmentTransaction ft = manager.beginTransaction();
                ft.add(this, tag);
                ft.commitNowAllowingStateLoss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int show(@NonNull FragmentTransaction transaction, @Nullable String tag) {
        try {
            if (false) {
                super.show(transaction,tag);
            }
            //tag = this + "";
            if (!this.isAdded() /*&& null == manager.findFragmentByTag(tag)*/) {
                transaction.add(this, tag);
                return transaction.commitAllowingStateLoss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public static void testShow(FragmentActivity nActivity){
        MyDialogFragment fragment = new MyDialogFragment();
//        fragment.setArguments();
        fragment.show(nActivity.getSupportFragmentManager(), "49921");
    }


}
