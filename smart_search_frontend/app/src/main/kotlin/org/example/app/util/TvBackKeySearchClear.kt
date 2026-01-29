package org.example.app.util

import android.view.KeyEvent
import android.widget.EditText
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher

/**
 * TV/STB back-key handling helpers.
 *
 * Many STB remotes (RCU) expect BACK to clear the current query when the search box is focused,
 * instead of navigating away. This utility keeps the behavior isolated and consistent across
 * activity/fragment search inputs.
 */
object TvBackKeySearchClear {

    /**
     * PUBLIC_INTERFACE
     * Registers an OnBackPressedCallback that:
     * - clears [editText] when it is focused AND has non-empty text
     * - keeps DPAD focus on the [editText] after clearing
     * - otherwise does NOT consume back (normal back navigation proceeds)
     *
     * IMPORTANT: The callback is always enabled, but it conditionally consumes the event.
     * This keeps back navigation unchanged everywhere else.
     */
    fun registerBackToClearSearch(
        backPressedDispatcher: OnBackPressedDispatcher,
        editText: EditText
    ): OnBackPressedCallback {
        return object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (editText.isFocused && !editText.text.isNullOrEmpty()) {
                    // Clear query, but keep focus so the user can continue typing with the remote.
                    editText.setText("")
                    editText.requestFocus()
                    // Consumed.
                    return
                }
                // Not consumed: temporarily disable so dispatcher can continue normal back.
                isEnabled = false
                try {
                    backPressedDispatcher.onBackPressed()
                } finally {
                    isEnabled = true
                }
            }
        }.also { callback ->
            backPressedDispatcher.addCallback(callback)
        }
    }

    /**
     * PUBLIC_INTERFACE
     * Optional fallback/extra safety: attach an OnKeyListener directly to the EditText.
     * This can help on some TV input stacks where the focused view receives KEYCODE_BACK.
     *
     * Returns true only when the EditText is focused AND has non-empty text (consumes BACK).
     */
    fun consumeBackKeyToClearIfNeeded(editText: EditText): Boolean {
        if (editText.isFocused && !editText.text.isNullOrEmpty()) {
            editText.setText("")
            editText.requestFocus()
            return true
        }
        return false
    }

    /**
     * PUBLIC_INTERFACE
     * Installs a View.OnKeyListener on [editText] to clear on KEYCODE_BACK (ACTION_UP),
     * consuming only when the query is non-empty.
     */
    fun installKeyListener(editText: EditText) {
        editText.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_UP) {
                consumeBackKeyToClearIfNeeded(editText)
            } else {
                false
            }
        }
    }
}
