package sergio.sastre.multiplying.quality.of.unittests.ui.login

import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import net.jqwik.api.*
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.runner.RunWith
import org.junit.platform.runner.JUnitPlatform
import sergio.sastre.multiplying.quality.of.unittests.R

@RunWith(JUnitPlatform::class)
class LoginActivityTest2 {

    @Provide
    fun noUpperCase(): Arbitrary<String> =
        Arbitraries.strings().ascii().filter { it.matches("[^A-Z]".toRegex()) }

    @Property(tries = 1)
    fun loginActivityTest(@ForAll("noUpperCase") password: String?) {

        val appCompatEditText = onView(
            allOf(
                withId(R.id.username),
                childAtPosition(
                    allOf(
                        withId(R.id.container),
                        childAtPosition(
                            withId(android.R.id.content),
                            0
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatEditText.perform(replaceText(password), closeSoftKeyboard())

        val appCompatEditText2 = onView(
            allOf(
                withId(R.id.username), withText("hello"),
                childAtPosition(
                    allOf(
                        withId(R.id.container),
                        childAtPosition(
                            withId(android.R.id.content),
                            0
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatEditText2.perform(click())
    }


    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}