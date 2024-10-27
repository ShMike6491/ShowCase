package store.ui.details

import io.mockk.every
import io.mockk.mockk
import org.example.showcase.common.domain.model.IError
import org.example.showcase.common.domain.model.Result
import org.example.showcase.common.ui.model.UiString
import org.example.showcase.common.ui.model.asUiString
import org.example.showcase.store.domain.model.IProduct
import org.example.showcase.store.ui.details.DetailsReducerImpl.reduce
import org.example.showcase.store.ui.model.ProductDetailState
import kotlin.test.Test
import kotlin.test.assertEquals

class DetailsReducerImplTest {

    val mockProduct: IProduct = mockk(relaxed = true)

    @Test
    fun loading_loadingResultMsg_loadingStateUpdate() {
        val loadingResult = Result.Loading
        val initialState = ProductDetailState(
            isLoading = false,
            errorMessage = "test".asUiString()
        )

        val reducedState = initialState.reduce(loadingResult)

        assertEquals(true, reducedState.isLoading)
        assertEquals(null, reducedState.errorMessage)
    }

    @Test
    fun error_errorResultMsg_errorStateUpdate() {
        val customError = object : IError {}
        val errorResult = Result.Error(customError)
        val initialState = ProductDetailState(
            isLoading = true,
            errorMessage = null
        )

        val reducedState = initialState.reduce(errorResult)
        val expected = "Something went wrong!"
        val actual = (reducedState.errorMessage as? UiString.DynamicString)?.value

        assertEquals(false, reducedState.isLoading)
        assertEquals(expected, actual)
    }

    @Test
    fun success_resultDataMsg_stateDataUpdate() {
        val successfulResult = Result.Success(mockProduct)
        val initialState = ProductDetailState(
            isLoading = true,
            errorMessage = null
        )

        every { mockProduct.category } returns "category"

        val reducedState = initialState.reduce(successfulResult)

        val expected = "category"
        val actual = reducedState.category

        assertEquals(expected, actual)
    }
}