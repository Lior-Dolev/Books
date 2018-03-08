package lior.books.Post;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

import lior.books.Post.Model.PostQuote;
import lior.books.Post.Model.PostQuoteRepository;
import lior.books.R;

import static android.support.v4.content.ContextCompat.getDrawable;


public class FeedFragment extends Fragment {
    ListView list;
    List<PostQuote> data = new LinkedList<>();
    FeedListAdapter adapter;
    FeedViewModel feedViewModel;

    private OnFeedInteractionListener mListener;

    public void setListener(OnFeedInteractionListener listener) {
        this.mListener = listener;
    }

    public FeedFragment() {
    }

    public static FeedFragment newInstance() {
        FeedFragment fragment = new FeedFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_feed, container, false);

        list = view.findViewById(R.id.feed_list);
        adapter = new FeedListAdapter();
        list.setAdapter(adapter);

        ImageButton addButton = view.findViewById(R.id.feed_add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.OnAddClick();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFeedInteractionListener) {
            mListener = (OnFeedInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }


        feedViewModel = new ViewModelProviders().of(this).get(FeedViewModel.class);

        feedViewModel.getPosts().observe(this, new Observer<List<PostQuote>>() {
            @Override
            public void onChanged(@Nullable List<PostQuote> posts) {

                data = posts;
                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFeedInteractionListener {
        void OnAddClick();
    }

    class FeedListAdapter extends BaseAdapter {
        LayoutInflater inflater = FeedFragment.this.getLayoutInflater();

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null){
                convertView = inflater.inflate(R.layout.post_row,null);
            }

            TextView quote = convertView.findViewById(R.id.post_row_quote);
            TextView bookName = convertView.findViewById(R.id.post_row_bookname);
            TextView author = convertView.findViewById(R.id.post_row_author);

            final ImageView imageView = convertView.findViewById(R.id.post_row_image);
            final ProgressBar progressBar = convertView.findViewById(R.id.post_row_progressBar);

            final PostQuote post = data.get(position);
            quote.setText(post.QuoteText);
            bookName.setText(post.BookName);
            author.setText(post.Author);

            imageView.setTag(post.ImageURL);

            imageView.setImageDrawable(getDrawable(getContext(), R.drawable.cameraicon));

            if (post.ImageURL != null && !post.ImageURL.isEmpty() && !post.ImageURL.equals("")){
                progressBar.setVisibility(View.VISIBLE);
                feedViewModel.getImage(post.ImageURL, new PostQuoteRepository.GetImageListener() {
                    @Override
                    public void onSuccess(Bitmap image) {
                        String tagUrl = imageView.getTag().toString();
                        if (tagUrl.equals(post.ImageURL)) {
                            imageView.setImageBitmap(image);
                            progressBar.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFail() {
                        progressBar.setVisibility(View.GONE);
                    }
                });
            }

            return convertView;
        }
    }
}
