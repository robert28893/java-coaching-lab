# Start database

```bash
docker compose up -d
```
Lệnh này sẽ start mysql server sử dụng docker

Connect tới db sử dụng port kết nối mặc định là `3306`, `username` root và `password` là giá trị của biến `MYSQL_ROOT_PASSWORD` trong file `docker-compose.yml`

# Stop database
```bash
docker compose stop
```

# Creating database dumps
```bash
docker exec mysqldb_mysql-db_1 sh -c 'exec mysqldump --all-databases -uroot -p"Admin@123"' > db-dumps/all-databases.sql
```

# Restoring data from dump files
```bash
docker exec -i mysqldb_mysql-db_1 sh -c 'exec mysql -uroot -p"Admin@123"' < db-dumps/all-databases.sql
```

# Cài đặt MySQL Workbench trên Ubuntu

Xem hướng dẫn tại link sau: [Cài mysql workbench](https://robert28893.github.io/blog/databases/mysql/install-mysql-workbench/)