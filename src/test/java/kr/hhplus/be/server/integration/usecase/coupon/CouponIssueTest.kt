import kr.hhplus.be.server.controller.coupon.CouponUserController
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@WebMvcTest(CouponUserController::class)
class CouponControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `쿠폰 발급 API 통합 테스트`() {
        // given
        val couponId = 1L
        val userId = 1L

        // when
        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/coupon/$couponId/users/$userId")
                .contentType(MediaType.APPLICATION_JSON)
        )
            // then
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data.couponId").value(couponId))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data.userId").value(userId))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data.issuedAt").value("2025-01-18T12:00:00"))
    }
}
