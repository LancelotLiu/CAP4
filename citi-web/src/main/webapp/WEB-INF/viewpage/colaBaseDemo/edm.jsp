<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="decorator" content=cola>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>ZIP TEST PAGE</title>
<style type="text/css">
#overwrite_Y, #overwrite_N {
	height: 20px;
	width: 30px;
}

.mainBody input {
	width: 70%;
}

.leftBox {
	width: 60%;
	float: left;
	border-right: 2.3px dotted #CBC9CF;
}

.rightBox {
	background: #E9E7ED;
	width: 39%;
	height: 15.65em;
	float: right;
}

.ftlRightBox {
	height: 11em;
}

.isEmptyFolderRightBox {
	height: 8em;
}

.isExistsFileRightBox {
	height: 5em;
}

.outerBox {
	display: inline-block;
	border-bottom: 1px solid #CBC9CF;
	padding-bottom: 0.7em;
	width: 100%;
}
</style>
</head>
<body>
	<script>
    loadScript('js/colaBaseDemo/edm');
  </script>
	<h3>《寄送EDM》</h3>
	<div class="outerBox">
		<div class="leftBox">
			<form id="edmForm" autocomplete="off" method="post">
				<div>
					寄送地址 <input type="text" id="eamilAccount" name="eamilAccount" />
				</div>
				<div>
					選擇ftl&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" id="edmFtlPath" name="edmFtlPath" />
				</div>
				<div>
					客戶姓名 <input type="text" id="edmCustomerName" name="edmCustomerName" />
				</div>
				<div>
					選擇方案 <input type="text" id="edmProject" name="edmProject" />
				</div>
			</form>
			<button id="sendEdmBtn" class="sendbtn" type="button">送出</button>
		</div>
		<div class="rightBox">
			<p id="edmResult"></p>
		</div>
	</div>
	<div>
		<h3>《產檔ftl》</h3>
		<div class="outerBox">
			<div class="leftBox">
				<form id="ftlForm" autocomplete="off" method="post">
					<div>
						選擇檔案 <input type="text" id="ftlFile" name="ftlFile" />
					</div>
					<div>
						輸出目錄 <input type="text" id="ftlOutPath" name="ftlOutPath" />
					</div>
				</form>
				<button id="sendFtlBtn" class="sendbtn" type="button">送出</button>
			</div>
			<div class="rightBox ftlRightBox">
				<p id="ftlResult"></p>
			</div>
		</div>
	</div>
</body>
</html>
