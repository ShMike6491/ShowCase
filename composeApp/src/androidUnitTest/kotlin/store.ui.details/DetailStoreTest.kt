package store.ui.details

import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.extensions.coroutines.states
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.runBlocking
import org.example.showcase.common.domain.model.IError
import org.example.showcase.common.domain.model.Result
import org.example.showcase.common.ui.model.UiIntent
import org.example.showcase.common.ui.model.UiString
import org.example.showcase.store.domain.StoreProductsInteractor
import org.example.showcase.store.domain.model.IProduct
import org.example.showcase.store.ui.details.DetailsExecutorImpl
import org.example.showcase.store.ui.details.DetailsReducerImpl
import org.example.showcase.store.ui.model.ProductDetailState
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class DetailStoreTest {

    val state = ProductDetailState()
    val dispatcher = Dispatchers.Unconfined
    val mockInteractor: StoreProductsInteractor = mockk()
    val mockData: IProduct = mockk(relaxed = true)

    lateinit var store: Store<UiIntent, ProductDetailState, Nothing>

    @BeforeTest
    fun setup() {
        val module = module {
            single { mockInteractor }
        }

        stopKoin()
        startKoin {
            modules(module)
        }
    }

    @AfterTest
    fun stopKoinAfterTest() = stopKoin()

    @Test
    fun loading_state_on_initialization() {
        coEvery { mockInteractor.getProductById(any()) } returns Result.Loading

        createStore("test_Id")

        assertEquals(state, store.state)
    }

    @Test
    fun passed_id_on_initialization() = runBlocking(dispatcher) {
        val idSlot = slot<String>()
        val testId = "id"

        coEvery { mockInteractor.getProductById(capture(idSlot)) } returns Result.Loading

        createStore(testId)

        store.states.take(1).last()

        assertEquals(testId, idSlot.captured)
        coVerify(exactly = 1) { mockInteractor.getProductById(any()) }
    }

    @Test
    fun error_state_on_error_result() = runBlocking(dispatcher) {
        val error = object : IError {}
        coEvery { mockInteractor.getProductById(any()) } returns Result.Error(error)

        createStore("test")

        val expected = "Something went wrong!"
        val actualState = store.states.take(1).last().errorMessage
        val actual = (actualState as? UiString.DynamicString)?.value

        assertEquals(expected, actual)
    }

    @Test
    fun correct_state_on_successful_result() = runBlocking(dispatcher) {
        coEvery { mockInteractor.getProductById(any()) } returns Result.Success(mockData)
        every { mockData.imageUrl } returns "https://www.sample.image.jpeg"
        every { mockData.category } returns "test category"
        every { mockData.priceUsd } returns 2.0
        every { mockData.title } returns "test title"
        every { mockData.description } returns "test description"

        createStore("test")

        val actualState = store.states.take(1).last()

        val expectedLoading = false
        val actualLoading = actualState.isLoading
        assertEquals(expectedLoading, actualLoading)

        val expectedError = null
        val actualError = actualState.errorMessage
        assertEquals(expectedError, actualError)

        val expectedTitle = "test title"
        val actualTitle = (actualState.titleText as? UiString.DynamicString)?.value
        assertEquals(expectedTitle, actualTitle)

        val expectedDescription = "test description"
        val actualDescription = (actualState.descriptionText as? UiString.DynamicString)?.value
        assertEquals(expectedDescription, actualDescription)

        val expectedCategory = "test category"
        val actualCategory = actualState.category
        assertEquals(expectedCategory, actualCategory)

        val expectedPrice = "$2.0"
        val actualPrice = (actualState.price?.formatted as? UiString.DynamicString)?.value
        assertEquals(expectedPrice, actualPrice)

        val expectedImage = "https://www.sample.image.jpeg"
        val actualImage = actualState.imageUrl
        assertEquals(expectedImage, actualImage)
    }

    private fun createStore(id: String) {
        val factory = DefaultStoreFactory()
        store = object : Store<UiIntent, ProductDetailState, Nothing> by factory.create(
            name = "test",
            initialState = state,
            bootstrapper = SimpleBootstrapper(Unit),
            reducer = DetailsReducerImpl,
            executorFactory = { DetailsExecutorImpl(id, dispatcher, dispatcher) }
        ) {}
    }
}