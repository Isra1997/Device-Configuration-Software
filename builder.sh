#!/bin/bash
db_image_name="MYSQL-${APP_NAME}"

# run the database docker container
docker pull mysql/mysql-server:latest
docker run --name="${db_image_name}" -d mysql/mysql-server:latest

# wait for the generated container password in the logs
while (( ! $(docker logs "${db_image_name}" | grep PASSWORD)  ));
do
        echo "waiting for database container to start..."
        sleep 15
done

# extract the generated password
generated_password=docker logs "${db_image_name}" | grep -oE '[a-zA-Z0-9]*[0-9][a-zA-Z0-9]*'
echo "${generated_password}"
docker exec -it "${db_image_name}" mysql -uroot -p

cd



