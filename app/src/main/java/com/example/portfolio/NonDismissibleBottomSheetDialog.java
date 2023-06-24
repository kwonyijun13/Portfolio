package com.example.portfolio;

import android.content.Context;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;

import com.google.android.material.bottomsheet.BottomSheetDialog;

public class NonDismissibleBottomSheetDialog extends BottomSheetDialog {

    public NonDismissibleBottomSheetDialog(@NonNull Context context) {
        super(context);
    }

    public NonDismissibleBottomSheetDialog(@NonNull Context context, @StyleRes int theme) {
        super(context, theme);
    }

    protected NonDismissibleBottomSheetDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        // Prevent touch events from dismissing the dialog
        return false;
    }
}
