#!/bin/bash
sleep 2
curl -s -X POST http://localhost:8081/api/v1/banks -H "Content-Type: application/json" -d '{"name":"DemoBank Central","number":"001"}' > /dev/null
curl -s -X POST http://localhost:8081/api/v1/agencies -H "Content-Type: application/json" -d '{"bankId":1,"code":"0001"}' > /dev/null
curl -s -X POST http://localhost:8081/api/v1/onboarding -H "Content-Type: application/json" -d '{"cpf":"12345678901","agencyId":1,"accountNumber":"10001-9","email":"alice@demobank.com"}' > /dev/null
curl -s -X POST http://localhost:8081/api/v1/onboarding -H "Content-Type: application/json" -d '{"cpf":"98765432100","agencyId":1,"accountNumber":"10002-8","email":"bob@demobank.com"}' > /dev/null

curl -X POST http://localhost:8081/api/v1/onboarding/accounts/1/approve > /dev/null
curl -X POST http://localhost:8081/api/v1/onboarding/accounts/2/approve > /dev/null

count=1
while [ $count -le 30 ]; do
  curl -s -X POST http://localhost:8081/api/v1/transactions/deposit -H "Content-Type: application/json" -d '{"accountId":1,"amount":100.00,"notes":"Auto Deposit '$count'"}' > /dev/null
  curl -s -X POST http://localhost:8081/api/v1/transactions/deposit -H "Content-Type: application/json" -d '{"accountId":2,"amount":200.00,"notes":"Auto Deposit '$count'"}' > /dev/null
  curl -s -X POST http://localhost:8081/api/v1/transactions/transfer -H "Content-Type: application/json" -d '{"fromAccountId":1,"toAccountId":2,"amount":15.00,"notes":"Auto Transfer '$count'"}' > /dev/null
  curl -s -X POST http://localhost:8081/api/v1/transactions/withdraw -H "Content-Type: application/json" -d '{"accountId":2,"amount":10.00,"notes":"Auto Withdraw '$count'"}' > /dev/null
  curl -s http://localhost:8081/api/v1/accounts > /dev/null
  curl -s http://localhost:8081/api/v1/transactions > /dev/null
  count=$((count + 1))
  sleep 2
done
