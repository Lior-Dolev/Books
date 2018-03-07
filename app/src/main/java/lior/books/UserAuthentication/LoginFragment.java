package lior.books.UserAuthentication;

//import android.app.Fragment;
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

public class LoginFragment extends Fragment {
    private EditText emailEditText;
    private EditText passwordEditText;
    private OnLoginFragmentInteractionListener mListener;
    private LoginViewModel loginViewModel;

    public void setListener(OnLoginFragmentInteractionListener listener) {
        this.mListener = listener;
    }

    public LoginFragment() {
    }

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
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
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        emailEditText = view.findViewById(R.id.login_email);
        passwordEditText = view.findViewById(R.id.login_password);

        Button loginButton = view.findViewById(R.id.login_loginbutton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mListener != null){
                    String email = emailEditText.getText().toString();
                    String password = passwordEditText.getText().toString();

                    loginViewModel.userLogin(email, password).observe(getFragmentManager().findFragmentById(R.id.login_container) , new Observer<String>() {
                        @Override
                        public void onChanged(@Nullable String userID) {

                            if(userID ==  null || userID.isEmpty()) {

                                mListener.OnLoginFailure();
                            }
                            else{

                                mListener.OnSuccessfulLogin(userID);
                            }
                        }
                    });
                }
            }
        });

        TextView registration = view.findViewById(R.id.login_signup);
        registration.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(mListener != null){
                    mListener.OnRegistrationClick();
                }
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnLoginFragmentInteractionListener) {
            mListener = (OnLoginFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }

        loginViewModel = new ViewModelProviders().of(this).get(LoginViewModel.class);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnLoginFragmentInteractionListener {
        void OnSuccessfulLogin(String userID);
        void OnLoginFailure();
        void OnRegistrationClick();
    }
}
