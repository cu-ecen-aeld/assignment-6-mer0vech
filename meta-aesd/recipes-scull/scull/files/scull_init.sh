#!/bin/sh

case "$1" in
  start)
    printf "Loading scull module...\n"
    insmod /lib/modules/*/extra/scull.ko
    ;;
  stop)
    printf "Unloading scull module...\n"
    rmmod scull
    ;;
  *)
    printf "Usage: $0 {start|stop}\n"
    exit 1
esac
exit 0

