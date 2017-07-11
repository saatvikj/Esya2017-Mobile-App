package com.example.discrollview;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.google.android.gms.maps.model.Marker;
import com.ramotion.foldingcell.FoldingCell;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import static android.R.id.message;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;


public class MainActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    private SliderLayout sliderLayout;
    private SliderLayout sponsorsSliderLayout;
    private Button events;
    private Button djNight;
    private Button comedyNight;
    private Button workshops;
    private Button initiatives;
    private Button accomodation;
    private ImageView unfoldedDJ;
    private ImageView foldedDJ;
    private ImageView unfoldedEvents;
    private ImageView foldedEvents;
    private ImageView unfoldedComedy;
    private ImageView foldedComedy;
    private ImageView unfoldedWorkshops;
    private ImageView foldedWorkshops;
    private ImageView unfoldedInitiatives;
    private ImageView foldedInitiatives;
    private ImageView unfoldedAccomodation;
    private ImageView foldedAccomodation;
    private RelativeLayout imageSlider;
    final int foldingCellArr[] = new int[6];

    private final LatLng LOCATION_IIITD = new LatLng(28.5459495, 77.2688703);
    private GoogleMap map;
    private Marker myMarker;

    private LinearLayout horizontalOuterLayout;
    private HorizontalScrollView horizontalScrollview;
    private TextView horizontalTextView;
    private int scrollMax;
    private int scrollPos = 0;
    private TimerTask clickSchedule;
    private TimerTask scrollerSchedule;
    private TimerTask faceAnimationSchedule;
    private Button clickedButton = null;
    private Timer scrollTimer = null;
    private Timer clickTimer = null;
    private Timer faceTimer = null;
    private Boolean isFaceDown = true;
    private String[] nameArray = {"AutoDesk", "RAJA Biscuits", "Bittoo Tikki Wala", "CodeChef", "Coding_Ninjas", "Engineers India LTD.", "GitLab", "Hacker Earth", "Happn", "Holiday IQ", "Luxor", "Qnswr", "Rau's IAS Study Circle", "Spykar", "UNIBIC"};
    private String[] imageNameArray = {"autode", "bisc", "btw", "codechef", "coding_ninjas", "eil", "gitlab", "hack", "happn", "holiq", "luxor", "qnswr", "rauias", "spykar", "unibic"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        imageSlider = (RelativeLayout) findViewById(R.id.slideFrame);
        imageSlider.getLayoutParams().width = Resources.getSystem().getDisplayMetrics().widthPixels;
        int height = (int) (Resources.getSystem().getDisplayMetrics().widthPixels * 1.5);
        imageSlider.getLayoutParams().height = height;
        imageSlider.requestLayout();

        horizontalScrollview = (HorizontalScrollView) findViewById(R.id.horiztonal_scrollview_id);
        horizontalOuterLayout = (LinearLayout) findViewById(R.id.horiztonal_outer_layout_id);
        horizontalTextView = (TextView) findViewById(R.id.horizontal_textview_id);

        horizontalScrollview.setHorizontalScrollBarEnabled(false);
        addImagesToView();

        ViewTreeObserver vto = horizontalOuterLayout.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                horizontalOuterLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                getScrollMaxAmount();
                startAutoScrolling();
            }
        });


        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

        myMarker = map.addMarker(new MarkerOptions().position(LOCATION_IIITD).title("Find us here! IIITD"));

        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(LOCATION_IIITD, 15);
        map.animateCamera(update);

        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

            @Override
            public boolean onMarkerClick(Marker arg0) {
                if (arg0.getTitle().equals("Find us here! IIITD")) {
                    // if marker source is clicked
                    Toast.makeText(MainActivity.this, arg0.getTitle(), Toast.LENGTH_SHORT).show();
                    Uri gmmIntentUri = Uri.parse("google.navigation:q=" + "IIIT Delhi");
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(mapIntent);
                }
                return true;
            }

        });

        unfoldedDJ = (ImageView) findViewById(R.id.unfoldedDJ);
        foldedDJ = (ImageView) findViewById(R.id.iconDJ);
        unfoldedDJ.getLayoutParams().height = Resources.getSystem().getDisplayMetrics().widthPixels;
        unfoldedDJ.getLayoutParams().width = Resources.getSystem().getDisplayMetrics().widthPixels;
        unfoldedDJ.requestLayout();

        foldedDJ.getLayoutParams().width = Resources.getSystem().getDisplayMetrics().widthPixels;
        foldedDJ.getLayoutParams().height = (int) Resources.getSystem().getDisplayMetrics().widthPixels / 3;
        foldedDJ.requestLayout();

        unfoldedEvents = (ImageView) findViewById(R.id.unfoldedEvents);
        foldedEvents = (ImageView) findViewById(R.id.iconEvents);
        unfoldedEvents.getLayoutParams().height = Resources.getSystem().getDisplayMetrics().widthPixels;
        unfoldedEvents.getLayoutParams().width = Resources.getSystem().getDisplayMetrics().widthPixels;
        unfoldedEvents.requestLayout();

        foldedEvents.getLayoutParams().width = Resources.getSystem().getDisplayMetrics().widthPixels;
        foldedEvents.getLayoutParams().height = (int) Resources.getSystem().getDisplayMetrics().widthPixels / 3;
        foldedEvents.requestLayout();

        unfoldedInitiatives = (ImageView) findViewById(R.id.unfoldedInitiatives);
        foldedInitiatives = (ImageView) findViewById(R.id.iconInitiatives);
        unfoldedInitiatives.getLayoutParams().height = Resources.getSystem().getDisplayMetrics().widthPixels;
        unfoldedInitiatives.getLayoutParams().width = Resources.getSystem().getDisplayMetrics().widthPixels;
        unfoldedInitiatives.requestLayout();

        foldedInitiatives.getLayoutParams().width = Resources.getSystem().getDisplayMetrics().widthPixels;
        foldedInitiatives.getLayoutParams().height = (int) Resources.getSystem().getDisplayMetrics().widthPixels / 3;
        foldedInitiatives.requestLayout();

        unfoldedWorkshops = (ImageView) findViewById(R.id.unfoldedWorkshops);
        foldedWorkshops = (ImageView) findViewById(R.id.iconWorkshops);
        unfoldedWorkshops.getLayoutParams().height = Resources.getSystem().getDisplayMetrics().widthPixels;
        unfoldedWorkshops.getLayoutParams().width = Resources.getSystem().getDisplayMetrics().widthPixels;
        unfoldedWorkshops.requestLayout();

        foldedWorkshops.getLayoutParams().width = Resources.getSystem().getDisplayMetrics().widthPixels;
        foldedWorkshops.getLayoutParams().height = (int) Resources.getSystem().getDisplayMetrics().widthPixels / 3;
        foldedWorkshops.requestLayout();

        unfoldedComedy = (ImageView) findViewById(R.id.unfoldedComedy);
        foldedComedy = (ImageView) findViewById(R.id.iconComedy);
        unfoldedComedy.getLayoutParams().height = Resources.getSystem().getDisplayMetrics().widthPixels;
        unfoldedComedy.getLayoutParams().width = Resources.getSystem().getDisplayMetrics().widthPixels;
        unfoldedComedy.requestLayout();

        foldedComedy.getLayoutParams().width = Resources.getSystem().getDisplayMetrics().widthPixels;
        foldedComedy.getLayoutParams().height = (int) Resources.getSystem().getDisplayMetrics().widthPixels / 3;
        foldedComedy.requestLayout();

        unfoldedAccomodation = (ImageView) findViewById(R.id.unfoldedAccomodation);
        foldedAccomodation = (ImageView) findViewById(R.id.iconAccomodation);
        unfoldedAccomodation.getLayoutParams().height = Resources.getSystem().getDisplayMetrics().widthPixels;
        unfoldedAccomodation.getLayoutParams().width = Resources.getSystem().getDisplayMetrics().widthPixels;
        unfoldedAccomodation.requestLayout();

        foldedAccomodation.getLayoutParams().width = Resources.getSystem().getDisplayMetrics().widthPixels;
        foldedAccomodation.getLayoutParams().height = (int) Resources.getSystem().getDisplayMetrics().widthPixels / 3;
        foldedAccomodation.requestLayout();

        foldingCellArr[0] = R.id.folding_cell;
        foldingCellArr[1] = R.id.folding_cell2;
        foldingCellArr[2] = R.id.folding_cell3;
        foldingCellArr[3] = R.id.folding_cell4;
        foldingCellArr[4] = R.id.folding_cell5;
        foldingCellArr[5] = R.id.folding_cell6;

        ImageButton facebook = (ImageButton) findViewById(R.id.facebookEsya);
        ImageButton instagram = (ImageButton) findViewById(R.id.instagramEsya);
        ImageButton twitter = (ImageButton) findViewById(R.id.twitterEsya);
        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile/383935642008094"));
                    startActivity(intent);
                } catch (Exception e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com/EsyaIIITD")));
                }
            }
        });

        instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://instagram.com/_u/esya_iiitd");
                Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

                likeIng.setPackage("com.instagram.android");

                try {
                    startActivity(likeIng);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://instagram.com/esya_iiitd")));
                }
            }
        });

        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=" + "esyaiiitd")));
                } catch (Exception e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/#!/" + "esyaiiitd")));
                }
            }
        });

        ImageButton email = (ImageButton) findViewById(R.id.emailEsya);
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "esya@iiitd.ac.in", null));
                intent.putExtra(Intent.EXTRA_SUBJECT, "Query");
                intent.putExtra(Intent.EXTRA_TEXT, message);
                startActivity(Intent.createChooser(intent, "Choose an Email client :"));
            }
        });

        Button viewallspons = (Button) findViewById(R.id.viewallspons);
        viewallspons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.activity_sponspopup, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                builder.setView(inflater.inflate(R.layout.activity_sponspopup, null));
                AlertDialog ad = builder.create();
                ad.setTitle("All Past Sponsors");
                ad.show();
            }
        });


        TextView web = (TextView) findViewById(R.id.websiteEsya);
        web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.esya.iiitd.edu.in")));
            }
        });

        final FoldingCell fc = (FoldingCell) findViewById(R.id.folding_cell);
        fc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkUnfolder(R.id.folding_cell);
                fc.toggle(false);
            }
        });

        final FoldingCell fc2 = (FoldingCell) findViewById(R.id.folding_cell2);
        fc2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkUnfolder(R.id.folding_cell2);
                fc2.toggle(false);
            }
        });

        final FoldingCell fc3 = (FoldingCell) findViewById(R.id.folding_cell3);
        fc3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkUnfolder(R.id.folding_cell3);
                fc3.toggle(false);
            }
        });

        final FoldingCell fc4 = (FoldingCell) findViewById(R.id.folding_cell4);
        fc4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkUnfolder(R.id.folding_cell4);
                fc4.toggle(false);
            }
        });

        final FoldingCell fc5 = (FoldingCell) findViewById(R.id.folding_cell5);
        fc5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkUnfolder(R.id.folding_cell5);
                fc5.toggle(false);
            }
        });

        final FoldingCell fc6 = (FoldingCell) findViewById(R.id.folding_cell6);
        fc6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkUnfolder(R.id.folding_cell6);
                fc6.toggle(false);
            }
        });

        sliderLayout = (SliderLayout) findViewById(R.id.slider);
        HashMap<String, Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("Image 1", R.drawable.esya01);
        file_maps.put("Image 2", R.drawable.esya02);
        file_maps.put("Image 3", R.drawable.esya03);
        file_maps.put("Image 4", R.drawable.esya04);
        file_maps.put("Image 5", R.drawable.esya05);

        for (String name : file_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(MainActivity.this);
            textSliderView

                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit);

            sliderLayout.addSlider(textSliderView);

        }


        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.ZoomOutSlide);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setDuration(3750);
        sliderLayout.addOnPageChangeListener(this);



        events = (Button) findViewById(R.id.events);
        events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent eventsIntent = new Intent(MainActivity.this, EventsMainActivity.class);
                startActivity(eventsIntent);
            }
        });

        djNight = (Button) findViewById(R.id.djNight);
        djNight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent djIntent = new Intent(MainActivity.this, DjNight.class);
                startActivity(djIntent);
            }
        });

        comedyNight = (Button) findViewById(R.id.comedyNight);
        comedyNight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent comedyIntent = new Intent(MainActivity.this, ComedyNight.class);
                startActivity(comedyIntent);

            }
        });

        workshops = (Button) findViewById(R.id.workshops);
        workshops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent workshopIntent = new Intent(MainActivity.this, Workshops.class);
                startActivity(workshopIntent);
            }
        });

        initiatives = (Button) findViewById(R.id.initiatives);
        initiatives.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent initiativeIntent = new Intent(MainActivity.this, Initiatives.class);
                startActivity(initiativeIntent);
            }
        });

        accomodation = (Button) findViewById(R.id.accomodation);
        accomodation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent initiativeIntent = new Intent(MainActivity.this, Accomodation.class);
                startActivity(initiativeIntent);
            }
        });
    }

    public void checkUnfolder(int unfolderID) {
        for (int i = 0; i < 6; i++) {
            FoldingCell fc = (FoldingCell) findViewById(foldingCellArr[i]);
            if (fc.isUnfolded()) {
                fc.toggle(false);
            }
        }
    }


    @Override
    protected void onStop() {
        sliderLayout.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

        Toast.makeText(this, slider.getBundle().get("extra") + "", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {

        Log.d("Slider Demo", "Page Changed: " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    public void getScrollMaxAmount() {
        int actualWidth = (horizontalOuterLayout.getMeasuredWidth() - getWindowManager().getDefaultDisplay().getWidth());

        scrollMax = actualWidth;
    }

    public void startAutoScrolling() {
        if (scrollTimer == null) {
            scrollTimer = new Timer();
            final Runnable Timer_Tick = new Runnable() {
                public void run() {
                    moveScrollView();
                }
            };

            if (scrollerSchedule != null) {
                scrollerSchedule.cancel();
                scrollerSchedule = null;
            }
            scrollerSchedule = new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(Timer_Tick);
                }
            };

            scrollTimer.schedule(scrollerSchedule, 30, 30);
        }
    }

    public void moveScrollView() {
        scrollPos = (int) (horizontalScrollview.getScrollX() + 4.0);

        if (scrollPos >= scrollMax) {
            Log.v("childCount", "" + scrollMax);
            addImagesToView();
            getScrollMaxAmount();
        }
        horizontalScrollview.scrollTo(scrollPos, 0);
    }

    /**
     * Adds the images to view.
     */
    public void addImagesToView() {
        for (int i = 0; i < imageNameArray.length; i++) {
            final Button imageButton = new Button(this);
            int imageResourceId = getResources().getIdentifier(imageNameArray[i], "drawable", getPackageName());
            Drawable image = this.getResources().getDrawable(imageResourceId);
            imageButton.setBackgroundDrawable(image);
            float scale = getResources().getDisplayMetrics().density;
            int dpAsPixels = (int) (10 * scale + 0.5f);
            imageButton.setPadding(dpAsPixels, 0, dpAsPixels, 0);
            imageButton.setTag(i);

            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    if (isFaceDown) {
                        if (clickTimer != null) {
                            clickTimer.cancel();
                            clickTimer = null;
                        }
                        clickedButton = (Button) arg0;
                        stopAutoScrolling();
                        clickedButton.startAnimation(scaleFaceUpAnimation());
                        clickedButton.setSelected(true);
                        clickTimer = new Timer();

                        if (clickSchedule != null) {
                            clickSchedule.cancel();
                            clickSchedule = null;
                        }

                        clickSchedule = new TimerTask() {
                            public void run() {
                                startAutoScrolling();
                            }
                        };

                        clickTimer.schedule(clickSchedule, 1500);
                    }
                }
            });
            int h = image.getIntrinsicHeight();
            int w = image.getIntrinsicWidth();
            int r = h / w;
            if (r == 0) {
                r = w / h;
                r = 256 * r;
            } else {
                r = 256 / r;
            }
            if (r > 400)
                r = 400;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(r, 256);
            params.setMargins(0, dpAsPixels, 0, dpAsPixels);
            params.leftMargin = 10;
            params.rightMargin = 10;
            imageButton.setLayoutParams(params);
            horizontalOuterLayout.addView(imageButton);
        }
    }

    public Animation scaleFaceUpAnimation() {
        Animation scaleFace = new ScaleAnimation(1.0f, 1.2f, 1.0f, 1.2f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleFace.setDuration(500);
        scaleFace.setFillAfter(true);
        scaleFace.setInterpolator(new AccelerateInterpolator());
        Animation.AnimationListener scaleFaceAnimationListener = new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation arg0) {
                horizontalTextView.setText(nameArray[(Integer) clickedButton.getTag()]);
                isFaceDown = false;
            }

            @Override
            public void onAnimationRepeat(Animation arg0) {
            }

            @Override
            public void onAnimationEnd(Animation arg0) {
                if (faceTimer != null) {
                    faceTimer.cancel();
                    faceTimer = null;
                }

                faceTimer = new Timer();
                if (faceAnimationSchedule != null) {
                    faceAnimationSchedule.cancel();
                    faceAnimationSchedule = null;
                }
                faceAnimationSchedule = new TimerTask() {
                    @Override
                    public void run() {
                        faceScaleHandler.sendEmptyMessage(0);
                    }
                };

                faceTimer.schedule(faceAnimationSchedule, 750);
            }
        };
        scaleFace.setAnimationListener(scaleFaceAnimationListener);
        return scaleFace;
    }

    private Handler faceScaleHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (clickedButton.isSelected() == true)
                clickedButton.startAnimation(scaleFaceDownAnimation(500));
        }
    };

    public Animation scaleFaceDownAnimation(int duration) {
        Animation scaleFace = new ScaleAnimation(1.2f, 1.0f, 1.2f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleFace.setDuration(duration);
        scaleFace.setFillAfter(true);
        scaleFace.setInterpolator(new AccelerateInterpolator());
        Animation.AnimationListener scaleFaceAnimationListener = new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation arg0) {
            }

            @Override
            public void onAnimationRepeat(Animation arg0) {
            }

            @Override
            public void onAnimationEnd(Animation arg0) {
                horizontalTextView.setText("");
                isFaceDown = true;
            }
        };
        scaleFace.setAnimationListener(scaleFaceAnimationListener);
        return scaleFace;
    }

    public void stopAutoScrolling() {
        if (scrollTimer != null) {
            scrollTimer.cancel();
            scrollTimer = null;
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void onPause() {
        super.onPause();
        finish();
    }

    public void onDestroy() {
        clearTimerTaks(clickSchedule);
        clearTimerTaks(scrollerSchedule);
        clearTimerTaks(faceAnimationSchedule);
        clearTimers(scrollTimer);
        clearTimers(clickTimer);
        clearTimers(faceTimer);

        clickSchedule = null;
        scrollerSchedule = null;
        faceAnimationSchedule = null;
        scrollTimer = null;
        clickTimer = null;
        faceTimer = null;
        super.onDestroy();
    }

    private void clearTimers(Timer timer) {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    private void clearTimerTaks(TimerTask timerTask) {
        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }
    }
}