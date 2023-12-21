package org.workshift.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.workshift.model.Shop;
@Repository
public interface ShopRepository extends JpaRepository<Shop, String> {
    @Query("select shop FROM Shop shop where shop.shopId = :shopId")
    Shop findByIdWithoutJoin(@Param("shopId") String shopId);

    @Modifying(clearAutomatically = true)
    @Query("update Shop shop set shop.userId = :userId where shop.shopId = :shopId")
    void updateUserId(@Param("userId") String userId, @Param("shopId") String shopId);
}
