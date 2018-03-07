package lior.books.Post;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

import lior.books.Post.Model.PostQuote;
import lior.books.R;
import lior.books.Utilities;

public class PostQuoteFragment extends Fragment {
    private static final String ARG_USER_ID = "userID";
    private OnPostQuoteInteractionListener mListener;
    private PostQuoteViewModel postQuoteViewModel;
    private String mUserID;

    public void setListener(OnPostQuoteInteractionListener listener) {
        this.mListener = listener;
    }

    public PostQuoteFragment() {
    }

    public static PostQuoteFragment newInstance(String userID) {
        PostQuoteFragment fragment = new PostQuoteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_USER_ID, userID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUserID = getArguments().getString(ARG_USER_ID);
        }


        postQuoteViewModel = new ViewModelProviders().of(this, new PostQuoteViewModelFactory(this.mUserID)).get(PostQuoteViewModel.class);

        postQuoteViewModel.getPosts().observe(this, new Observer<List<PostQuote>>() {
            @Override
            public void onChanged(@Nullable List<PostQuote> employees) {
                Log.d("TAG", "list updated");
//                employeesList = employees;
//                if (adapter != null) adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post_quote, container, false);

        Button postButton = view.findViewById(R.id.post_post);
        final EditText booknameEditText = view.findViewById(R.id.post_bookname);
        final EditText authorEditText = view.findViewById(R.id.post_author);
        final EditText quoteEditText = view.findViewById(R.id.post_qoute);

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PostQuote post = new PostQuote();
                post.ID = Utilities.GenerateID();
                post.BookName = booknameEditText.getText().toString();
                post.Author = authorEditText.getText().toString();
                post.QuoteText = quoteEditText.getText().toString();
                post.LaseUpdated = Utilities.GetDateAsFloat();

                postQuoteViewModel.addPost(post, mUserID);
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnPostQuoteInteractionListener) {
            mListener = (OnPostQuoteInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnPostQuoteInteractionListener {

    }
}
