function genPwd(pwd, salt) {
    return new jsSHA(pwd, "TEXT").getHMAC(salt, "TEXT", "SHA-256", "B64")
}
