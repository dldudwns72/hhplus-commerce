# Index 란?

- Database 조회 성능을 향상 시키기 위해 특정 컬럼에 대해 미리 정렬 해놓는 구조
- Index 가 없다면 조건에 대해 조회를 진행하게 되면 전체를 조회하는 Full Scan 이 일어나게 되며 속도가 매우 느려지게 된다.

## 어떻게 Index 가 동작하는가?

- Index 가 없다면 1~100까지 조회를 하였을 떄 처음부터 끝까지 조회를 진행하게 되는데, 이떄 조회할 조건을 정렬 해놓은 상태에서 절반씩 조회 조건을 줄여가면 조회 횟수를 줄일 수 있다.
- 이떄 정렬해놓은 컬럼 사본을 index 이라 하고 B Tree 구조로 데이터를 정렬 해놓고 데이터를 찾아간다.
- 주로 B Tree, B+ Tree 자료구조를 이용하는데 B+ Tree 자료구조는 범위 검색에 더 용이하여 사용된다고 함

## Index 종류

### 단일 Index (Single Index)

- 1개의 컬럼만을 사용하여 구성된 Index
- 데이터의 중복도가 낮은 ( 카디널리티가 높은 ) 컬럼을 선택하여 성능을 높힐 수 있다.

### 복합 Index (Composite Index)

- 2개 이상의 컬럼으로 구성된 Index, 구성된 컬럼의 순서대로 정렬이 이뤄진다
    - 컬럼 순서에 따라 성능 차이가 많이나며, 선두 컬럼을 제일 활용하되 앞에 있는 컬럼들이 중복도가 낮은 경우 뒤에 중복도가 높은 컬럼이 들어왔을 경우 의미 없는 인덱스가 될 수 있다.
      ( 불필요한 컬럼 추가 X)

### Unique Index

- 복합키의 한 종류이나 Unique Index 로 설정된 컬럼들은 중복이 될 수 없다.

### Index 사용 시 유의 사항

1. Index 컬럼의 값과 타입을 그대로 사용할것

``` sql
# X
where price > 10000 / 100 # 연산과 같은 작업이 있다면 우측으로 옮김
# O
where price * 100 > 10000  
```

2. Index 가 있는 컬럼에서 Like 검색 시 앞 부분은 일차하게 한 후 사용

``` sql
LIKE '%abc%' → 인덱스 사용 불가능 (앞에 %가 있으면 인덱스가 비효율적)
LIKE 'abc%' → 인덱스 사용 가능 (앞부분이 일치해야 인덱스 활용 가능)
```

## Index 설정 유의 사항

- Index 는 컬럼을 복사해서 정렬해 놓은것으로, 메모리 용량을 사용한다. (비용 발생) -> 의미 없는 Index 사용 금지
- 데이터가 변경될 떄 마다 Index 도 같이 정렬되어야 하므로 추가적인 연산이 발생하여 생성, 수정,삭제 작업에 성능 저하가 있을 수 있다.
- 데이터 수정 삭제 작업 시 성능 하락을 야기할 수 있으나 크게 신경쓰지 않아도됨

#### Covering Index

- Index 사용 후 조회 시 원본 데이터를 가져오는데, 행의 갯수, 합 등을 확인할 떄는 원본 ROW에 접근할 필요가 없이 그대로 반환 해주는 것

## Mysql 실행 분석 명령어 explain

### explain 컬럼별 의미

1. id

- 쿼리 실행 단계를 식별하는 ID 번호
- 여러 개의 조인된 테이블이 있을 때, 각각의 SELECT 단계마다 다른 ID가 부여되며 값이 클수록 먼저 실행

2. select_type

- 쿼리의 유형
    - SIMPLE: 단순 SELECT (서브쿼리나 UNION이 없음)
    - PRIMARY: 최상위 SELECT (서브쿼리가 있을 때 최상위)
    - SUBQUERY: 서브쿼리 내부의 SELECT
    - DERIVED: 파생 테이블 (서브쿼리 결과를 임시 테이블로 변환)
    - UNION: UNION 연산의 일부
    - DEPENDENT: SUBQUERY 외부 쿼리에 의존하는 서브쿼리
    - UNCACHEABLE: SUBQUERY 캐싱할 수 없는 서브쿼리

3. table

- 분석중인 테이블 명

4. partitions

- 5.7v 이후부터 확인 가능하며, 적용된 파티셔닝 정보 조회

5. type

