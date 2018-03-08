package lior.books.Post;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import lior.books.Post.Model.PostQuote;
import lior.books.R;
import lior.books.Utilities;

import static android.app.Activity.RESULT_OK;

public class PostQuoteFragment extends Fragment {
    private static final String ARG_USER_ID = "userID";
    private OnPostQuoteInteractionListener mListener;
    private PostQuoteViewModel postQuoteViewModel;
    private String mUserID;
    private ImageView imageView;
    Bitmap imageBitmap;

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

                postQuoteViewModel.addPost(post, mUserID, imageBitmap);
                mListener.OnPostAdded();
            }
        });

        ImageView camera = view.findViewById(R.id.post_camera);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });
        imageView = new ImageView(getContext());

        return view;
    }


    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
        }
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
        void OnPostAdded();
    }
}
