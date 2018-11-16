package com.android.mangaliso.news_app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class NewsAdapter extends ArrayAdapter<News> {
    @SuppressLint("SimpleDateFormat")
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    private static final String TAG = NewsAdapter.class.getName();

    public NewsAdapter(Context c, ArrayList<News> news) {
        super(c, 0, news);
    }

    @SuppressLint("StringFormatInvalid")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        News currentNews = getItem(position);

        TextView sectionNameView = listItemView.findViewById(R.id.section_name);
        assert currentNews != null;
        sectionNameView.setText(currentNews.getSectionName());

        TextView webUrlView = listItemView.findViewById(R.id.web_url);
        webUrlView.setText(currentNews.getUrl());

        TextView webTitleView = listItemView.findViewById(R.id.web_title);
        webTitleView.setText(currentNews.getTitle());

        TextView publicationDateView = listItemView.findViewById(R.id.publication_date);

        Date dateToday = new Date();
        Date datePublished = null;
        @SuppressLint("SimpleDateFormat") DateFormat dateFormatToday = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        String todayFormattedDate = dateFormatToday.format(dateToday);

        try {

            datePublished = simpleDateFormat.parse(currentNews.getPublicationDate());
        } catch (ParseException e) {
            Log.e(TAG, "Parsing failed " + e);
        }

        long difference = dateToday.getTime() - datePublished.getTime();

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;
        long monthsInMilli = daysInMilli * 30;

        long elapsedMonths = difference / monthsInMilli;
        difference = difference % monthsInMilli;

        long elapsedDays = difference / daysInMilli;
        difference = difference % daysInMilli;

        long elapsedHours = difference / hoursInMilli;
        difference = difference % hoursInMilli;

        long elapsedMinutes = difference / minutesInMilli;
        difference = difference % minutesInMilli;

        long elapsedSeconds = difference / secondsInMilli;


        if (elapsedHours>=0 && elapsedHours <= 12) {

            if(elapsedHours==1)
            {
                publicationDateView.setText(getContext().getResources().getString(R.string.posted_an_hour,elapsedHours));
            }
            else
            {
                publicationDateView.setText(getContext().getResources().getString(R.string.posted_hours_ago,elapsedHours));
            }
        }
        else
            {
            if (elapsedDays <= 30) {
                if (elapsedDays == 1) {
                    publicationDateView.setText(getContext().getResources().getString(R.string.posted_a_day,elapsedDays));
                } else {
                    publicationDateView.setText(getContext().getResources().getString(R.string.posted_days,elapsedDays));
                }
            } else {
                if (elapsedMonths == 1) {
                    publicationDateView.setText(getContext().getResources().getString(R.string.posted_a_month ,elapsedMonths ));
                } else {
                    publicationDateView.setText(getContext().getResources().getString(R.string.posted_months,elapsedMonths));
                }
            }
        }


        Log.d(TAG, "Today's date in the current format " + todayFormattedDate);
        Log.d(TAG, "Days " + elapsedDays + " Hours " + elapsedHours + " Minutes " + elapsedMinutes + " Seconds " + elapsedSeconds);
        return listItemView;
    }
}
