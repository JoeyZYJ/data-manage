$("#close-btn1,#close-btn2").click(function () {
  location.reload();
});
$("#plus-btn").click(function () {
  $("#myModalLabel").html("批量添加抖音帐号信息");
  $("#tId").val("");
  $("#tt_id").val("");
  $("#temp").val(0);
});
$("#submit_tt").click(function () {
  var tId = $("#tId").val() == null ? "" : $("#tId").val();
  var temp = $("#temp").val();
  var id = $("#tt_id").val();
  var url = "";
  if (temp == 0) {
    url = "/tt/add";
    $.post(url, {tId: tId}, function (result) {
      if (result == "ok") {
        $("#msg").html("添加成功！");
        $("#msg").css("color", "green");
        $("#tId").val("");
      } else {
        $("#msg").html("请编辑抖音帐号信息！");
        $("#msg").css("color", "red");
      }
    });
  } else {
    url = "/tt/upd";
    $.post(url, {id: id, tId: tId}, function (result) {
      if (result == "ok") {
        $("#msg").html("修改成功！");
        $("#msg").css("color", "green");
        $("#tId").val("");
      } else if(result == "no") {
        $("#msg").html("请编辑抖音帐号信息！");
        $("#msg").css("color", "red");
      }else {
        $("#msg").html("只能修改单个帐号信息，请重新编辑！");
        $("#msg").css("color", "red");
      }
    });
  }

});

function edit_tt(id,tId) {
  $("#myModalLabel").html("单个抖音帐号信息修改");
  $("#tId").val(tId);
  $("#temp").val(1);
  $("#tt_id").val(id);
  $("#msg").html("");
}

function del_tt(id) {
  $.post("/tt/del", {id: id}, function (result) {
    if (result == "ok") {
      location.reload();
    }
  });
}
