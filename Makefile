.PHONY: run stop

run:
	mvn clean install
	docker-compose up

stop:
	docker-compose down