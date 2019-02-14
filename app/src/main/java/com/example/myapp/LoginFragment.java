package com.example.myapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NavigationRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.VibrationEffect;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginFragment extends Fragment {

    private EditText Loginid,password;
    private Button Login;
    private TextView textView;
    private FirebaseAuth mauth;
    private TextInputLayout textInputLayout1, textInputLayout2;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        container.removeAllViewsInLayout();

        View newClaimRequest= inflater.inflate(R.layout.fragment_login, container, false);
        SpannableString ss = new SpannableString("Not a Member Yet?Signup Now");
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new SignupFragment() ).addToBackStack("").commit();
            }
        };

        ss.setSpan(clickableSpan , 17,23 , Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView =(TextView) newClaimRequest.findViewById(R.id.textView);
        textView.setText(ss);
        textView.setMovementMethod(LinkMovementMethod.getInstance());

        return newClaimRequest;


    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Login=getView().findViewById(R.id.Login_button);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Loginid = getView().findViewById(R.id.Loginid);
                password = getView().findViewById(R.id.password);
                String user = Loginid.getText().toString();
                String pass = password.getText().toString();
                textInputLayout1=getView().findViewById(R.id.textInputLayout2);
                mauth = FirebaseAuth.getInstance();
                if (user.isEmpty() || pass.isEmpty()) {

                    showMessage("please enetr  All  valid fields");

                } else {
                    mauth.signInWithEmailAndPassword(user, pass)
                            .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful()) {
                                        getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new user() ).addToBackStack("").commit();
                                    } else {
                                        showMessage("faild signInWithEmailAndPassword");
                                    }

                                }


                            });

                    }
                }
            });

        }



    private void showMessage(String s) {

        Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
    }



}





