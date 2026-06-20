LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "git://github.com/cu-ecen-aeld/assignment-7-mer0vech;protocol=https;branch=master \
           file://0001-ldd3-makefile-modified.patch \
           file://misc-modules-init.sh \
           "

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "f8f9d8b99ca10cac4d42a1775b7ffc4b509332ef"

S = "${WORKDIR}/git"
MODULES_SUBDIR = "misc-modules"

EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"

inherit module
inherit update-rc.d

INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} = "misc-modules-init"
INITSCRIPT_PARAMS:${PN} = "start 99 S . stop 11 0 1 6 ."

do_install () {
    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/misc-modules-init.sh ${D}${sysconfdir}/init.d/misc-modules-init

    install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}/extra
    install -m 0644 ${S}/misc-modules/*.ko ${D}${base_libdir}/modules/${KERNEL_VERSION}/extra/
}

FILES:kernel-module-faulty = "${base_libdir}/modules/${KERNEL_VERSION}/extra/faulty.ko"
FILES:kernel-module-hello = "${base_libdir}/modules/${KERNEL_VERSION}/extra/hello.ko"
FILES:kernel-module-complete = "${base_libdir}/modules/${KERNEL_VERSION}/extra/complete.ko"
FILES:kernel-module-hellop = "${base_libdir}/modules/${KERNEL_VERSION}/extra/hellop.ko"
FILES:kernel-module-jiq = "${base_libdir}/modules/${KERNEL_VERSION}/extra/jiq.ko"
FILES:kernel-module-jit = "${base_libdir}/modules/${KERNEL_VERSION}/extra/jit.ko"
FILES:kernel-module-kdataalign = "${base_libdir}/modules/${KERNEL_VERSION}/extra/kdataalign.ko"
FILES:kernel-module-kdatasize = "${base_libdir}/modules/${KERNEL_VERSION}/extra/kdatasize.ko"
FILES:kernel-module-seq = "${base_libdir}/modules/${KERNEL_VERSION}/extra/seq.ko"
FILES:kernel-module-silly = "${base_libdir}/modules/${KERNEL_VERSION}/extra/silly.ko"
FILES:kernel-module-sleepy = "${base_libdir}/modules/${KERNEL_VERSION}/extra/sleepy.ko"

FILES:${PN} = "${sysconfdir}/init.d/misc-modules-init"

INSANE_SKIP:${PN} = "installed-vs-shipped"
INSANE_SKIP:kernel-module-faulty = "installed-vs-shipped"
INSANE_SKIP:kernel-module-hello = "installed-vs-shipped"
INSANE_SKIP:kernel-module-complete = "installed-vs-shipped"
INSANE_SKIP:kernel-module-hellop = "installed-vs-shipped"
INSANE_SKIP:kernel-module-jiq = "installed-vs-shipped"
INSANE_SKIP:kernel-module-jit = "installed-vs-shipped"
INSANE_SKIP:kernel-module-kdataalign = "installed-vs-shipped"
INSANE_SKIP:kernel-module-kdatasize = "installed-vs-shipped"
INSANE_SKIP:kernel-module-seq = "installed-vs-shipped"
INSANE_SKIP:kernel-module-silly = "installed-vs-shipped"
INSANE_SKIP:kernel-module-sleepy = "installed-vs-shipped"

RDEPENDS:${PN} += "kernel-module-faulty"
RDEPENDS:${PN} += "kernel-module-hello"
RDEPENDS:${PN} += "kernel-module-complete"
RDEPENDS:${PN} += "kernel-module-hellop"
RDEPENDS:${PN} += "kernel-module-jiq"
RDEPENDS:${PN} += "kernel-module-jit"
RDEPENDS:${PN} += "kernel-module-kdataalign"
RDEPENDS:${PN} += "kernel-module-kdatasize"
RDEPENDS:${PN} += "kernel-module-seq"
RDEPENDS:${PN} += "kernel-module-silly"
RDEPENDS:${PN} += "kernel-module-sleepy"
