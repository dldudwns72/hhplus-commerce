package kr.hhplus.be.server.infra.coupon

import kr.hhplus.be.server.domain.coupon.CouponEntity
import org.springframework.data.jpa.repository.JpaRepository

interface CouponJpaRepository : JpaRepository<CouponEntity, Long> {
}