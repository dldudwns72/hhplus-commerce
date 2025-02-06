# Redis 란?

- Remote Dictionary Server 의 약자로써 In-Memory 기반 오픈소스 NoSQL

## 어떤 기능에서 사용될 수 있는가

- In-Memory 기반의 빠른 데이터 처리
- 분산 시스템에서 활용 (세션 저장, 메세지 큐)

## 무조건적인 Redis 활용은 좋은가?

- 아니라고 생각된다, 메모리는 디스크보다 비싸고 용량이 보다 제한적이기 때문에 사용하는데 주의가 필요하다
- 변경이 잦은 데이터를 저장하기 위해서는 메모리 사용량이 급격히 증가하므로 단순 쓰기, 조회 기능에 활용하기에 유용하다.
- 장애가 발생한 데이터에 대한 손실 위험성 존재

## Redis 설정

1. 도커 이미지 다운로드

```dockerfile
docker pull redis
```

2. Redis Container execute

```dockerfile
docker run --name redis -p 6379:6379 -d redis
```

- --name redis: 컨테이너 이름 redis 로 설정
- -p 6379:6379: 호스트와 컨테이너 간 포트 연결
- -d: background 에서 실행

3. Redis Container 접속

```dockerfile
docker exec -it redis redis-cli
```

## Redis 자료구조

- Redis 에서 데이터를 저장할 떄 어떤 형식으로 저장할지를 결정하는 데이터 구조
- 기본적으로 Key-Value 기반 데이터 저장이 이루어짐

### String

- 가장 기본적인 자료구조, 일반 문자열
- 단순 증감 혹은 문자열로 표현 가능한 모든 자료를 저장하는 목적으로 사용

#### 명령어

``` redis
# 수정 및 조회
SET user:1 "Lee" / 응답: OK / SET: Key-Value 설정
    GET user:1 / 응답: "Lee" / GET: Key 기반 Value 조회
SETNX user:1 "lee2" / 응답: (integer) 0 / SETNX: Set if Not eXists의 약자로 값이 없을 때만 키를 설정, 이미 user:1이 존재하므로 아무런 변화가 이뤄지지 않는다.
MGET user:1 user:2 / 응답: 1) "Lee_update" 2) "kim" 3) (nil) / MGET: 조회하고 싶은 여러키 조회, 매칭되는 key 값이 없다면 nil 조회

# 증감
INCR visit_count / 응답: (integer) 1 / INCR Key 기반 증가 +1  
    GET visit_count / 응답: "1"  
DECR visit_count / 응답: (integer) 0 / DECR: Key 기반 감소 -1
    GET visit_count / 응답: "0"
```

### Sets

- 저장된 원소의 Unique 함을 보장하는 자료구조
- 정렬을 보장하지않으며, Set 자료구조를 위한 연산을 지원 (교집합 / 합집합 / 차집합 등)
- 빠른 조회 및 삽입,삭제가 이루어짐

#### 명령어

``` redis
# 수정 및 조회
SADD myset "apple" "banana" / 응답: (integer) 2, 2개 요소 생성 / key-value(collectons) 설정
    SMEMBERS myset / 응답: 1) "apple" 2) "banana" / SMEMBERS: key 기반 저장된 모든값 조회
SADD myset "suback" "apple" / 응답: (integer) 1 /  기존 데이터에 데이터 추가 삽입, 이미 존재하는 값은 추가되지 않음
    SMEMBERS myset / 응답: 1) "apple" 2) "banana" 3) "suback"
SREM myset "suback" / 응답: (integer) 1 / Set에서 데이터 삭제
    SMEMBERS myset / 응답: 1) "apple" 2) "banana"
    
# 요소 판별
SISMEMBER myset "apple" / 응답: 1) (integer) 1 / SISMEMBER: 존재하면 1 아니면 0
SCARD myset / 응답: (integer) 2 / SCARD: 저장된 요소 개수 반환
SPOP myset / 응답: "banana" / SPOP: 임의의 값 하나 랜덤하게 삭제

SMOVE myset myset2 apple / 응답: (integer) 1 / SMOVE: set 간 요소 이동 
    SMEMBERS myset2 / 1) "dog" 2) "cat" 3) "apple" 


# 집합 계열
SDIFF myset myset2 / 응답: "banana" / SDIFF: myset에만 존재하고 myset2에는 없는값 반환 (차집합)
SINTER myset myset2 / 응답: "apple" / SINTER: myset,myset2 두개 다 존재하는 요소 (교집합)
SUNION myset myset2 / 응답: 1) "apple" 2) "banana" 3) "dog" 4) "cat" / SUNION: 모두 존재하는 값 반환 (합집합)
```

### Sorted Sets (ZSet)

- set + score 가중치 필드가 추가된 자료구조
- score 에 따라 자동으로 정렬된 상태 유지 (기본 오름차순이지만 변경 가능)
- score 값이 같을 경우, 사전 순으로 정렬
- 실시간 랭킹, 선호도 관련 기능 중 정렬기능 기반 데이터 조회 시 사용될 수 있음

#### 명령어

