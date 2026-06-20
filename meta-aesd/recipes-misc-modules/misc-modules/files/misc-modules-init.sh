#!/bin/sh

KERNEL_VERSION=$(uname -r)
MODULES_DIR="/lib/modules/$KERNEL_VERSION/extra"
MODE="644"

make_node() {
  rm -f "/dev/$1"
  mknod "/dev/$1" c "$2" "$3"
  chgrp "$GROUP" "/dev/$1"
  chmod "$MODE" "/dev/$1"
}

load_and_setup_module() {
  MODULE="$1"
  DEVICE="$1"
  MODULE_PATH="${MODULES_DIR}/${MODULE}.ko"

  printf "Loading %s module...\n" "$MODULE"

  if [ -f "$MODULE_PATH" ]; then
    /sbin/insmod "$MODULE_PATH" || return 1
    sleep 1
  else
    printf "Error: %s not found at %s\n" "${MODULE}.ko" "$MODULE_PATH"
    return 1
  fi

  MAJOR=$(awk "\$2==\"$MODULE\" {print \$1}" /proc/devices)
    
  if [ -n "$MAJOR" ]; then
    make_node "$DEVICE" "$MAJOR" 0
  else
    printf "Warning: Module %s did not register a major number device. Continuing...\n" "$MODULE"
  fi
  
  return 0
}

unload_and_unset_module() {
  MODULE="$1"
  DEVICE="$1"

  rmmod $MODULE || return 1
  rm -f /dev/${DEVICE}
}

case "$1" in
  start)
    if grep -q '^staff:' /etc/group; then
      GROUP="staff"
    else
      GROUP="wheel"
    fi

    printf "Loading misc modules...\n"

    load_and_setup_module "faulty"
    load_and_setup_module "hello"

    #load_and_setup_module "hellop"
    
    #load_and_setup_module "seq"
    #load_and_setup_module "kdatasize"
    #load_and_setup_module "kdataalign"
    
    #load_and_setup_module "jiq"
    #load_and_setup_module "jit"
    #load_and_setup_module "sleepy"
    #load_and_setup_module "complete"
    #load_and_setup_module "silly"
    
    
    
    ;;
    
  stop)
    printf "Unloading misc modules...\n"

    unload_and_unset_module "hello"
    unload_and_unset_module "faulty"

    #unload_and_unset_module "silly"
    #unload_and_unset_module "complete"
    #unload_and_unset_module "sleepy"
    #unload_and_unset_module "jit"
    #unload_and_unset_module "jiq"

    #unload_and_unset_module "kdataalign"
    #unload_and_unset_module "kdatasize"
    #unload_and_unset_module "seq"

    #unload_and_unset_module "hellop"
    

    ;;
    
  *)
    printf "Usage: $0 {start|stop}\n"
    exit 1
esac

exit 0
