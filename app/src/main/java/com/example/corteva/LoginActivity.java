package com.example.corteva;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private UserLoginTask mAuthTask = null;
    private EditText email;
    private EditText contra;
    private View mProgressView;
    private View mLoginFormView;
    private String nombreUser;
    private String site;
    private CheckBox chkRememberMe;
    private LinearLayout form;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        PackageManager manager = this.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            //String packageName = info.packageName;
            //int versionCode = info.versionCode;
            TextView version = findViewById(R.id.version);
            version.setText("Versión instalada: " + Integer.toString(info.versionCode));
        } catch (PackageManager.NameNotFoundException e) {
            TextView version = findViewById(R.id.version);
            version.setText("Versión instalada: 0");
        }

        //Primer sincronizacion automatica
        //IMPORTANT Se hace aca para que funcione el versionado
        new Syncronizer(LoginActivity.this, "Main").execute();

        // Set up the login form.
        form =  findViewById(R.id.loginForm);
        chkRememberMe = (CheckBox) findViewById(R.id.checkRecordar);
        email = findViewById(R.id.email);
        contra = findViewById(R.id.contra);

        contra.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        //Asigna el evento clic al button
        Button mEmailSignInButton = findViewById(R.id.button);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        FloatingActionButton newSyncro = findViewById(R.id.newSyncro);
        newSyncro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Primer sincronizacion
                new Syncronizer(LoginActivity.this, "Completo").execute();
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        // La actividad se ha vuelto visible (ahora se "reanuda").
        // Check if UserResponse is Already Logged In
        if(SaveSharedPreference.getLoggedStatus(getApplicationContext())) {
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            Bundle extras = new Bundle();
            extras.putString("useremail", SaveSharedPreference.getLoggedEmail(getApplicationContext()));
            extras.putString("username", SaveSharedPreference.getLoggedUsername(getApplicationContext()));
            intent.putExtras(extras);
            startActivity(intent);
        } else {
            form.setVisibility(View.VISIBLE);
        }
    }

    private void attemptLogin() {

        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        email.setError(null);
        contra.setError(null);

        // Store values at the time of the login attempt.
        String e_mail = email.getText().toString();
        String password = contra.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            contra.setError(getString(R.string.error_invalid_password));
            focusView = contra;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(e_mail)) {
            email.setError(getString(R.string.error_field_required));
            focusView = email;
            cancel = true;
        } else if (!isEmailValid(e_mail)) {
            email.setError(getString(R.string.error_invalid_email));
            focusView = email;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            //showProgress(true);
            mAuthTask = new UserLoginTask(e_mail, password, this);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 3;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // Si la version de Android lo permite hace un fade del progress.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // Como el ViewPropertyAnimator no esta disponible simplemente se ocultan los views.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;
        private final Context mContext;

        UserLoginTask(String email, String password, Context context) {
            mEmail = email;
            mPassword = password;
            mContext = context;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                ArrayList<String> usuario = new ControladorUsuarios(mContext).validarUsuario(mEmail, mPassword);
                nombreUser = usuario.get(0);
                site = usuario.get(1);
                return true;
            } catch (Exception e){
                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                if (chkRememberMe.isChecked())
                {
                    SaveSharedPreference.setLoggedIn(getApplicationContext(), true, mEmail, nombreUser, site);
                } else {
                    SaveSharedPreference.setLoggedIn(getApplicationContext(), false, mEmail, nombreUser, site);
                }
                finish();
                /* si todo anda bien se abre el formulario MainActivity */
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                Bundle extras = new Bundle();
                extras.putString("useremail", mEmail); //Put your id to your next Intent
                extras.putString("username", nombreUser); //Put your id to your next Intent
                intent.putExtras(extras);
                LoginActivity.this.startActivity(intent);
            } else {
                contra.setError(getString(R.string.error_incorrect_password));
                contra.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}