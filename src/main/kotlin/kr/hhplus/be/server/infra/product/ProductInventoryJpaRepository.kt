package kr.hhplus.be.server.infra.product

import kr.hhplus.be.server.domain.product.ProductInventoryEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param


interface ProductInventoryJpaRepository : JpaRepository<ProductInventoryEntity, Long> {
    fun findByProductId(productId: Long): ProductInventoryEntity?

    // pi.product.id 유의
    @Modifying
    @Query("update ProductInventoryEntity pi set pi.inventory = :inventory where pi.product.id = :productId")
    fun updateInventory(@Param("productId") productId: Long, @Param("inventory") inventory: Int)


}