package com.alome.tellerium.Activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import com.alome.tellerium.R
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
        val companyName = Html.fromHtml("<b> Tellerium</b>");
        addSlide(AppIntroFragment.newInstance(
                "Welcome!",
                "This is an Android Developer Based Assessment by $companyName to create a  simple farmer Management Dashboard",
                imageDrawable = R.drawable.ic_welcome
        ))

        addSlide(AppIntroFragment.newInstance(
                "Get Started!",
                "Ready when you are!, hoping you'd have a wonderful experience  ",
                imageDrawable = R.drawable.ic_farm_girl
        ))
        setTransformer(AppIntroPageTransformerType.Parallax())
    }

    public override fun onSkipPressed(currentFragment: Fragment?) {
        super.onSkipPressed(currentFragment)
        finish()
    }

    public override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)
        finish()
    }
}