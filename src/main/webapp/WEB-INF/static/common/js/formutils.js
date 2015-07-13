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

function initForm(form, options) {
    if(!form || 0 == $(form).length) {
        form = $("#inputForm") || $("form").first();
        options = form;
    } else {
        form = $(form);
    }
    form.find("input:text").not("[readonly]").first().focus();
    form.find("input[data-defVal]").each(function() {
        var input = $(this);
        input.val(input.attr("data-defVal"));
    });
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
