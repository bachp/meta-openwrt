# Copyright (C) 2016 Khem Raj <raj.khem@gmail.com>
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "Tiny HTTP server"
HOMEPAGE = "http://git.openwrt.org/?p=project/uhttpd.git;a=summary"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://main.c;beginline=1;endline=18;md5=ba30601dd30339f7ff3d0ad681d45679"
SECTION = "base"
DEPENDS = "libubox ubus ucode json-c ustream-ssl virtual/crypt"

SRC_URI = "\
          git://git.openwrt.org/project/uhttpd.git;protocol=https;branch=master \
          file://0100-fix-wrong-binaries-found-due-to-inconsistent-path.patch \
	  "

SRCREV = "e3395cd90bed9b7b9fc319e79528fedcc0d947fe"
S = "${WORKDIR}/git"

inherit cmake pkgconfig openwrt-services openwrt openwrt-base-files

SRCREV_openwrt = "${OPENWRT_SRCREV}"

CFLAGS += "-D_DEFAULT_SOURCE"

EXTRA_OECMAKE = "-DTLS_SUPPORT=ON -DLUA_SUPPORT=ON -DUBUS_SUPPORT=ON"

do_install:append() {
    install -Dm 0755 ${S}/openwrt/package/network/services/uhttpd/files/uhttpd.init ${D}${sysconfdir}/init.d/uhttpd
    install -Dm 0644 ${S}/openwrt/package/network/services/uhttpd/files/uhttpd.config ${D}${sysconfdir}/config/uhttpd
    install -Dm 0644 ${S}/openwrt/package/network/services/uhttpd/files/ubus.default ${D}${sysconfdir}/uci-defaults/00_uhttpd_ubus
    install -dm 0755 ${D}/usr/sbin
    ln -s /usr/bin/uhttpd ${D}/usr/sbin/uhttpd
    install -dm 0755 ${D}/www
}

FILES:${PN}  += "${libdir}/* /www"

RDEPENDS:${PN} += "\
                  openssl \
                  base-files-scripts-openwrt \
                  "
