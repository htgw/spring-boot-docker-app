# Automovive部門 J2C タスクサンプルアプリ

## 概要

Spring Boot (2.3.4) で実装したTodo アプリのバックエンド想定した簡素なREST API です。

コンテナとして実行することを想定しているため、Dockerfileも配布しています。

---
## API仕様 (編集中)

|  No. | エンドポイント | HTTPメソッド | 機能概要 |
| ---: | :------------- | :----------- | :------- |
|    1 | /todos         | GET          | 全件取得 |
|    2 | /todos/${id}   | GET          | 1件取得  |
|    3 | /todos         | POST         | 新規作成 |
|    4 | /todos/${id}   | PUT          | 1件完了  |
|    5 | /todos/${id}   | DELTE        | 1件削除  |
---

## クイックスタート

+ リポジトリのクローン

```sh
$ cd <workdir>
$ git clone https://gitlab.com/hirotow/spring-boot-docker-app.git
$ cd spring-boot-docker-app
```

+ アプリケーションのビルド

```sh
$ cd <workdir>/spring-boot-docker-app
# Maven Wrapper から jar を作成(mvn package と同等)
$ ./mvnw package
# 端末の JVM 上で実行する場合は以下を実行
$ java -jar target/spring-boot-docker-app-0.0.1-SNAPSHOT.jar
```

+ コンテナイメージの作成

```sh
$ cd <workdir>/spring-boot-docker-app
# image_name は任意で指定してください。
$ docker build -t <image_name> .
```

+ コンテナイメージの実行

```sh
# rm オプションを付与することで、コンテナの停止(ctrl + c)後、コンテナが自動で削除されます。
# p オプションでは、コンテナ内部のポートと、ホストマシンのポートを指定しています (host:containerの順)
$ docker container run --rm -p 8080:8080 <image_name>
```

+ APIの動作確認（初期データは起動時にH2 Databaseに投入済です。）

```sh
# 全件取得
$ curl -X GET http://localhost:8080/todos
# 1件取得
$ curl -X GET http://localhost:8080/todos/1
# 新規作成
$ curl -X POST http://localhost:8080/todos -H "Content-Type:application/json" -d '{"title": "hoge"}'
# 1件完了(更新)
$ curl -X PUT http://localhost:8080/todos/1
# 1件削除
$ curl -X DELETE http://localhost:8080/todos/1
```