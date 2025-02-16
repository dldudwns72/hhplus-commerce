CREATE TABLE IF NOT EXISTS `users`
(
    `id`         bigint       NOT NULL AUTO_INCREMENT,
    `name`       varchar(255) NOT NULL,
    `balance`    bigint       NOT NULL,
    `created_at` datetime(6)  NOT NULL,
    `updated_at` datetime(6)  NOT NULL,
    `version`    int          NOT NULL DEFAULT 0,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

-- 10만 명의 사용자 데이터를 삽입
INSERT INTO users (balance, created_at, updated_at, name)
SELECT
    FLOOR(RAND() * 9000000) + 1000000,  -- 1,000,000 ~ 10,000,000 사이의 랜덤 balance
    TIMESTAMPADD(SECOND, FLOOR(RAND() * 86400), DATE_ADD('2020-01-01', INTERVAL FLOOR(RAND() * 2192) DAY)),  -- 현재 시간
    TIMESTAMPADD(SECOND, FLOOR(RAND() * 86400), DATE_ADD('2020-01-01', INTERVAL FLOOR(RAND() * 2192) DAY)),  -- 현재 시간
    CONCAT('User_', @user_id := @user_id + 1)  -- 'User_1', 'User_2' 형태로 이름 생성
FROM (
         SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION
         SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9 UNION SELECT 10) t1,
     (SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION
      SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9 UNION SELECT 10) t2,
     (SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION
      SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9 UNION SELECT 10) t3,
     (SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION
      SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9 UNION SELECT 10) t4,
     (SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION
      SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9 UNION SELECT 10) t5,
     (SELECT @user_id := 0) r  -- @user_id 변수 초기화
LIMIT 100000;


-- 상품(product) 10,000개 생성
INSERT INTO product (created_at, updated_at, price, name)
SELECT
    NOW(), NOW(),
    FLOOR(RAND() * 100000) + 1000,  -- 1,000 ~ 101,000 랜덤 가격
    CONCAT('Product_', LPAD(ROW_NUMBER() OVER (), 5, '0'))
FROM (
         SELECT 1 FROM
                      (SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5
                       UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9 UNION ALL SELECT 10) a,
                      (SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5
                       UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9 UNION ALL SELECT 10) b,
                      (SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5
                       UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9 UNION ALL SELECT 10) c,
                      (SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5
                       UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9 UNION ALL SELECT 10) d,
                      (SELECT 1 UNION ALL SELECT 2) e  -- 10 x 10 x 10 x 10 x 2 = 10,000개
     ) t
LIMIT 10000;

-- 생성된 product의 ID를 기반으로 product_inventory 10,000개 생성
INSERT INTO product_inventory (inventory, version, product_id)
SELECT
    FLOOR(RAND() * 500) + 1,  -- 1 ~ 500 랜덤 재고 수량
    0,
    id
FROM product
ORDER BY id
LIMIT 10000;

CREATE TABLE IF NOT EXISTS `coupon`
(
    `id`             bigint       NOT NULL AUTO_INCREMENT,
    `name`           varchar(200) NOT NULL,
    `capacity`       int          NOT NULL,
    `max_capacity`   int          NOT NULL,
    `discount_type`  varchar(100) NOT NULL,
    `discount_value` int          NOT NULL,
    `created_at`     datetime(6)  NOT NULL,
    `updated_at`     datetime(6)  NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

INSERT INTO coupon (capacity, discount_value, max_capacity, created_at, updated_at, name, discount_type)
SELECT
    FLOOR(RAND() * 1000) + 1,  -- 1 ~ 1000 랜덤 용량
    FLOOR(RAND() * 46) + 5,  -- 5 ~ 50 랜덤 할인값
    FLOOR(RAND() * 4000) + 1000,  -- 1000 ~ 5000 랜덤 최대 용량
    TIMESTAMPADD(SECOND, FLOOR(RAND() * 86400), DATE_ADD('2020-01-01', INTERVAL FLOOR(RAND() * 2192) DAY)),
    TIMESTAMPADD(SECOND, FLOOR(RAND() * 86400), DATE_ADD('2020-01-01', INTERVAL FLOOR(RAND() * 2192) DAY)),
    CONCAT('Coupon_', id),  -- 'Coupon_1', 'Coupon_2' 형태
    IF(RAND() > 0.5, 'AMOUNT', 'PERCENT')  -- 랜덤 할인 타입
FROM (
         SELECT (@rownum := @rownum + 1) AS id FROM
                                                   (SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION
                                                    SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9 UNION SELECT 10) t1,
                                                   (SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION
                                                    SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9 UNION SELECT 10) t2
                                                       CROSS JOIN (SELECT @rownum := 0) r
     ) temp
LIMIT 100;

CREATE TABLE IF NOT EXISTS `coupon_user`
(
    `id`         bigint      NOT NULL AUTO_INCREMENT,
    `user_id`    bigint      NOT NULL,
    `coupon_id`  bigint      NOT NULL,
    `is_used`    bit(1)      NOT NULL,
    `created_at` datetime(6) NOT NULL,
    `updated_at` datetime(6) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `orders`
(
    `id`           bigint      NOT NULL AUTO_INCREMENT,
    `coupon_id`    bigint      DEFAULT NULL,
    `product_id`   bigint      NOT NULL,
    `total_amount` bigint      NOT NULL,
    `user_id`      bigint      NOT NULL,
    `created_at`   datetime(6) NOT NULL,
    `status`       varchar(255) DEFAULT NULL,
    `updated_at`   datetime(6) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;


CREATE TABLE IF NOT EXISTS `order_product`
(
    `id`         bigint      NOT NULL AUTO_INCREMENT,
    `created_at` datetime(6) NOT NULL,
    `updated_at` datetime(6) NOT NULL,
    `quantity`   int         NOT NULL,
    `order_id`   bigint      NOT NULL,
    `product_id` bigint      NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;


-- 1. orders 테이블에 200,000개 더미 데이터 삽입
INSERT INTO orders (coupon_id, created_at, total_amount, updated_at, user_id, status)
SELECT
    CASE WHEN RAND() < 0.8 THEN FLOOR(RAND() * 100) + 1 ELSE NULL END,  -- 80% 확률로 쿠폰 적용
    TIMESTAMPADD(SECOND, FLOOR(RAND() * 86400), DATE_ADD('2020-01-01', INTERVAL FLOOR(RAND() * 2192) DAY)),
    FLOOR(RAND() * 490000) + 10000,  -- 10000 ~ 500000 랜덤 금액
    NOW(),
    FLOOR(RAND() * 10000) + 1,  -- 1 ~ 10000 랜덤 사용자 ID
    IF(RAND() > 0.5, 'COMPLETED', 'RESERVED')  -- 랜덤 주문 상태
FROM (SELECT 1 FROM
                   (SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION
                    SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9 UNION SELECT 10) t1,
                   (SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION
                    SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9 UNION SELECT 10) t2,
                   (SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION
                    SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9 UNION SELECT 10) t3,
                   (SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION
                    SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9 UNION SELECT 10) t4,
                   (SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION
                    SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9 UNION SELECT 10) t5,
                   (SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION
                    SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9 UNION SELECT 10) t6
     ) t
LIMIT 1000000;

-- 2. order_product 테이블에 200,000개 더미 데이터 삽입
INSERT INTO order_product (quantity, created_at, order_id, product_id, updated_at)
SELECT
    FLOOR(RAND() * 10) + 1,  -- 1 ~ 10 사이 랜덤 수량
    TIMESTAMPADD(SECOND, FLOOR(RAND() * 86400), DATE_ADD('2020-01-01', INTERVAL FLOOR(RAND() * 2192) DAY)),
    o.id,  -- orders 테이블의 id를 참조
    FLOOR(RAND() * 200000) + 1,  -- product_id는 1~200000 중 랜덤 선택
    TIMESTAMPADD(SECOND, FLOOR(RAND() * 86400), DATE_ADD('2020-01-01', INTERVAL FLOOR(RAND() * 2192) DAY))
FROM orders o
ORDER BY RAND()
LIMIT 1000000;

INSERT INTO coupon (capacity, discount_value, max_capacity, created_at, updated_at, name, discount_type)
SELECT
    FLOOR(RAND() * 1000) + 1,  -- 1 ~ 1000 랜덤 용량
    FLOOR(RAND() * 46) + 5,  -- 5 ~ 50 랜덤 할인값
    FLOOR(RAND() * 4000) + 1000,  -- 1000 ~ 5000 랜덤 최대 용량
    NOW(), NOW(),
    CONCAT('Coupon_', id),  -- 'Coupon_1', 'Coupon_2' 형태
    IF(RAND() > 0.5, 'AMOUNT', 'PERCENT')  -- 랜덤 할인 타입
FROM (
         SELECT (@rownum := @rownum + 1) AS id FROM
                                                   (SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION
                                                    SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9 UNION SELECT 10) t1,
                                                   (SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION
                                                    SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9 UNION SELECT 10) t2
                                                       CROSS JOIN (SELECT @rownum := 0) r
     ) temp
LIMIT 100;

CREATE TABLE IF NOT EXISTS `payment`
(
    `id`         bigint      NOT NULL AUTO_INCREMENT,
    `created_at` datetime(6) NOT NULL,
    `updated_at` datetime(6) NOT NULL,
    `quantity`   int         NOT NULL,
    `order_id`   bigint      NOT NULL,
    `product_id` bigint      NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
