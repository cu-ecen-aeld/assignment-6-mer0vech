# Recipe created by recipetool
# This is the basis of a recipe and may need further editing in order to be fully functional.
# (Feel free to remove these comments when editing.)

# WARNING: the following LICENSE and LIC_FILES_CHKSUM values are best guesses - it is
# your responsibility to verify that the values are complete and correct.
#
# The following license files were not able to be identified and are
# represented as "Unknown" below, you will need to check them yourself:
#   LICENSE
LICENSE = "Unknown"
LIC_FILES_CHKSUM = "file://LICENSE;md5=f098732a73b5f6f3430472f5b094ffdb"

SRC_URI = "git://github.com/cu-ecen-aeld/assignment-7-mer0vech;protocol=https;branch=master \
           file://0001-ldd3-makefile-modified.patch \
           "

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "f8f9d8b99ca10cac4d42a1775b7ffc4b509332ef"

S = "${WORKDIR}/git"

inherit module
inherit update-rc.d

MODULES_INSTALL_TARGET = "install"
EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"
EXTRA_OEMAKE += "-C ${STAGING_KERNEL_DIR} M=${S}/scull EXTRA_CFLAGS=-I${S}/include"

INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} = "scull_init.sh"
INITSCRIPT_PARAMS:${PN} = "defaults 90 10"
FILES:${PN} += "${sysconfdir}/init.d/scull_init.sh"

do_install () {
    install -d ${D}${sysconfdir}/init.d/

    install -m 0755 ${WORKDIR}/scull_init.sh ${D}${sysconfdir}/init.d/
}

