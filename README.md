System requirements:
<li>Docker installation</li>
<li>Maven installation</li>
<li>Make GNU installation (optional)</li>

To run and deploy the application:
1. `
mvn clean install
`
2. `
docker-compose up
`
3. Import the `Device.postman_collection.json` and start testing the endpoint on Postman.

Note: If Make is installed run `make run`

To Stop the application run:
1. `docker-compose down`

Note: If Make is installed run `make run`
