# Start database

```bash
docker compose up -d
```

# Stop database
```bash
docker compose stop
```

# Creating database dumps
```bash
docker exec mysqldb_mysql-db_1 sh -c 'exec mysqldump --all-databases -uroot -p"Admin@123"' > /home/robert/work/robert/java-coaching-lab/mysql-db/db-dumps/all-databases.sql
```

# Restoring data from dump files
```bash
docker exec -i mysqldb_mysql-db_1 sh -c 'exec mysql -uroot -p"Admin@123"' < /home/robert/work/robert/java-coaching-lab/mysql-db/db-dumps/all-databases.sql
```
