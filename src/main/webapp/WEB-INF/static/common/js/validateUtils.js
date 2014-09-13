$.validator.addMethod("mobileCN", function(phone_number, element) {
    phone_number = phone_number.replace(/\(|\)|\s+|-/g, "");
    return this.optional(element) || phone_number.length > 9 &&
                                     phone_number.match(/^(((00|\+)86)?1\d{10})$/);
}, "请输入正常的手机号码");

$.validator.addMethod("mobileOrPhoneCN", function(phone_number, element) {
    phone_number = phone_number.replace(/\(|\)|\s+|-/g, "");
    return this.optional(element) || phone_number.length > 9 &&
                                     (phone_number.match(/^(((00|\+)86)?1\d{10})$/) ||
                                      phone_number.match(/^(\d{3,4}-?\d{7,9})$/));
}, "请输入正常的手机或电话号码");
