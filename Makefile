default: init
	
init: reset-docker build up

reset-docker:
	-docker-compose -f docker-compose.yml down --rmi=local --volumes --remove-orphans

build:
	docker-compose -f docker-compose.yml build

up:
	docker-compose -f docker-compose.yml up -d --force-recreate

