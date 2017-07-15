package com.example.discrollview;

import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by HP on 01-07-2017.
 */

public class FragmentNT9 extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_nt9, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        int imageHeight = (int) Math.round(Resources.getSystem().getDisplayMetrics().widthPixels / 2.69);
        int imageWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        ImageView mainCover = (ImageView) getActivity().findViewById(R.id.minimilitiaMainCover);
        mainCover.getLayoutParams().height = imageHeight;
        mainCover.getLayoutParams().width = imageWidth;

    }
}