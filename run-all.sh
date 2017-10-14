#!/bin/sh

set -eu

version=$1

prefix="java -Xmx80m -Dbank.port=8080 -DcreditCheck.port=8081 -Daccount.port=8082 -cp target/asynchrony-1.0-SNAPSHOT-jar-with-dependencies.jar com.iteratrlearning.examples"

eval "(${prefix}.synchronous.account.AccountService) &"
accountPid=$!

if [ $version = "sync" ]; then
    eval "(${prefix}.synchronous.bank.BankService) &"
    bankPid=$!
else
    eval "(${prefix}.asynchronous.bank.AsyncBankService) &"
    bankPid=$!
fi

eval "(${prefix}.synchronous.credit_check.CreditCheckService) &"
creditCheckPid=$!

echo "Press any key to exit all processes"
IFS= read -r REPLY

echo
echo "----------------------"
echo

jps

echo
echo "----------------------"
echo

echo "Killing 3 services"
kill -9 $accountPid
kill -9 $bankPid
kill -9 $creditCheckPid

echo
echo "----------------------"
echo

jps
