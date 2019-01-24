$(document).ready(function () {
  $("#close-btn1,#close-btn2").click(function () {
    location.reload();
  });
  $("#plus-btn").click(function () {
    $("#myModalLabel").html("批量添加微博帐号信息");
    $("#userName").val("");
    $("#wb_id").val("");
    $("#temp").val(0);
  });

  $("#submit_wb").click(function () {
    var userName = $("#userName").val() == null ? "" : $("#userName").val();
    var temp = $("#temp").val();
    var id = $("#wb_id").val();
    var url = "";
    if (temp == 0) {
      url = "/wb/add";
      $.post(url, {userName: userName}, function (result) {
        if (result == "ok") {
          $("#msg").html("添加成功！");
          $("#msg").css("color", "green");
          $("#userName").val("");
        } else {
          $("#msg").html("请编辑微博帐号信息！");
          $("#msg").css("color", "red");
        }
      });
    } else {
      url = "/wb/upd";
      $.post(url, {id: id, userName: userName}, function (result) {
        if (result == "ok") {
          $("#msg").html("修改成功！");
          $("#msg").css("color", "green");
          $("#userName").val("");
        } else if (result == "no") {
          $("#msg").html("请编辑微博帐号信息！");
          $("#msg").css("color", "red");
        } else {
          $("#msg").html("只能修改单个帐号密码信息，请重新编辑！");
          $("#msg").css("color", "red");
        }
      });
    }
  });

  /**
   * 导出分组下场统计Excel
   * 2018-08-23 Joey
   */
  $("#export-btn").click(function () {
    var exportExcelUrl = "/util/export";
    var pageNum = $("#pageNum").text();
    var pageSize = $("#pageSize").text();
    var day = $("#day").val();
    var unbanned = $("#unbanned").val();
    var msg = "您确定要导出" + day + "的数据吗?";
    location.href=exportExcelUrl+"?"+"pageNum="+pageNum+"&pageSize="+pageSize+"&day="+day+"&unbanned="+unbanned;
  });

});


function edit_wb(id, userName, passWord) {
  $("#myModalLabel").html("单个微博帐号信息修改");
  $("#userName").val(userName + "----" + passWord);
  $("#temp").val(1);
  $("#wb_id").val(id);
  $("#msg").html("");
}

function del_wb(id) {
  $.post("/wb/del", {id: id}, function (result) {
    if (result == "ok") {
      location.reload();
    }
  });
}

function selectQuery(pageNum, pageSize) {
  var day = $("#day").val();
  var unbanned = "0";
  if (day != 0) {
    unbanned = "1";
    $("#unbanned").val(unbanned);
  }
  common(unbanned, day, pageNum, pageSize);
}

/**
 * previous
 */
function prev() {
  var pageNum = $("#pageNum").text();
  var pageSize = $("#pageSize").text();
  var day = $("#day").val();
  var unbanned = $("#unbanned").val();
  if (day==0){
    unbanned=0;
  }else {
    unbanned=1;
  }
  $("#unbanned").val(unbanned);
  var pn = (parseInt(pageNum) - 1);
  common(unbanned, day, pn, pageSize);
}

function next() {
  var pageNum = $("#pageNum").text();
  var pageSize = $("#pageSize").text();
  var day = $("#day").val();
  var unbanned = $("#unbanned").val();
  if (day==0){
    unbanned=0;
  }else {
    unbanned=1;
  }
  $("#unbanned").val(unbanned);
  var pn = (parseInt(pageNum) + 1);
  common(unbanned, day, pn, pageSize);
}

function common(unbanned, day, pageNum, pageSize) {
  $.get("/wb/ajax", {day: day, pageNum: pageNum, pageSize: pageSize, unbanned: unbanned}, function (result) {
    if (result.ok == "ok") {
      var data = result.weiboLists;
      var htmlstr = "";
      $.each(data, function (n, value) {
        var status = "";
        switch (value.status) {
          case 0 :
            status = "正常";
            break;
          case 1 :
            status = "封禁";
            break;
          case 2 :
            status = "登录超时";
            break;
          case 3 :
            status = "密码错误";
            break;
          default :
            status = "正常";
        }
        var used = "";
        switch (value.used) {
          case 0 :
            used = "未使用";
            break;
          case 1 :
            used = "已使用";
            break;
          default :
            used = "未使用";
        }
        var createTime = dateFtt("yyyy-MM-dd hh:mm:ss", new Date(value.createTime));
        htmlstr = htmlstr + "<tr><td>" + (n + 1) + "</td><td>" + value.userName + "</td><td>" + value.passWord + "</td><td>" + status + "</td><td>" + createTime + "</td><td>" + used + "</td>\n" +
          "<td><a href=\"javascript:;\" onclick=\"edit_wb("+value.id+",'"+value.userName+"','"+value.passWord+"');\" class=\"btn btn-info btn-xs\" data-toggle=\"modal\" data-target=\"#myModal\" id=\"edit-btn\">" +
          "<i class=\"fa fa-pencil\"></i> 编辑 </a><a href=\"javascript:;\" onclick=\"del_wb("+value.id+");\" class=\"btn btn-danger btn-xs\" >" +
          "<i class=\"fa fa-trash-o\"></i> 删除 </a></td> </tr>"
      });
      $("#cotents").html(htmlstr == null || htmlstr == "" ? "<tr><td colspan='7' style='text-align: center'>没有数据</td></tr>" : htmlstr);
      $("#start").text(result.start);
      $("#end").text(result.end);
      $("#pageTotal").text(result.pageTotal);
      $("#pageNum").text(result.pageNum);
      $("#totalPages").text(result.totalPages);
      if (!result.isFirstPage) {
        $("#datatable_previous").html("<a href=\"#\" aria-controls=\"datatable\" data-dt-idx=\"0\" tabindex=\"0\"\n" + " onclick=\"prev();\">上一页</a>");
      } else {
        $("#datatable_previous").html("<a href=\"#\" aria-controls=\"datatable\" data-dt-idx=\"0\" tabindex=\"0\" href=\"javascript:void(0);\">上一页</a>");
      }
      if (!result.isLastPage) {
        $("#datatable_next").html("<a href=\"#\" aria-controls=\"datatable\" data-dt-idx=\"8\" tabindex=\"0\" onclick=\"next();\">下一页</a>");
      } else {
        $("#datatable_next").html("<a href=\"#\" aria-controls=\"datatable\" data-dt-idx=\"8\" tabindex=\"0\" href=\"javascript:void(0);\">下一页</a>");
      }
    }
  });
}

function dateFtt(fmt, date) { //author: meizz
  var o = {
    "M+": date.getMonth() + 1,     //月份
    "d+": date.getDate(),     //日
    "h+": date.getHours(),     //小时
    "m+": date.getMinutes(),     //分
    "s+": date.getSeconds(),     //秒
    "q+": Math.floor((date.getMonth() + 3) / 3), //季度
    "S": date.getMilliseconds()    //毫秒
  };
  if (/(y+)/.test(fmt))
    fmt = fmt.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));
  for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt))
      fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
  return fmt;
}
