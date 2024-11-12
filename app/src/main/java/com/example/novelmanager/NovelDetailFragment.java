package com.example.novelmanager;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class NovelDetailFragment extends Fragment {

    private TextView textViewTitle;
    private TextView textViewAuthor;
    private TextView textViewYear;
    private TextView textViewSynopsis;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_novel_detail, container, false);

        textViewTitle = view.findViewById(R.id.textViewTitle);
        textViewAuthor = view.findViewById(R.id.textViewAuthor);
        textViewYear = view.findViewById(R.id.textViewYear);
        textViewSynopsis = view.findViewById(R.id.textViewSynopsis);

        Bundle args = getArguments();
        if (args != null) {
            textViewTitle.setText(args.getString("title"));
            textViewAuthor.setText(args.getString("author"));
            textViewYear.setText(String.valueOf(args.getInt("year")));
            textViewSynopsis.setText(args.getString("synopsis"));
        }

        return view;
    }
}
