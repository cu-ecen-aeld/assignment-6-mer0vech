#!/bin/sh

MODULE="scull"
DEVICE="scull"
MODE="664"

KERNEL_VERSION=$(uname -r)
MODULE_PATH="/lib/modules/$KERNEL_VERSION/extra/${MODULE}.ko"


case "$1" in
  start)
    if grep -q '^staff:' /etc/group; then
      GROUP="staff"
    else
      GROUP="wheel"
    fi

    printf "Loading scull module...\n"
    
    if /sbin/modprobe "$MODULE"; then
      sleep 1
    else
      if [ -f "$MODULE_PATH" ]; then
        /sbin/insmod "$MODULE_PATH" || exit 1
        sleep 1
      else
        printf "Error: %s not found\n" "$MODULE"
        exit 1
      fi
    fi

    MAJOR=$(awk "\$2==\"$MODULE\" {print \$1}" /proc/devices)
    if [ -n "$MAJOR" ]; then
      rm -f /dev/${DEVICE}[0-3]
      mknod /dev/${DEVICE}0 c $MAJOR 0
      mknod /dev/${DEVICE}1 c $MAJOR 1
      mknod /dev/${DEVICE}2 c $MAJOR 2
      mknod /dev/${DEVICE}3 c $MAJOR 3
      ln -sf ${DEVICE}0 /dev/${DEVICE}
      chgrp $GROUP /dev/${DEVICE}[0-3] 
      chmod $MODE  /dev/${DEVICE}[0-3]

      rm -f /dev/${DEVICE}pipe[0-3]
      mknod /dev/${DEVICE}pipe0 c $MAJOR 4
      mknod /dev/${DEVICE}pipe1 c $MAJOR 5
      mknod /dev/${DEVICE}pipe2 c $MAJOR 6
      mknod /dev/${DEVICE}pipe3 c $MAJOR 7
      ln -sf ${DEVICE}pipe0 /dev/${DEVICE}pipe
      chgrp $GROUP /dev/${DEVICE}pipe[0-3] 
      chmod $MODE  /dev/${DEVICE}pipe[0-3]

      rm -f /dev/${DEVICE}single
      mknod /dev/${DEVICE}single  c $MAJOR 8
      chgrp $GROUP /dev/${DEVICE}single
      chmod $MODE  /dev/${DEVICE}single

      rm -f /dev/${DEVICE}uid
      mknod /dev/${DEVICE}uid   c $MAJOR 9
      chgrp $GROUP /dev/${DEVICE}uid
      chmod $MODE  /dev/${DEVICE}uid

      rm -f /dev/${DEVICE}wuid
      mknod /dev/${DEVICE}wuid  c $MAJOR 10
      chgrp $GROUP /dev/${DEVICE}wuid
      chmod $MODE  /dev/${DEVICE}wuid

      rm -f /dev/${DEVICE}priv
      mknod /dev/${DEVICE}priv  c $MAJOR 11
      chgrp $GROUP /dev/${DEVICE}priv
      chmod $MODE  /dev/${DEVICE}priv
    else
      printf "Error: Module %s not found in /proc/devices.\n" "$MODULE"
      exit 1
    fi
    ;;
    
  stop)
    printf "Unloading scull module...\n"
    rmmod $MODULE || exit 1
    rm -f /dev/${DEVICE} /dev/${DEVICE}[0-3] 
    rm -f /dev/${DEVICE}priv
    rm -f /dev/${DEVICE}pipe /dev/${DEVICE}pipe[0-3]
    rm -f /dev/${DEVICE}single
    rm -f /dev/${DEVICE}uid
    rm -f /dev/${DEVICE}wuid
    ;;
    
  *)
    printf "Usage: $0 {start|stop}\n"
    exit 1
esac

exit 0

