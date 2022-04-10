#!/bin/bash

cd /home/ubuntu/backup

DATE=$(date +"%Y-%m-%d")

OLD=$(date -d "30 days ago" +"%Y-%m-%d")

AUTH="-u root -pHelena1993_@#$"

DB="scq"

mkdir -p "$DB"

TABLES=$(mysql $AUTH -NB -e "SHOW TABLES"  $DB)

for TABLE in $TABLES; do
    mysqldump $AUTH $DB $TABLE | gzip  > $DB/$DATE-$TABLE.sql.gz

    aws s3 cp $DB/$DATE-$TABLE.sql.gz s3://scq-backup/$DB/$DATE-$TABLE.sql.gz

    aws s3 rm s3://scq-backup/$DB/$OLD-$TABLE.sql.gz

    rm -f "$DB/$OLD-$TABLE.sql.gz"
done