``` redis
ZADD sortedsets 100 lee 90 kim 80 park / 응답 (integer) 3 / ZADD KEY score element1 score element2    
ZRANGE sortedsets 0 -1 / 응답  1) "park" 2) "kim" 3) "lee" / ZRANGE key 범위, score 높은 순으로 전체 반환
ZREM sortedsets kim / 응답: (integer) 1 / ZREM ket element, 특정 요소 제거
    ZRANGE sortedsets 0 -1 / 응답: 1) "park" 2) "lee"
ZSCORE sortedsets lee / 응답: "100" / ZSCORE: 특정 값의 점수 조회
ZINCRBY sortedsets 20 lee / 응답: "120" /  ZINCRBY: 특정 값 점수 증가 

ZCARD sortedsets / 응답: (integer) 5 / ZCARD: 요소 개수 조회 
ZRANK sortedsets / 응답: (integer) 3 / ZRANK: 해당 요소의 순위 조회 (점수 낮은 순)
ZREVRANK sortedsets / 응답: (integer) 1 / ZRANK: 해당 요소의 순위 조회 (점수 높은 순)
```

### Hash

- 하나의 Key 에 여러개 Value 를 저장할 수 있는 자료구조
- 복잡한 데이터를 저장할 떄 사용

#### 명령어

``` redis
HSET user:1000 name "Alice" age 30 city "Seoul"
HGET user:1000 name / 응답: "Alice" / HGET: key 필드의 value 반환 
HGETALL user:1000 / 응답: 1) "name" 2) "Alice" 3) "age" 4) "30" 5) "city" 6) "Seoul" / 모든 필드와 값을 반환
HKEYS user:1000 / 응답: 1) "name" 2) "city" / HKEYS: Hash의 모든 필드 반환 
HVALS user:1000 / 응답: 1) "Alice" 2) "Seoul" / Hash의 모든 값을 반환하는 명령어
HEXISTS user:1000 age

HDEL user:1000 age / 응답: (integer) 1 / HDEL: 특정 필드 삭제
```

## 캐싱 최적화

### TTL (Time to Live)

- 캐시된 데이터가 만료되도록 설정, 주기적으로 캐시 데이터를 자동으로 제거

``` redis
SET key "value" EX 3600   # 1시간 후 만료
SET key "value" PX 60000  # 1분 후 만료 (밀리초 단위)
```

### LRU(Least Recently Used)

- 최근 사용 알고리즘을 통해 메모리에서 가장 오래된 데이터를 자동으로 제거

```redis
CONFIG SET maxmemory 100mb
CONFIG SET maxmemory-policy allkeys-lru
```

### 캐시 프리징 (Cache Preloading)

- 캐시 프리징은 서버 재시작 시 필요할 수 있는 데이터들을 미리 Redis에 미리 로딩하는 방식으로, 초기 지연을 줄일 수 있음.

### Redis Cluster 활용

- 데이터가 많아지면 Redis 클러스터를 이용하여 데이터 분산 저장 가능, 클러스터를 사용하면 데이터의 로드 밸런싱을 자동으로 처리하고, 수평 확장이 가능해져 대규모 데이터 처리가 용이
- **샤딩(sharding)** 을 통해 여러 Redis 인스턴스에 데이터를 분산 저장하고, 더 많은 요청을 처리할 수 있도록 함

#### 샤딩(Sharding)이란?

- 데이터베이스나 캐시 시스템에서 데이터를 여러 서버나 데이터베이스 인스턴스로 분할하여 저장하는 기술
    - 성능 향상: 여러 Redis 인스턴스에 데이터를 분산시켜 각 서버에 대한 부하를 줄이고, 병렬 처리를 통해 성능 향상
    - 수평 확장: 데이터를 여러 서버에 분산시킴으로써 용량을 확장하고, 필요할 때 더 많은 서버를 추가하여 시스템 확장
    - 메모리 용량 확장: 하나의 Redis 서버에 메모리 용량이 한정되어 있기 때문에, 샤딩을 통해 여러 서버에서 메모리 용량 확장
- 데이터 분배와 키 설계를 고려해야함

### Redis Pipelines

- 여러 명령을 하나의 요청으로 묶어 한 번에 Redis 서버에 보내는 방식인 Pipelining 을 활용하면, 네트워크 지연을 줄이고 여러 캐시 작업을 더 빠르게 처리
- 이를 통해 동시성을 개선하고, 성능을 최적화할 수 있음.

### Lazy Loading

- 캐시가 만료된 데이터를 필요할 때만 로딩하는 방식이야. 즉, 데이터가 요청되었을 때 캐시에서 미리 로딩된 값이 없으면 데이터베이스에서 가져오고, 가져온 값을 다시 캐시하는 방식.
- 장점: 초기 메모리 사용을 최소화하고, 캐시 적중률을 높일 수 있음.
- 단점: 캐시 미스가 발생할 때 데이터베이스에서 값을 읽는 비용이 증가할 수 있음.


## E-Commerce 에서 Redis Cache 를 사용할 수 있는 사례
1. 선착순 쿠폰 발급
   - Set 기반
   - 발급 수 제한, 중복 발급 방지
   - 사용자 ID 
2. 인기 상품 조회
   - 주문 시 해당 상품에 대한 incr 진행? 