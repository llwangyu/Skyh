/**
 * 
 */
package android.com.skyh.tool;

import android.content.Context;
import android.widget.Toast;


public class ToastUtil {

	public static void show(Context context, String info) {
		Toast.makeText(context, info, Toast.LENGTH_LONG).show();
	}

	public static void shortshow(Context context, String info) {
		Toast.makeText(context, info, Toast.LENGTH_SHORT).show();
	}

}
