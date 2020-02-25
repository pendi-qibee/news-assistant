package id.qibee.newsassistant.ui.setting

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import id.qibee.newsassistant.R

class SettingFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root, rootKey)
    }
}
