# ushorterl

## Config env vars

- `USHORTERL_MONGODB_URI` (string): URI to mongodb server
  like `mongodb+srv://<user>:<password>@<domain>/`
- `USHORTERL_MONGODB_DATABASE` (string): DB name to use (created if not exist)
- `USHORTERL_SQLITE_DATABASE` (string): Path+name of sqlite db (will be created at first start, folders must exist)
- `USHORTERL_KEY_SIZE` (int): size of url short keys. Will be used at fist start to generate all the possible keys

## Run

*Note: A build must be done at first, docker image isn't online*

```shell
docker volume create --name ushorterl_volume
docker run -it -p 8080:8080 \
-e USHORTERL_MONGODB_URI="<mongodb_uri>" \
-e USHORTERL_MONGODB_DATABASE="ushorterl_prod" \
-e USHORTERL_SQLITE_DATABASE="./kgs.sqlite" \
-e USHORTERL_KEY_SIZE=3 \
-v ushorterl_volume:/workspace/ \
--name ushorterl \
ushorterl:0.0.1
```

## From source

### Run

```shell
mvn javadoc:javadoc spring-boot:run
```

*Note: `javadoc:javadoc` goal is optional, just here to package into API server for the only purpose of easy access for
exercise presentation.*

### Build

```shell
mvn javadoc:javadoc spring-boot:build-image
```

*Note: `javadoc:javadoc` goal is optional, just here to package into API server for the only purpose of easy access for
exercise presentation.*

## Info links

- Accessing exercise description (in
  French) `<serverurl>/exercise.html`: [latest online](http://34.155.28.95:8080/exercise.html)
- Accessing APIDoc `<serverurl>/swagger-ui/index.html`: [latest online](http://34.155.28.95:8080/swagger-ui/index.html)
- Accessing JavaDoc `<serverurl>/apidoc/index.html` (if built/run with `javadoc:javadoc` maven
  goal): [latest online](http://34.155.28.95:8080/apidoc/index.html)

## About project and code

### Java

Dev with JDK 17 LTS ([Eclipse Temurin](https://adoptium.net/)), code targets Java 8.

### IDE

Dev with IntelliJ IDEA 2022.1 (Community Edition), as IntelliJ IDEA was a requirement. First time I really use it for
Java development, but not first time with a JetBrain IDE (PyCharm, PhpStorm). I also briefly use Android Studio.
It was a good experience, yet I still have learning to do for a more optimal use.

### Framework

API is based on Spring Boot 2.6 with Maven as build automation tool.
Building a Spring Boot application from scratch is relatively new for me, so I took time to read documentation when
something wasn't clear for me.
Mainly [official documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/index.html)
and [Baeldung](https://www.baeldung.com/rest-with-spring-series).

More experienced with Gradle, yet Maven wasn't new for me.

### Code architecture

I'm always focusing on 2 objective for Java project architecture :

- follow the current framework policies
- follow logical separation

Thus code organisation follows general spring boot structure with dedicated packages for controllers, models,
repositories...

I firstly tried to fully modularize the [Key Generation Service](./src/main/java/thibaut/sf/ushorterl/kgs) but since I
encountered some issues and drawbacks, I preferred to not lose too much focus time on that, so I scraped everything and
put code under one main package. But with more time, I would definitely improve that.

- thibaut.sf.ushorterl (app package)
    - controllers
    - exceptions
    - kgs (Key Generation Service)
    - models
    - repositories
    - services
    - sqlite (for hibernate dialect)

### Persistence

For the main database, to store short urls, MongoDB was chosen. Since theoretically an url shortener service can hold a
large number of url data with no relation between them, NoSQL was the pertinent choice: read/write performance and
scalability.

For the Key Generation Service, I completely could have stayed with MongoDB. In fact, I should have... But for the only
purpose of variation on this exercise, I chose to use an SQL based database. My choice was for SQLite mainly for its
simplicity and since I didn't need to store complex data (one table with 2 columns).

### About KGS

The main part to implement an url shortener is to link long url to a short key value, in general 5-6 characters long.

But the implementation question is how to generate a key for a long url ?

I studied several possibilities :

- random string (naive)
- hash & encode
- database of already generated keys (KGS)

Technically due to the scale of the project, the naive approach was possibly adapted but really uninteresting.

Hash & encode was interesting, basically hash the long url, then encode to a short string format, maybe add some noise
to reduce collision. But with a workaround problem, if 2 users wants to shorten same URL, the short url obtained might
be the same.

So last solution is to pre-generate all possible keys based on wanted alphabet and key length, and then, when a user
wants to shorten a new url, the API ask KGS database for an available key and use that key as short url.
Which means that the number of key generated would be $b^n$ with:

- b the alphabet length
- n the key length

For the project I used the base 62 alphabet (a-zA-Z0-9) with a length of 3.
Which would mean $62^3=238328$ possible keys (note that with a length o 6, we reach billions of possibilities
$62^6=56800235584$).

With base 62 and a length of 3, the SQLite db size generated was 6MB.
