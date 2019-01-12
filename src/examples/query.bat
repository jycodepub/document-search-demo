set SRV_URL=http://localhost:8080/search
curl -X POST -H "Content-Type: application/json" -d @%1 %SRV_URL% | jq .
