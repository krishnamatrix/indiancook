package com.krish.indiancook.views;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.AssetFileDescriptor;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.text.Spannable;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.krish.indiancook.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Krishna on 1/10/2016.
 */
public class TextViewWithImages extends TextView {

    public TextViewWithImages(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    public TextViewWithImages(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public TextViewWithImages(Context context) {
        super(context);
    }
    @Override
    public void setText(CharSequence text, BufferType type) {
        Spannable s = getTextWithImages(getContext(), text);
        super.setText(s, BufferType.SPANNABLE);
    }

    private static final Spannable.Factory spannableFactory = Spannable.Factory.getInstance();

    private boolean addImages(final Context context, Spannable spannable) {
        //Pattern refImg = Pattern.compile("\\Q[ALARM\\(([0-9]+?)\\)\\Q/]\\E");
        Pattern refImg = Pattern.compile("\\[ALARM\\([0-9]+?\\)\\]");
        boolean hasChanges = false;
        String alarmTime = "";
        Matcher matcher = refImg.matcher(spannable);
        while (matcher.find()) {
            boolean set = true;
            for (ImageSpan span : spannable.getSpans(matcher.start(), matcher.end(), ImageSpan.class)) {
                if (spannable.getSpanStart(span) >= matcher.start()
                        && spannable.getSpanEnd(span) <= matcher.end()
                        ) {
                    spannable.removeSpan(span);
                } else {
                    set = false;
                    break;
                }
            }
            alarmTime = spannable.toString().substring(matcher.start(), matcher.end());
            alarmTime = alarmTime.replace(("[ALARM("),"");
            alarmTime = alarmTime.replace((")]"),"");
            final String finalAlarmTime = alarmTime;
            if (set) {
                hasChanges = true;
                spannable.setSpan(new ImageSpan(context, R.drawable.alarmclock),
                        matcher.start(),
                        matcher.end(),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                );

                ImageSpan[] image_spans = spannable.getSpans(0, spannable.length(), ImageSpan.class);
                for (ImageSpan span : image_spans) {
                    final String image_src = span.getSource();
                    final int start = spannable.getSpanStart(span);
                    final int end = spannable.getSpanEnd(span);
                    ClickableSpan click_span = new ClickableSpan() {
                        @Override
                        public void onClick(View widget) {
                            Log.d("IndianCook","Came to clickableSpan");
                            showCircularProgress(context, finalAlarmTime);
                        }
                    };
                    ClickableSpan[] click_spans = spannable.getSpans(start, end, ClickableSpan.class);
                    if(click_spans.length != 0) {
                        for(ClickableSpan c_span : click_spans) {
                            spannable.removeSpan(c_span);
                        }
                    }
                    setMovementMethod(LinkMovementMethod.getInstance());
                    spannable.setSpan(click_span, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
        }

        return hasChanges;
    }
    private Spannable getTextWithImages(Context context, CharSequence text) {
        Spannable spannable = spannableFactory.newSpannable(text);
        addImages(context, spannable);
        return spannable;
    }

    public void showCircularProgress(final Context context, String finalAlarmTime){
        // custom dialog
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.alarmdialog);
        final ProgressBar mProgressBar = (ProgressBar)dialog.findViewById(R.id.progressbar);
        mProgressBar.setMax(Integer.parseInt(finalAlarmTime) * 60);
        final TextView textViewShowTime = (TextView)dialog.findViewById(R.id.tvTimeCount);
        final MediaPlayer mp = new MediaPlayer();
        try{
            AssetFileDescriptor afd = context.getResources().openRawResourceFd(R.raw.alarmsound);
            if (afd == null) return;
            mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            mp.setLooping(true);
            /*int count = 0; // initialise outside listener to prevent looping

            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
                int maxCount = 3;

                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    if(count < maxCount) {
                        count++;
                        mediaPlayer.seekTo(0);
                        mediaPlayer.start();
                    }
                }});*/
            afd.close();
        }catch(Exception e){e.printStackTrace();}

        CountDownTimer countDownTimer = new CountDownTimer(Integer.parseInt(finalAlarmTime) * 60 * 1000, 1000) {
            // 500 means, onTick function will be called at every 500
            // milliseconds
            @Override
            public void onTick(long leftTimeInMilliseconds) {
                long seconds = leftTimeInMilliseconds / 1000;
                //Setting the Progress Bar to decrease wih the timer
                mProgressBar.setProgress((int) (leftTimeInMilliseconds / 1000));
                textViewShowTime.setText(String.format("%02d", seconds / 60)
                        + ":" + String.format("%02d", seconds % 60));
            }

            @Override
            public void onFinish() {
                textViewShowTime.setText(String.format("%02d", 0 / 60)
                        + ":" + String.format("%02d", 0 % 60));
                try{
                    mp.prepare();
                    mp.start();
                }catch(Exception e){e.printStackTrace();}
            }

        }.start();

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                //nothing;
                mp.stop();
            }
        });

        dialog.show();
    }
}