; // 自定义校验方法
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

function initForm(options, form) {
    if(!form) {
        form = $("#inputForm") || $("form").first();
    }
    form.find("input:text").not("[readonly]").first().focus();
    options = $.extend({}, {
        submitHandler: function(form){
            loading('正在提交，请稍等...');
            form.submit();
        },
        errorContainer: "#messageBox",
        errorPlacement: function(error, element) {
            $("#messageBox").text("输入有误，请先更正。");
            if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
                error.appendTo(element.parent().parent());
            } else {
                error.insertAfter(element);
            }
        }
    }, options);
    form.validate(options);
}
