package com.example.myapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SignupFragment extends Fragment {
    private TextView textView;
    private TextInputEditText Firstname, Lastname, Email, Age, Password, ConfirmPassword;
    private Button Signup;
    private FirebaseAuth mauth;

    int number=0;
    boolean a;
    Activity activity = getActivity();



    public SignupFragment() {
        // Required empty public constructor
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View newClaimRequest= inflater.inflate(R.layout.fragment_signup, container, false);
        container.removeAllViewsInLayout();
        SpannableString ss = new SpannableString("Already a member Login Now");
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new LoginFragment() ).addToBackStack("").commit();
            }
        };

        ss.setSpan(clickableSpan , 17,23 , Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView =(TextView) newClaimRequest.findViewById(R.id.textView1);
        textView.setText(ss);
        textView.setMovementMethod(LinkMovementMethod.getInstance());

        return newClaimRequest;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Signup=getView().findViewById(R.id.Signup_button);
        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Firstname = getView().findViewById(R.id.Signup_first_name);
                Lastname = getView().findViewById(R.id.Signup_Last_name);
                Email = getView().findViewById(R.id.signup_email);
                Password = getView().findViewById(R.id.Signup_password);
                Age = getView().findViewById(R.id.Signup_Age);
                ConfirmPassword = getView().findViewById(R.id.Signup_password2);

                final String firstname = Firstname.getText().toString();
                final String lastname = Lastname.getText().toString();
                final String email = Email.getText().toString();
                String age = Age.getText().toString();
                final String password = Password.getText().toString();
                final String confirmPassword = ConfirmPassword.getText().toString();
                try {
                    number = Integer.parseInt(Age.getText().toString());
                } catch (NumberFormatException e) {
                    Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
                }


                if (firstname.isEmpty() || lastname.isEmpty() || email.isEmpty() || age.isEmpty() || password.isEmpty()) {

                    showMessage("please enetr  All  valid fields");
                } else if (firstname.isEmpty()) {

                    showMessage("please enter first name");


                } else if (lastname.isEmpty()) {
                    showMessage("please enter last name");


                } else if (email.isEmpty()) {
                    showMessage("please enter email id");

                } else if (a == isEmailValid(email)) {
                    showMessage("please enter  valid email id");

                } else if (age.isEmpty()) {
                    showMessage("please enter valid age");
                } else if (number < 1) {
                    showMessage("please enter valid age between 1 to 99");
                } else if (number > 99) {
                    showMessage("please enter valid age between 1 to 99");
                } else if (password.isEmpty()) {
                    showMessage("please enter valid password of length 8");
                } else if (password.length() < 8) {
                    showMessage("please enter valid password of length 8");
                } else if (!password.equals(confirmPassword)) {
                    Password.setText("");
                    ConfirmPassword.setText("");
                    showMessage("please enter both password same");
                } else {

                    mauth = FirebaseAuth.getInstance();
                    mauth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                showMessage("user createdd");
                                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new LoginFragment() ).addToBackStack("").commit();

                            } else {
                                showMessage(task.getException().getMessage());
                            }

                        }

                    });
                }
            }


    private void showMessage(String s) {

        Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
    }
            public boolean isEmailValid(String email)
            {
                String regExpn =
                        "\\b[A-Z0-9._%-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}\\b";

                CharSequence inputStr = email;

                Pattern pattern = Pattern.compile(regExpn,Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(inputStr);

                if(matcher.matches())
                    return true;
                else
                    return false;
            }
    });


        }









}





