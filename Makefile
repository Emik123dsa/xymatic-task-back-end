default: init
	
init: reset-docker build up

reset-docker:
	-docker-compose -f docker-compose.yml down --rmi=local --volumes --remove-orphans

db: 
	-docker-compose -f docker-compose.yml up -d --force-recreate db  

adminer: 
	-docker-compose -f docker-compose.yml up -d --force-recreate adminer  

build:
	docker-compose -f docker-compose.yml build

up:
	docker-compose -f docker-compose.yml up -d --force-recreate

pg_dump: 
	docker-compose exec db sh -c "pg_dump -Fc -v --host=db --username=xymatic-user --dbname=xymatic-localhost -f ./database/xymatic-localhost.db.postgres.dump"

dump:
	-docker-compose exec db sh -c "psql -U xymatic-user --set ON_ERROR_STOP=on xymatic-localhost < xymatic-localhost.db.postgres.sql"
	
ssh-db: 
	docker-compose exec db /bin/bash

ssh-nginx:
	docker-compose -f docker-compose.yml exec nginx /bin/bash