- 테이블에서 데이터를 검색하는 방식
- 값이 좋을수록 성능이 우수함
- 유형
    - system: 시스템 테이블 (한 행만 존재)    / 매우 빠름
    - const:    PK 또는 UNIQUE 인덱스를 사용하여 특정 값 조회 / 매우 빠름
    - eq_ref: 조인에서 PK 또는 UNIQUE KEY 를 사용한 정확한 매칭 / 빠름
    - ref: 일반적인 인덱스 조회 (PK 아님, WHERE 조건 사용)    / 보통
    - range:    WHERE 에서 범위 조건 사용 (BETWEEN, >, < 등)    / 보통
    - index:    테이블의 전체 인덱스 스캔 (INDEX FULL SCAN)    / 느림
    - ALL:    풀 테이블 스캔 (INDEX 없이 테이블 전체 검색)    / 매우 느림
- 성능을 높이려면 ALL 이나 index 대신 ref, eq_ref, const 를 목표로 튜닝 해야 한다.

6. possible_keys

- 쿼리에 사용될 가능성이 있는 인덱스 목록.
- NULL 이면 인덱스를 사용하지 않는다는 의미 → 인덱스 추가 고려 필요


7. key

- 실제 사용된 인덱스.
- NULL 이면 인덱스가 사용되지 않음.

8. key_len

- 사용된 인덱스의 바이트 길이
- 클수록 메모리 사용량이 증가하며, 짧을수록 효율적이다.

9. ref

- 조인 시 어떤 컬럼이 비교되는지 표시.

10. rows

- MySQL 이 테이블에서 조회해야 하는 예상 행(row) 수
- 숫자가 크면 성능 저하 가능성 있음 → 인덱스 튜닝 필요

11. filtered

- 필터링 후 남은 행 비율 (%)
- 100이면 모든 행이 남고, 10이면 90%가 제거됨.

12. extra

- 추가적인 실행 정보 제공.
- 관련 내용
    - Using where: WHERE 조건 사용 / 괜찮음
    - Using index: 인덱스만으로 데이터를 조회함 / 효율적
    - Using temporary: 임시 테이블 사용 (ORDER BY, GROUP BY 등) / 최적화 필요
    - Using filesort: 인덱스 없이 정렬 발생 /️ 성능 저하 가능
    - Using join buffer: 조인 버퍼 사용 (인덱스 부족 시 발생) / 인덱스 최적화 필요

### 인기 상품 조회에서 Index 를 이용한 조회 최적화

#### 실행 쿼리 [인기상품조회]

```sql
SELECT p.id             AS product_id,
       p.name           AS product_name,
       pi.inventory     AS product_inventory,
       SUM(op.quantity) AS order_count
FROM orders o
         INNER JOIN
     order_product op ON o.id = op.order_id
         INNER JOIN
     product p ON op.product_id = p.id
         INNER JOIN
     product_inventory pi ON p.id = pi.product_id
WHERE o.created_at BETWEEN '2021-02-11 00:00:00' AND '2025-02-12 00:00:00'
  AND o.status = 'COMPLETED'
GROUP BY p.id, pi.inventory
ORDER BY order_count DESC
LIMIT 100; 
```

#### Index 미설정 시 조회 결과

- 10만건 데이터 기준 약 3s 조회 시간 소요

| Type   | Table | Key     | ref           | rows   | filtered | Extra                           |
|--------|-------|---------|---------------|--------|----------|---------------------------------|
| ALL    | op    | null    | null          | 996072 | 100      | Using temporary; Using filesort |
| eq_ref | p     | PRIMARY | op.product_id | 1      | 100      | null                            |
| eq_ref | pi    | null    | op.product_id | 1      | 100      | null                            |
| eq_ref | o     | PRIMARY | op.order_id   | 1      | 5.56     | Using where                     |

#### Index 추가 설정

```sql
CREATE INDEX idx_status_created_at ON orders (status, created_at); # 중복도 높은 status 먼저 설정 후, created_at 설정 
CREATE INDEX idx_order_id ON order_product (order_id); # 외래키 관련 INDEX 설정
CREATE INDEX idx_product_id ON order_product (product_id); # 외래키 관련 INDEX 설정
```

#### Index 설정 후 조회 결과

- 10만건 데이터 기준 약 1s 조회 시간 소요 ( 3s -> 1s )

| Type   | Table | Key            | ref         | rows | filtered | Extra                           |
|--------|-------|----------------|-------------|------|----------|---------------------------------|
| index  | p     | PRIMARY        | null        | 9961 | 100      | Using temporary; Using filesort |
| eq_ref | pi    | foreign key    | p.id        | 1    | 100      | null                            |
| eq_ref | op    | idx_product_id | p.id        | 5    | 100      | null                            |
| eq_ref | o     | PRIMARY        | op.order_id | 1    | 50       | Using where                     |

