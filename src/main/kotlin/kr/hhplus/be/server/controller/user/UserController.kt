package kr.hhplus.be.server.controller.user

import kr.hhplus.be.server.controller.common.SingleResponse
import kr.hhplus.be.server.controller.user.dto.request.BalanceRequest
import kr.hhplus.be.server.controller.user.dto.response.UserResponse
import kr.hhplus.be.server.controller.user.dto.response.toResponse
import kr.hhplus.be.server.domain.user.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/user")
class UserController(
    private val userService: UserService
) : UserSpecificationApi {

    @PostMapping("/{userId}/balance")
    override fun chargeBalance(
        @PathVariable userId: Long,
        @RequestBody request: BalanceRequest
    ): ResponseEntity<SingleResponse<UserResponse>> {
        return ResponseEntity(
            SingleResponse.execute {
                userService.chargePoint(userId, request.point).toResponse()
            },
            HttpStatus.OK
        )
    }

    @GetMapping("/{userId}")
    override fun gatUserBalance(
        @PathVariable userId: Long
    ): ResponseEntity<SingleResponse<UserResponse>> {
        return ResponseEntity(
            SingleResponse.execute {
                userService.getUserById(userId).toResponse()
            }, HttpStatus.OK
        )
    }
}

