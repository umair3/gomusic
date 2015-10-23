package adapters_Miscellenous;

import android.content.Context;
import android.provider.MediaStore;
import android.widget.MediaController;

/**
 * Created by Shoaib on 9/29/2015.
 */
public class musicController extends MediaController {

    public musicController(Context context) {

        super(context);
    }

    @Override
    public void hide() {
       // super.hide();


    }

    public void hideit(){
        super.hide();
    }
}
