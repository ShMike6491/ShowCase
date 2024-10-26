package org.example.showcase.store.ui.details

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.arkivanov.decompose.Cancellation
import com.arkivanov.decompose.value.Value
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.example.showcase.MainActivity
import org.example.showcase.common.ui.model.UiIntent
import org.example.showcase.common.ui.model.asUiString
import org.example.showcase.store.ui.model.ProductDetailState
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertTrue

// todo: similarly test the feed screen
class DetailsScreenKtTest {

    @get:Rule
    var composeTestRule = createAndroidComposeRule(MainActivity::class.java)

    private val mockComponent: DetailComponent = mockk(relaxed = true)
    private val testState = ProductDetailState()

    @Test
    fun visibility_loadingState_visible() {
        every { mockComponent.model } returns testState
            .copy(
                isLoading = true,
                errorMessage = null
            )
            .asModel()

        composeTestRule.activity.setContent {
            DetailScreen(mockComponent)
        }

        composeTestRule.onNodeWithTag("contentContainer")
            .assertIsNotDisplayed()
        composeTestRule.onNodeWithTag("errorText")
            .assertIsNotDisplayed()
        composeTestRule.onNodeWithTag("loadingContainer")
            .assertIsDisplayed()
    }

    @Test
    fun visibility_errorState_visible() {
        val testErrorMsg = "Something went wrong!"

        every { mockComponent.model } returns testState
            .copy(
                isLoading = false,
                errorMessage = testErrorMsg.asUiString()
            )
            .asModel()

        composeTestRule.activity.setContent {
            DetailScreen(mockComponent)
        }

        composeTestRule.onNodeWithTag("contentContainer")
            .assertIsNotDisplayed()
        composeTestRule.onNodeWithTag("loadingContainer")
            .assertIsNotDisplayed()
        composeTestRule.onNodeWithTag("errorText")
            .assertIsDisplayed()
            .assertTextEquals(testErrorMsg)
    }

    @Test
    fun visibility_content_visible() {
        every { mockComponent.model } returns testState
            .copy(isLoading = false, errorMessage = null)
            .asModel()

        composeTestRule.activity.setContent {
            DetailScreen(mockComponent)
        }

        composeTestRule.onNodeWithTag("loadingContainer")
            .assertIsNotDisplayed()
        composeTestRule.onNodeWithTag("errorText")
            .assertIsNotDisplayed()
        composeTestRule.onNodeWithTag("contentContainer")
            .assertIsDisplayed()
    }

    @Test
    fun visibility_description_visible() {
        val testMsg = "Test description"

        every { mockComponent.model } returns testState
            .copy(
                isLoading = false,
                errorMessage = null,
                descriptionText = testMsg.asUiString()
            )
            .asModel()

        composeTestRule.activity.setContent {
            DetailScreen(mockComponent)
        }

        composeTestRule.onNodeWithTag("descriptionTagText")
            .assertIsDisplayed()
            .assertTextEquals("Description")

        composeTestRule.onNodeWithTag("descriptionText")
            .assertIsDisplayed()
            .assertTextEquals(testMsg)
    }

    @Test
    fun visibility_description_hidden() {
        every { mockComponent.model } returns testState
            .copy(
                isLoading = false,
                errorMessage = null,
                descriptionText = null
            )
            .asModel()

        composeTestRule.activity.setContent {
            DetailScreen(mockComponent)
        }

        composeTestRule.onNodeWithTag("descriptionTagText")
            .assertIsNotDisplayed()
        composeTestRule.onNodeWithTag("descriptionText")
            .assertIsNotDisplayed()
    }

    @Test
    fun clickEvent_navigationIconClick_active() {
        val intentSlot = slot<UiIntent>()

        every { mockComponent.onIntent(capture(intentSlot)) } returns Unit
        every { mockComponent.model } returns testState
            .copy(isLoading = false, errorMessage = null)
            .asModel()

        composeTestRule.activity.setContent {
            DetailScreen(mockComponent)
        }

        composeTestRule.onNodeWithTag("headerNavigationIconButton")
            .assertIsDisplayed()
            .performClick()

        verify(exactly = 1) { mockComponent.onIntent(any()) }
        assertTrue(intentSlot.captured is UiIntent.ButtonClick)
    }

    // todo: continue testing
}


fun <T: Any> T.asModel(): Value<T> {
    return object : Value<T>() {
        override val value: T get() = this@asModel

        override fun subscribe(observer: (T) -> Unit): Cancellation {
            return Cancellation {}
        }
    }
}