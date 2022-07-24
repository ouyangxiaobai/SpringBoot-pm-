<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>页面找不到了</title>
    <#include "../admin/common/header.ftl"/>
    <style>
        html {
            margin: 0;
            padding: 0;
            background-color: white;
        }

        body,
        html {
            width: 100%;
            height: 100%;
            overflow: hidden;
        }

        #svgContainer {
            width: 640px;
            height: 512px;
            background-color: white;
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            bottom: 0;
            margin: auto;
        }
    </style>

</head>
<body>

<script type="text/javascript" src="/admin/js/bodymovin.js"></script>
<script type="text/javascript" src="/admin/js/data.js"></script>
<script type="text/javascript" src="/admin/js/jquery.min.js"></script>
<script type="text/javascript" src="/admin/js/bootstrap.min.js"></script>
<!--对话框-->
<script src="/admin/js/jconfirm/jquery-confirm.min.js"></script>
<script src="/admin/js/common.js"></script>

<div id="svgContainer"></div>
<div class="alert alert-success" role="alert" style="text-align: center">${msg!""}</div>

<script type="text/javascript">
    var svgContainer = document.getElementById('svgContainer');
    var animItem = bodymovin.loadAnimation({
        wrapper: svgContainer,
        animType: 'svg',
        loop: true,
        animationData: JSON.parse(animationData)
    });
</script>

<div style="text-align:center;margin:10px 0; font:normal 14px/24px 'MicroSoft YaHei';">
</div>
</body>
</html>

