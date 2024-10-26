package org.example.showcase.store.ui.details

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.example.showcase.common.navigation.INavigator
import org.example.showcase.common.ui.model.UiIntent
import org.example.showcase.store.ui.model.ProductDetailState
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class DetailComponentTest : KoinTest {

    private val lifecycle = LifecycleRegistry()

    val mockContext: ComponentContext = DefaultComponentContext(lifecycle)
    val mockNavigator: INavigator = mockk()
    val mockStore: DetailsStore = mockk(relaxed = true)

    lateinit var component: DetailComponent

    @BeforeTest
    fun setup() {
        val module = module {
            single { mockStore }
        }

        stopKoin()
        startKoin {
            modules(module)
        }

        component = DetailComponent("testId", mockContext, mockNavigator)
    }

    @AfterTest
    fun stopKoinAfterTest() = stopKoin()

    @Test
    fun initialization_storeStateInitialValue_identicalToStore() {
        val testState = ProductDetailState(id = "state_id_test")

        every { mockStore.state } returns testState

        val expected = "state_id_test"
        val actual = component.model.value.id

        assertEquals(expected, actual)
    }

    @Test
    fun uiIntent_navigationHandling_navigatorCall() {
        val testIntent = UiIntent.ButtonClick()

        every { mockNavigator.navigateUp() } returns Unit

        component.onIntent(testIntent)

        verify(exactly = 1) { mockNavigator.navigateUp() }
    }

    @Test
    fun uiIntent_stateUpdate_storeAcceptCall() {
        val testIntent = UiIntent.ToggleChange(true)
        val intentSlot = slot<UiIntent>()

        every { mockStore.accept(capture(intentSlot)) } returns Unit

        component.onIntent(testIntent)

        assertEquals(testIntent, intentSlot.captured)
        verify(exactly = 1) { mockStore.accept(any()) }
    }
}