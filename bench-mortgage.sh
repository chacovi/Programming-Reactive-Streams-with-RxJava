#!/bin/sh

set -eu

ab -c 10 -n $1 -k http://localhost:8080/mortgage?customerId=bob

