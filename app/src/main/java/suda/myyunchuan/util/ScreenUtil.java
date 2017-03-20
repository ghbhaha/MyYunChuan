package suda.myyunchuan.util;

import android.content.Context;
import android.view.WindowManager;

/**
 * Created by guhaibo on 2017/3/20.
 */

public class ScreenUtil {

    public static int getScreenWidth(Context context){
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
    }

}
