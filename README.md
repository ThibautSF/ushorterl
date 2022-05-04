# ushorterl

## Config env vars

- `USHORTERL_MONGODB_URI` (string): URI to mongodb server
  like `mongodb+srv://<user>:<password>@<domain>/`
- `USHORTERL_MONGODB_DATABASE` (string): DB name to use (created if not exist)
- `USHORTERL_SQLITE_DATABASE` (string): Path+name of sqlite db (will be created at first start, folders must exist)
- `USHORTERL_KEY_SIZE` (int): size of url short keys. Will be used at fist start to generate all the possible keys

## Run

```shell
docker volume create --name ushorterl_volume
docker run -it -p 8080:8080 \
-e USHORTERL_MONGODB_URI="<mongodb_uri>" \
-e USHORTERL_MONGODB_DATABASE="ushorterl_prod" \
-e USHORTERL_SQLITE_DATABASE="./kgs.sqlite" \
-e USHORTERL_KEY_SIZE=3 \
-v ushorterl_volume:/workspace/ \
--name ushorterl \
ushorterl:0.0.1-SNAPSHOT
```

## Build

TODO
