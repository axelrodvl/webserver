#!/bin/sh
xargs -I % -P 1000 curl "http://localhost:8087" < <(printf '%s\n' {1..10000})