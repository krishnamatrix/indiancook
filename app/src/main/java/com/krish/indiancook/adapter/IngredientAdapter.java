package com.krish.indiancook.adapter;

/**
 * Created by u462716 on 12/28/2015.
 */

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.krish.indiancook.R;
import com.krish.indiancook.views.RoundedImageView;

import java.util.List;

public class IngredientAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final List<String> values;
    private CountDownTimer countDownTimer; // built in android class

    public IngredientAdapter(Context context, List<String> values) {
        super(context, -1, values);
        this.context = context;
        this.values = values;
    }
    @Override
    public int getCount() {
        return values.size();
    }

    @Override
    public String getItem(int arg0) {
        return values.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout rowView;
        if(convertView == null) {
            rowView = new LinearLayout(getContext());
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            inflater.inflate(R.layout.ingredient, rowView, true);
        } else {
            rowView = (LinearLayout)convertView;
        }
        String ingredItem = values.get(position);
        //Log.d("IndianCook", "IngredItem:" + ingredItem +",position:" + position);
        String[] ingredItemList = ingredItem.split("\\|");
        TextView textView = (TextView) rowView.findViewById(R.id.ingredientDetail);
        RoundedImageView imageView = (RoundedImageView) rowView.findViewById(R.id.ingredientImage);
        textView.setText(ingredItemList[0]);
        textView.setTextColor(Color.parseColor("#000000"));
        try{
            final int res = context.getResources().getIdentifier(ingredItemList[1] , "drawable", context.getPackageName());
            //Log.d("IndianCook","The image is:" + res + "," + ingredItemList[1]);
            if(res != 0) {
                imageView.setImageResource(res);
                final Animation anim = AnimationUtils.loadAnimation(context, R.anim.scale);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //showCircularProgress();
                        showImage(res);
                        //view.startAnimation(anim);
                    }
                });

            }
        } catch(Exception e){
        }

        if(position % 2 == 0) {
            rowView.setBackgroundColor(Color.parseColor("#EEEEEE"));
        } else {
            rowView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
        return rowView;
    }

    public void showImage(int resId) {
        Dialog builder = new Dialog(context);
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                //nothing;
            }
        });

        RoundedImageView imageView = new RoundedImageView(context);
        Bitmap bm = BitmapFactory.decodeResource(context.getResources(), resId);
        bm = scaleBitmap(bm, 240, 240);
        /*imageView.setLayoutParams(new LinearLayout.LayoutParams(
                120,
                120));
        imageView.setScaleType(ImageView.ScaleType.MATRIX);*/
        //imageView.setBackgroundDrawable(new BitmapDrawable(bm));
        imageView.setImageBitmap(bm);
        builder.addContentView(imageView, new RelativeLayout.LayoutParams(
                240,
                240));
        builder.show();
    }
    private void startTimer() {


    }
    public static Bitmap scaleBitmap(Bitmap bitmapToScale, float newWidth, float newHeight) {
        if(bitmapToScale == null)
            return null;
//get the original width and height
        int width = bitmapToScale.getWidth();
        int height = bitmapToScale.getHeight();
// create a matrix for the manipulation
        Matrix matrix = new Matrix();

// resize the bit map
        matrix.postScale(newWidth / width, newHeight / height);

// recreate the new Bitmap and set it back
        return Bitmap.createBitmap(bitmapToScale, 0, 0, bitmapToScale.getWidth(), bitmapToScale.getHeight(), matrix, true);  }
}

