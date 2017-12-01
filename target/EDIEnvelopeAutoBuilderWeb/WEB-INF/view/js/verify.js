function getXHR(){//获取跨浏览器的XmlHttpRequest对象
    var req;
    if (window.XMLHttpRequest) {
        req= new XMLHttpRequest();
    }else if(window.ActiveXObject){
        req= new ActiveXObject("Microsoft.XMLHTTP");
    }
    return req;
}

function getServerData(){

    //获取跨浏览器的XHR对象
    xhr = getXHR();

    //设置随机数，避免AjaxURL的HTTP缓存
    rand= Math.random(1);
    url="phpdata.php?id="+rand;

    //XmlHttpRequest对象的open()方法；
    //创建一个新的HTTP连接。
    //可以是GET或者POST这样的所有有效的请求类型。
    //URL应该是一个完整的路径地址。
    //false或者true参数控制send()方法是会阻塞（等待服务器返回数据）还是立即返回。
    xhr.open("GET",url,false);

    //XmlHttpRequest对象的send()方法；
    //向服务器发出请求。
    //基于open()方法的false（同步）或者true（异步）控制，或者等待服务器数据，或者立即返回
    //括号中content参数，在post类型中用到，它可以用来向服务器发送数据
    xhr.send();

    //XmlHttpRequest对象的responseText属性：以字符串形式表达服务器的响应内容
    json=xhr.responseText;

    //将json数据转为js数组
    jsArr=json.parseJSON();
    div=document.getElementById("jsonData");
    div.innerHTML=jsArr[1];
}
