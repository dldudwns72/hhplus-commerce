package kr.hhplus.be.server.infra.coupon

import jakarta.persistence.LockModeType
import kr.hhplus.be.server.domain.coupon.CouponUserEntity
import kr.hhplus.be.server.domain.coupon.IssuedCouponResult
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface CouponUserJpaRepository : JpaRepository<CouponUserEntity, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    override fun findById(id: Long): Optional<CouponUserEntity>

    @Query("SELECT IssuedCouponResult(cu.couponId,cu.userId,cp.discountType,cp.discountValue) FROM CouponUserEntity cu INNER JOIN CouponPolicyEntity cp ON cp.couponId = cu.couponId")
    fun findIssuedCouponResultByCouponIdAndUserId(
        @Param("userId") couponId: Long,
        @Param("userId") userId: Long
    ): IssuedCouponResult?

    @Query("SELECT IssuedCouponResult(cu.couponId,cu.userId,cp.discountType,cp.discountValue) FROM CouponUserEntity cu INNER JOIN CouponPolicyEntity cp ON cu.couponId = cp.couponId WHERE cu.userId = :userId")
    fun findIssuedCouponResultByUserId(@Param("userId") userId: Long): List<IssuedCouponResult>

}