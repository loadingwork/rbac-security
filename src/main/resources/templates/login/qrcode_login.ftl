<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>二维码登录</title>
    <link rel="stylesheet" href="${ctx}/static/css/global.css">
    <style type="text/css">
    	html, body {
    		margin: 0;
    		padding: 0;
    	}
    	body {
    		background: url("/static/images/background/login/qrcode.jpg") 0 0  no-repeat;
    	}
    	html,body,.g-body {
    		width: 100%;
    		height: 100%;
    	}
    	
    	.g-login {
		    max-width: 440px;
		    max-height: 472px;
		    background-color:#fff;
		    position: absolute;
		    top: 0;
		    bottom: 0;
		    right: 0;
		    left: 0;
		    margin: auto;
		    z-index: 1;
		    -moz-border-radius: 5px;
		    -webkit-border-radius: 5px;
		    border-radius: 5px;
		}
    	
    	.m-qrcode {
    		
    	}
    	.m-qrcode .title {
    		text-align: center;
    		font-size: 56px;
    	}
    	
    	.m-qrcode .qrcode {
    		max-width: 300px;
    		height: 300px;
    		margin: 10 auto;
    	}
    	
    	.m-qrcode .qrcode img {
    		width: 100%;
    		height: 100%;
    	}
    	
    	.m-option {
    		text-align: center;
    		width: 100%;
    	}
    	
    	.m-option a {
    		display: block;
		    text-align: center;
		    text-decoration: none;
		    padding: 5px;
		    -moz-border-radius: 40px;
		    -webkit-border-radius: 40px;
		    border-radius: 40px;
		    background-color: #bae637;
		    max-width: 300px;
		    margin: 10px auto;
		    cursor: pointer;
    	}
    	
    	.m-option a:hover {
    		background: #73d13d;
    	}
    	
    	.m-option a:active {
		    background: #52c41a;
		}
    	
    	
    	
    </style>
</head>
<body>
    <div class="g-body" >
    	<div class="g-login">
   			<div class="m-qrcode" >
   				<div class="title">平台登录</div>
	   			<div class="qrcode">
	   				<img src="${ctx}/login/qrcode/img.do?qrcodeToken=${qrcodeToken}" />
	   			</div>
   			</div>
   			<div class="m-option">
   				<a href="${ctx}/login/login" >用户名登录</a>
   			</div>
    	</div>
    </div>
    
    <div  style="display: none;">
    	<form action="${ctx}/login/qrcode.do"  method="POST"  id="qrloginForm" >
    		<input type="text"  name="qrcode_token" value="${qrcodeToken}" />
    		<input type="text"  name="client_id" value="" id="client_id" />
    		<input type="text"  name="client_token" value=""  id="client_token" />
    	</form>
    
    </div>
    
    <script type="text/javascript" src="${ctx}/static/lib/sockjs-client/sockjs.min.js" ></script>
    <script type="text/javascript" src="${ctx}/static/lib/stomp.js/stomp.min.js" ></script>
    <script type="text/javascript">
    "use strict"
    
    var qrcodeToken = '${qrcodeToken}';
    
    
    $(function() {
    	
    	// 建立连接对象（还未发起连接）
    	var sockjs = new SockJS(ctx + '/wserver');
    	// 获取 STOMP 子协议的客户端对象
    	var stompClient = Stomp.over(sockjs);
		
    	// 向服务器发起websocket连接并发送CONNECT帧
    	stompClient.connect({}, function(frame) {
    		
    		// 推送状态
    		stompClient.subscribe("/queue/qrlogin/" + qrcodeToken, function(resp){   
				console.log(resp);
				var data = JSON.parse(resp.body);
				
				if(data.status === 'scan_ok') {
					$("#client_id").val(data.clientId);
				} else if(data.status === 'user_ok') {
					$("#client_token").val(data.clientToken);
					// 关闭连接
					try{
						stompClient.disconnect(function() {
							console.log("stompClient断开连接");
							$("#qrloginForm").submit();
						 });
					} catch(e) {
						console.log(e);
					}
				}
				
			});
    		
    		
    	}, function(error){
    		console.log(error);
    	});
    	
    	
    	
    });
    
	</script>
</body>
</html>