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
	docker-compose exec db sh -c "pg_dump -Fc -v --host=db --username=xymatic-user --dbname=xymatic-localhost -f ./database/xymatic-localhost.db.postgres.dump.sql"

dump:
	-docker-compose exec db sh -c "pg_restore -v --no-owner --host=db --port=5432 --username=xymatic-user --dbname=xymatic-localhost database/xymatic-localhost.db.postgres.dump.sql"
	
ssh-db:
	docker-compose exec db /bin/bash

ssh-nginx:
	docker-compose -f docker-compose.yml exec nginx /bin/bash