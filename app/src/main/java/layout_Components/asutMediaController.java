package layout_Components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import com.example.shoaib.gomusic.R;

/**
 * Created by Shoaib on 2/27/2016.
 */
public class asutMediaController extends LinearLayout {

    private Controllers_asutMediaController mControllerInterface_Instance = null;

    private ImageButton playButton;
    private ImageButton previousButton;
    private ImageButton nextButton;
    private ImageButton shuffleButton;
    private ImageButton repeatButton;
    private SeekBar seekBar;

    public asutMediaController(Context context, AttributeSet attrs) {
        super(context,attrs);

        View layout = LayoutInflater.from(context).inflate(R.layout.asut_mediacontroller,this,true);
        //mControllerInterface_Instance = theInstance;

        playButton = (ImageButton) layout.findViewById(R.id.playButton);
        previousButton = (ImageButton) layout.findViewById(R.id.previosButton);
        nextButton = (ImageButton) layout.findViewById(R.id.nextButton);
        shuffleButton = (ImageButton) layout.findViewById(R.id.shuffleButton);
        repeatButton = (ImageButton) layout.findViewById(R.id.repeatButton);
        seekBar = (SeekBar) layout.findViewById(R.id.seekBar_asutMediaController);
        setListenersToButtons ();

    }

    private void setListenersToButtons ()
    {
        playButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mControllerInterface_Instance.play();
            }
        });

        nextButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mControllerInterface_Instance.playNext();
            }
        });

        previousButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mControllerInterface_Instance.playPrevious();
            }
        });

        shuffleButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mControllerInterface_Instance.shuffle();
            }
        });

        repeatButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mControllerInterface_Instance.repeat();
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mControllerInterface_Instance.seekBarInteracted(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }


    //Any class that creates object of asutMediaController class must implement this interface
    public interface Controllers_asutMediaController {
        void play ();
        void playNext ();
        void playPrevious ();
        void seekBarInteracted(int pos);
        boolean shuffle ();
        int repeat ();

    }


}
