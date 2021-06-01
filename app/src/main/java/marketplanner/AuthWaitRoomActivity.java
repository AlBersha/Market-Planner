package marketplanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import marketplanner.stocks.R;

public class AuthWaitRoomActivity extends AppCompatActivity {

    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_wait_room);

        biometricPrompt = new BiometricPrompt(AuthWaitRoomActivity.this,
                ContextCompat.getMainExecutor(this), new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode,
                                              @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(getApplicationContext(),
                        "Authentication error: " + errString, Toast.LENGTH_SHORT)
                        .show();
                setContentView(R.layout.auth_wait_room);
            }

            @Override
            public void onAuthenticationSucceeded(
                    @NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(getApplicationContext(),
                        "Authentication succeeded!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(getApplicationContext(), "Authentication failed",
                        Toast.LENGTH_SHORT)
                        .show();
                if (biometricPrompt == null)
                    Toast.makeText(getApplicationContext(), "Prompt is null",
                        Toast.LENGTH_SHORT)
                        .show();

                setContentView(R.layout.auth_wait_room);
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Verify your identity")
                .setSubtitle("marketplanner.inc needs to verify it's you")
                .setNegativeButtonText("Cancel authorization")
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();

        Button view = (Button)findViewById(R.id.auth_button);
        view.setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(), "Authorize button pressed",
                    Toast.LENGTH_SHORT)
                    .show();

            biometricPrompt.authenticate(promptInfo);
        });
    }
}