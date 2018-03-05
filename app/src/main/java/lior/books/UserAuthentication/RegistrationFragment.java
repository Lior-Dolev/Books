package lior.books.UserAuthentication;


import android.arch.lifecycle.LiveData;
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
import android.widget.TextView;

import lior.books.R;

public class RegistrationFragment extends Fragment {
    private EditText emailEditText;
    private EditText passwordEditText;
    private OnRegistrationFragmentInteractionListener mListener;
    private RegistrationViewModel registrationViewModel;

    public void setListener(OnRegistrationFragmentInteractionListener listener) {
        this.mListener = listener;
    }

    public RegistrationFragment() {
    }

    public static RegistrationFragment newInstance() {
        RegistrationFragment fragment = new RegistrationFragment();
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
        View view = inflater.inflate(R.layout.fragment_registration, container, false);

        emailEditText = view.findViewById(R.id.registration_email);
        passwordEditText = view.findViewById(R.id.registration_password);

        Button registerButton = view.findViewById(R.id.registration_createaccount);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener != null){
                    String email = emailEditText.getText().toString();
                    String password = passwordEditText.getText().toString();

                    registrationViewModel.createUser(email, password).observe(getFragmentManager().findFragmentById(R.id.registration_container),
                            new Observer<String>() {
                                @Override
                                public void onChanged(@Nullable String userID) {

                                    if(userID ==  null || userID.isEmpty()) {
                                        Log.d("TAG", "null");
                                        mListener.OnAccountCreationFailure();
                                    }
                                    else {
                                        Log.d("TAG", userID);
                                        mListener.OnAccountCreated(userID);
                                    }
                                }
                            });
                }
            }
        });

        TextView login = view.findViewById(R.id.registration_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mListener != null){

                    mListener.OnLoginClick();
                }
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnRegistrationFragmentInteractionListener) {
            mListener = (OnRegistrationFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }

        registrationViewModel = new ViewModelProviders().of(this).get(RegistrationViewModel.class);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnRegistrationFragmentInteractionListener {
        void OnAccountCreated(String userID);
        void OnAccountCreationFailure();
        void OnLoginClick();
    }
}
