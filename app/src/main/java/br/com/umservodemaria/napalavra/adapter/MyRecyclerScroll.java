package br.com.umservodemaria.napalavra.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Suleiman on 18-04-2015.
 */
public abstract class MyRecyclerScroll extends RecyclerView.OnScrollListener {
    private static final float HIDE_THRESHOLD = 100;
    private static final float SHOW_THRESHOLD = 50;


    private static int displayedposition = 0;
    int scrollDist = 0;
    private boolean isVisible = true;

    //    We dont use this method because its action is called per pixel value change
    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        LinearLayoutManager llm = (LinearLayoutManager) recyclerView.getLayoutManager();

        displayedposition = llm.findFirstVisibleItemPosition();
        //  Check scrolled distance against the minimum
        if (isVisible && scrollDist > HIDE_THRESHOLD) {
            //  Hide fab & reset scrollDist
            hide();
            scrollDist = 0;
            isVisible = false;
        }
        //  -MINIMUM because scrolling up gives - dy values
        else if (!isVisible && scrollDist < -SHOW_THRESHOLD) {
            //  Show fab & reset scrollDist
            show();

            scrollDist = 0;
            isVisible = true;
        }

        //  Whether we scroll up or down, calculate scroll distance
        if ((isVisible && dy > 0) || (!isVisible && dy < 0)) {
            scrollDist += dy;
        }

    }

    public static int getDisplayedposition() {
        return displayedposition;
    }

    public static void setDisplayedposition(int displayedposition) {
        MyRecyclerScroll.displayedposition = displayedposition;
    }

    public abstract void show();

    public abstract void hide();
}