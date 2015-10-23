package com.com.mr_wrong.Settings


import android.app.Fragment
import android.os.Bundle
import android.preference.PreferenceFragment
import android.support.v7.app.AppCompatActivity
import com.example.mr_wrong.androidstudioproject.R

/**
 * Created by Mr_Wrong on 15/10/23.
 */
public class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        replaceFragment(android.R.id.content, SettingsFragment())
    }

    fun replaceFragment(viewId: Int, fragment: Fragment) {
        getFragmentManager().beginTransaction().replace(viewId, fragment).commit();
    }

    class SettingsFragment : PreferenceFragment() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            addPreferencesFromResource(R.xml.preferences)
        }
    }
}