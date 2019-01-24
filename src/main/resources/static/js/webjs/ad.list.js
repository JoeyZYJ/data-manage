$("#close-btn1,#close-btn2").click(function () {
  location.reload();
});
$("#plus-btn").click(function () {
  $("#myModalLabel").html("批量添加话术");
  $("#detail").val("");
  $("#phoneMark").val("");
  $("#phoneMark").attr("placeholder","示例：1,2,3,4,5(英文逗号)");
  // $("#remarks").val("");
  $("#adv_id").val("");
  $("#temp").val(0);
});
$("#submit").click(function () {
  var detail = $("#detail").val() == null ? "" : $("#detail").val();
  var phoneMark = $("#phoneMark").val() == null ? "" : $("#phoneMark").val();
  // var remarks = $("#remarks").val() == null ? "" : $("#remarks").val();
  var temp = $("#temp").val();
  var id = $("#adv_id").val();
  var url = "";
  if (temp == 0) {
    url = "/adv/add";
    $.post(url, {detail: detail, phoneMark: phoneMark}, function (result) {
      if (result == "ok") {
        $("#msg").html("添加成功！");
        $("#msg").css("color", "green");
        $("#detail").val("");
        $("#phoneMark").val("");
        // $("#remarks").val("");
      } else {
        $("#msg").html("请输入话术内容！");
        $("#msg").css("color", "red");
      }
    });
  } else {
    url = "/adv/upd";
    $.post(url, {detail: detail, phoneMark: phoneMark}, function (result) {
      if (result == "ok") {
        $("#msg").html("修改成功！");
        $("#msg").css("color", "green");
        $("#detail").val("");
        $("#phoneMark").val("");
        // $("#remarks").val("");
      } else {
        $("#msg").html("请编辑话术内容！");
        $("#msg").css("color", "red");
      }
    });
  }

});

function edit_ad(detail, mark) {
  $("#myModalLabel").html("查看话术");
  $("#detail").val(detail);
  $("#phoneMark").attr("placeholder","示例：1");
  $("#phoneMark").val(mark);
  // $("#remarks").val(remark);
  $("#temp").val(1);
  // $("#adv_id").val(id);
  $("#msg").html("");
  $("#submit").hide();
}

function del_ad(id) {
  $.post("/adv/del", {phoneId: id}, function (result) {
    if (result == "ok") {
      location.reload();
    }
  });
}
