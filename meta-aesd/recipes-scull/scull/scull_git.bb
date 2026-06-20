LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "git://github.com/cu-ecen-aeld/assignment-7-mer0vech;protocol=https;branch=master \
           file://0001-ldd3-makefile-modified.patch \
           file://scull-init.sh \
           "

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "f8f9d8b99ca10cac4d42a1775b7ffc4b509332ef"

S = "${WORKDIR}/git"
MODULES_SUBDIR = "scull"

EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"

inherit module
inherit update-rc.d

INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} = "scull-init"
INITSCRIPT_PARAMS:${PN} = "start 98 S . stop 10 0 1 6 ."

do_install () {
    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/scull-init.sh ${D}${sysconfdir}/init.d/scull-init

    install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}/extra
    install -m 0644 ${S}/scull/scull.ko ${D}${base_libdir}/modules/${KERNEL_VERSION}/extra/
}

FILES:kernel-module-scull = "${base_libdir}/modules/${KERNEL_VERSION}/extra/scull.ko"
FILES:${PN} = "${sysconfdir}/init.d/scull-init"

INSANE_SKIP:${PN} = "installed-vs-shipped"
INSANE_SKIP:kernel-module-scull = "installed-vs-shipped"

RDEPENDS:${PN} += "kernel-module-scull"

