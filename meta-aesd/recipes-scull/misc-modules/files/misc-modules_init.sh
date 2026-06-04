#!/bin/sh

case "$1" in
  start)
    printf "Loading misc modules...\n"
    insmod /lib/modules/*/extra/hello.ko
    insmod /lib/modules/*/extra/hellop.ko
    insmod /lib/modules/*/extra/seq.ko
    insmod /lib/modules/*/extra/jiq.ko
    insmod /lib/modules/*/extra/sleepy.ko
    insmod /lib/modules/*/extra/complete.ko
    insmod /lib/modules/*/extra/silly.ko
    insmod /lib/modules/*/extra/faulty.ko
    insmod /lib/modules/*/extra/kdatasize.ko
    insmod /lib/modules/*/extra/kdataalign.ko
    insmod /lib/modules/*/extra/jit.ko
    ;;
  stop)
    printf "Unloading misc modules...\n"
    rmmod hello
    rmmod hellop
    rmmod seq
    rmmod jiq
    rmmod sleepy
    rmmod complete
    rmmod silly
    rmmod faulty
    rmmod kdatasize
    rmmod kdataalign
    rmmod jit
    ;;
  *)
    printf "Usage: $0 {start|stop}\n"
    exit 1
esac
exit 0

