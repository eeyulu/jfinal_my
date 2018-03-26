<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>页面正在跳转中.....</title>
    <script type="text/javascript" src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script>
    <script type="text/javascript">

        function askLogin(askUrl,askData,okUrl) {
            $.ajax(
             {
                 'type':'post',
                 'url':askUrl,
                 'data':{
                     'askData':askData
                 },
                 'dataType':'jsonp',
                 'jsonp':'callback',
                 'success':function (data) {
                    var msg = data.msg;
                    if(msg == "-1"){
				    	window.location.href = "http://sso.test.com:8080/login?ReturnURL=http%3A%2F%2Fmy.web.com:8099%3A8099%2Fask";
			    	}else{
                        $.ajax({
                             'type':'post',
                             'url':okUrl,
                             'data':{
                                 'replyText':data.msg
                             },
                            'dataType':'json',
                            'success':function (data) {
                            	console.log(data.url);
                                window.location.href = data.url;
                            }
                        });
                    }
                }
            });
        }

        $(function () {

            askLogin('${askurl}','${askData}','${okurl}');

            window.setTimeout(function () {
                window.location.href = "http://my.web.com:8099/timeout.html";
            },30000)




        });

    </script>
</head>
<body>
    <h1>页面正在跳转中..........</h1>
</body>
</html>