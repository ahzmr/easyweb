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

$.validator.addMethod("repeatCheck", function(value, element, param) {
    var optional = this.optional(element);
    if(optional) {
        return optional;
    }
    param = typeof param === "string" && { url: param } || param;

    var $element = $(element);
    var oldValue = param.oldValue || $element.data("oldValue");
    if(oldValue == value) {
        return true;
    }

    var url = param.url, name = element.name;
    var success = false;
    var data = {
        elementName: name
    };
    data[name] = value;
    $.ajax(url, {
        dataType: 'json',
        async: false,
        data: data,
        success: function(data) {
            success = data;
        }
    });
    return success;
}, "请输入不重复的数据");

function initForm(form, options) {
    if(!form || typeof form !== 'string') {
        options = form;
        form = $("#inputForm") || $("form").first();
    } else {
        form = $(form);
    }
    form.find("input:text").not("[readonly]").first().focus();
    form.find("input[data-defVal]").each(function() {
        var input = $(this);
        input.val(input.attr("data-defVal"));
    });
    options = $.extend({}, {
        onkeyup: false,
        submitHandler: function(form){
            loading('正在提交，请稍等...');
            form.submit();
        },
        errorPlacement: function(error, element) {
            if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
                error.appendTo(element.parent().parent());
            } else {
                error.insertAfter(element);
            }
        }
    }, options);
    form.validate(options);
}
