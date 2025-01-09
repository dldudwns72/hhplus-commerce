package kr.hhplus.be.server.infra.product

import kr.hhplus.be.server.domain.product.ProductEntity
import kr.hhplus.be.server.domain.product.ProductResult
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param


interface ProductJpaRepository : JpaRepository<ProductEntity, Long> {

    @Query("select ProductResult(p.id,p.name,p.price, pi.inventory) from ProductEntity p INNER JOIN ProductInventoryEntity pi ON p.id = pi.id WHERE p.id = :id")
    fun findProduct(@Param("id") id: Long): ProductResult


    @Query("select ProductResult(p.id,p.name,p.price, pi.inventory) from ProductEntity p INNER JOIN ProductInventoryEntity pi ON p.id = pi.id")
    fun findAllProducts(pageable: Pageable): Page<ProductResult>


}