package com.alome.tellerium.Activities

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Html
import android.util.Log
import androidx.fragment.app.Fragment
import com.alome.tellerium.R
import com.alome.tellerium.Utils.AppPref
import com.github.appintro.AppIntro
import com.github.appintro.AppIntroFragment
import com.github.appintro.AppIntroPageTransformerType


class Onboarding : AppIntro() {
    companion object {
        fun startActivity(context: Context) {
            val intent = Intent(context, Onboarding::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val companyName = Html.fromHtml("<b>Tellerium</b>");
        addSlide(AppIntroFragment.newInstance(
                "Welcome!",

                "This is an Android Developer Based Assessment from $companyName to create a  simple farmer Management Dashboard",
                imageDrawable = R.drawable.ic_welcome,
                backgroundDrawable = R.drawable.bg_app_intro,
                titleColor =Color.BLACK,
                descriptionColor = Color.BLACK
        ))

        addSlide(AppIntroFragment.newInstance(
                "Get Started!",
                "Hoping you'd have a wonderful experience  ",
                imageDrawable = R.drawable.ic_farm_girl,
                backgroundDrawable = R.drawable.bg_app_intro,
                titleColor =Color.BLACK,
                descriptionColor = Color.BLACK
        ))
        setTransformer(AppIntroPageTransformerType.Parallax())
//        set
        setColorSkipButton(Color.BLACK)
        setColorDoneText(Color.BLACK)
        setProgressIndicator()
        setNextArrowColor(Color.BLACK)
    }

    public override fun onSkipPressed(currentFragment: Fragment?) {
        super.onSkipPressed(currentFragment)
        AppPref.isAppIntroShown = true
        val intent = Intent(this, Login::class.java)
        startActivity(intent)
        finish()
    }

    public override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)
        AppPref.isAppIntroShown = true
        val intent = Intent(this, Login::class.java)
        startActivity(intent)
        finish()
    }
}