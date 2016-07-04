   /*尽量放在HTML的最后*/

var anchorsArray = [];
var navigationTooltipsArray = [];
	anchorsArray.push('j408');
	navigationTooltipsArray.push('首页');
	anchorsArray.push('j409');
	navigationTooltipsArray.push('了解朗国');
	anchorsArray.push('j410');
	navigationTooltipsArray.push('加入朗国');
	anchorsArray.push('j411');
	navigationTooltipsArray.push('我在朗国');
	anchorsArray.push('j412');
	navigationTooltipsArray.push('你问我答');
	anchorsArray.push('j413');
	navigationTooltipsArray.push('联系我们');
var firstComeIn=-1;
	$(document).ready(
			function() {
				$('#fullpage').fullpage({
					anchors : anchorsArray, 
					navigation : true,
					navigationPosition : 'right',
					navigationTooltips :  navigationTooltipsArray, 
                    
					afterLoad: function(anchorLink, index){
					  $('#navUl li').removeClass('navActive');
						$('#navUl li#nav_'+anchorLink).addClass('navActive');
                
                        if(index=="2"){
                          tergether();
                         }
                        
                        if(firstComeIn==-1){
                            //console.log("ok");
                            $("#comeforus").animate({"margin-left":"600px"},0);
                            $("#comeforus").animate({"margin-left":"0px"},4000);
                            firstComeIn++;
                        }
                        
					},
                    onLeave : function(index,nextIndex,direction){
                        if(nextIndex=="2"){
                          $('#slide2Title').animate({'margin-top':"-500px",'margin-left':"1140px"},0);
                          $('#sec2con1').animate({'margin-top':"-500px"},0);
                          $('#sec2con2').animate({'margin-left':"3000px"},0);
                          $('#sec2con3').animate({'margin-left':"-1000px"},0);
                          $('#sec2con4').animate({'margin-top':"500px"},0);
                      }
                        
                    },
                    afterSlideLoad:function(index,slideIndex,direction,anchorLink){
                      if(direction==0){
                          tergether();
                      }
                    },
                    onSlideLeave:function(index){
                        //console.log(index);
                       if(index=="j409"){
                          $('#slide2Title').animate({'margin-top':"-500px",'margin-left':"1140px"},0);
                          $('#sec2con1').animate({'margin-top':"-500px"},0);
                          $('#sec2con2').animate({'margin-left':"3000px"},0);
                          $('#sec2con3').animate({'margin-left':"-1000px"},0);
                          $('#sec2con4').animate({'margin-top':"500px"},0);
                      } 
                    }
				});
			});
    
    
    /*------------------my scroll----------------------*/
    window.onload = function(){scrollPic();}

/*--------------------------------岗位----------------------------------*/
          var n=0;        /*margin 的数值*/
          //var liLength =$('#middleContainer div:nth-of-type(1) ul li').length;
          var liLength =$('#middleContainer div:first ul li').length;
          setBtnState();          


          function onBtnLeft(){
              //console.log("this is left");
              onExtend(); 
              //if(n>=6*136) return;   /*不可左拉*/
              
              if(n>=0) return;   /*不可左拉*/
              n=n+136;
              var $self = $('#middleContainer');
					$self.animate({
						'margin-left': n+"px"
					}, 300);
              setBtnState();      
                    
          }
          
          function onBtnRight(){
              //console.log("this is btnRight");
              onExtend(); 
              //if(n<=(-(liLength-1)*136))  return; /* 不可右拉*/
              
              if(n<=(-(liLength-7)*136))  return; /* 不可右拉*/
              n=n-136;
              var $self = $('#middleContainer');
					$self.animate({
						'margin-left': n+"px"
					}, 300);
                    
             setBtnState();   
          }
          
          function setBtnState(){
              if(n>=0) btnState(1,1);
              else btnState(1,2);
              if(n<=(-(liLength-7)*136)) btnState(2,1);
              else btnState(2,2);
          }

         //lr=1是左，lr=2是右；state=1是隐藏，state=2是现
          function btnState(lr,state){
              if(lr==1){
                  if(state==1){
                      $('#btnLeft').css("height","0px");
                  }else{
                      $('#btnLeft').css("height","40px");
                  }
              }else{
                  if(state==1){
                      $('#btnRight').css("height","0px");
                  }else{
                      $('#btnRight').css("height","40px");
                  }
              }
              
          }

          
          
          function onListActive(id){  /*三个list按键的事件*/
              clearListActive();
              var needMove = 0;
              //console.log("ok1");
              switch(id){
                      case 1:needMove=0;$("#listOfDev").addClass("active");$('#myActive').animate({"left":"85px"},500);break;
                      case 2:needMove=-212;$("#listOfMan").addClass("active");$('#myActive').animate({"left":"495px"},500);break;
                      case 3:needMove = -424;$("#listOfSup").addClass("active");$('#myActive').animate({"left":"905px"},500);break;
              }
              var middeleContainer = $("#middleContainer");
              middeleContainer.animate({'margin-top':needMove+"px"},450,function(){$("#middleContainer").css("margin-left","0px");});   
            
              n=0;     /*归零*/
              liLength = $('#middleContainer div:nth-of-type('+id+') ul li').length;
              onExtend(); 
              setBtnState();
          }
         
        function comeBackSec3(){    /* 点击叉叉按钮的事件*/
           
            onExtend(); 
        }


          function clearListActive(){      /* 清除选中*/
              $("#listOfDev").removeClass("active");
              $("#listOfMan").removeClass("active");
              $("#listOfSup").removeClass("active");
          }

          function onPosiActive(){     /*这是重点*/
              var id = this.id;       /*纯的js的方法*/              
              var p = this.getAttribute("position");     /*获得自定义属性的方法*/
              var lp = this.parentNode.getAttribute("listPosition");  /* 获得父结点用parentNode,如果用parent则出错*/
              
              var moveLeft = -(p-1)*136;
              var moveTop = -(lp-1)*212;
              
              onCompress(moveLeft,moveTop); 
          }
          
          function onCompress(moveLeft,moveTop){   /*压缩*/
             /* $("#middleContainer").animate({'margin-top':moveTop+"px",'margin-left':moveLeft+"px"},450);
              $("#middle").animate({'width':'136px'},300);
              $("#leftList").removeClass("left-list");
              $("#leftList").addClass("left-list-2");
              document.getElementById('btnLeft').style.display="none";
              $('#leftList').css("width","0");
              n=moveLeft;*/
              
              //document.getElementById('section3Text').style.display="block";
              //$("#section3Container").css('display','none');
              $('#section3Text').show(1000);
              $("#section3Container").hide(1000);
          }
          
          function onExtend(){      /*展开*/
              /*$("#middle").animate({'width':'952px'},450);
              document.getElementById('btnLeft').style.display="block";
              $("#leftList").removeClass("left-list-2");
              $("#leftList").addClass("left-list");
              $('#leftList').css("width","100%");*/
              
              //document.getElementById('section3Text').style.display="none";
              //$("#section3Container").css('display','block');
              $('#section3Container').show(800);
              $("#section3Text").hide(800);
              
          }
           function sec3MouseDown(){     /*鼠标不在section3Text时按下，实现展开效果*/
               if(mouseIn1==1||mouseIn2==1) return;
               //console.log("sec3MouseDown and n="+mouseIn1);
               onExtend();
           }
          
          $('#btnLeft').click(onBtnLeft);
          $('#btnRight').click(onBtnRight);
          
          $('#listOfDev').click(function(){onListActive(1);});
          $('#listOfMan').click(function(){onListActive(2);});
          $('#listOfSup').click(function(){onListActive(3);});
          //document.getElementById("listOfDev").attachEvent("onclick",function(){onListActive(1);});
          //document.getElementById("listOfMan").attachEvent("onclick",function(){onListActive(2);});
          //document.getElementById("listOfSup").attachEvent("onclick",function(){onListActive(3);});    /*监听传参数*/
          
          var mouseIn1 = 0;       /*用于监视鼠标是否在Text框内，1表in*/
          var mouseIn2 = 0;
          $('body').mousedown(sec3MouseDown);
          $('#section3Text').mouseenter(function(){mouseIn1=1;});
          $('#section3Text').mouseleave(function(){mouseIn1=0;});
          $('#section3Container').mouseenter(function(){mouseIn2=1;});
          $('#section3Container').mouseleave(function(){mouseIn2=0;});
          
          
          /*document.getElementsByTagName("li").addEventListener("click",onPosiActive,false); */
           $('#middleContainer li').click(onPosiActive);
          /*$(function(){
              var middleLi = document.getElementById("middleContainer").getElementsByTagName("li");
              var lenght = middleLi.length;       //给多个元素加事件
              for(var i=0;i<lenght;i++)
                  middleLi[i].attachEvent("onclick",onPosiActive); 
          });*/

    
          /*$("#section3Text").bind("mousewheel",function(e){
                  // 如果提供了事件对象，则这是一个非IE浏览器
              if ( e && e.stopPropagation ) {
                  // 因此它支持W3C的stopPropagation()方法 
                  e.stopPropagation();
              } else { 
                  // 否则，我们需要使用IE的方式来取消事件冒泡
                 window.event.cancelBubble = true;
               }
          });*/
          /*---------------------------------首页------------------------------------*/
          var guidId=1;
          function guidInterval(){  /*轮播*/
              guidId++;
              guidId=(guidId>7)?1:guidId;
              onGuidClick(guidId);
          }

          function onGuidClick(id){
              var sum = -(id-1)*222;
              guidId=id;
              var guidContent = $("#move");
              guidContent.animate({'margin-top':sum+"px"},600);
              //console.log(sum);
              onActive("#guidImg"+id);
              
          }
          
          function onActive(id){
              for(var i=1;i<=7;i++){
                  $("#guidImg"+i).removeClass('guidActive');
              }
              $(id).addClass('guidActive');
          }

          /*$('.contain').mouseenter(function(){$("#comeforus").css("background","url(testIMG/come6.png)");});
          $('.contain').mouseleave(function(){$("#comeforus").css("background","");});*/
          $('#comeforus').mousedown(function(){$(".contain").hide(1000);$("#contain2").show(1000);});
          function comeBackSec1(){$("#contain2").hide(1000);$(".contain").show(1000);}

          /*setInterval(function(){
              console.log("1");
              $("#comeforus").animate({"margin-left":"-600px"},
                                      4000,
                                      function(){
                                      console.log("2");
                                      $("#comeforus").css("margin-left","600px");
                                      $("#comeforus").animate({"margin-left":"0px"},4000);
                                      })},
                      8000);*/
          /*--------------------------------------*/
              $(function(){
            /*----------------首页------------------*/
              $("#guidImg1").addClass('guidActive');
              
              var guidTime = setInterval("guidInterval()",5000);       /*轮播开始*/
          /*----------------了解朗国------------------*/
              var add=0;
              for(var i=1;i<=8;i++)
              {   
                  $("#decMove div:nth-of-type("+i+")").css("left",add+"px");
                  add+=1020;
              }
          });
        
          /*----------------了解朗国------------------*/
          var nowId=1;
          function onDecClick(id){
              var decSum=0;
              var udActive = 140;
              if(id>8){  
                  if(nowId==8) return;
                  decSum = -(nowId-1)*1020-1020;
                  udActive += (nowId-1)*110+110; 
                  nowId++;
              }
              else if(id<0){ 
                  if(nowId==1) return ;
                  decSum = -(nowId-1)*1020+1020;
                  udActive +=(nowId-1)*110-110; 
                  nowId--;
              }else{
                  decSum = -(id-1)*1020;
                  udActive += (id-1)*110;
                  nowId = id;
              }
              var decContent = $("#decMove");
              decContent.animate({'margin-left':decSum+"px"},300);
              var underActive = $("#underActive");
              underActive.animate({'left':udActive+"px"},300);
              //console.log(nowId);
          }

  
/*--------------slide2---------------*/
/*$('#slide11').mouseenter(function(){slide1Over(1);});
$('#slide12').mouseenter(function(){slide1Over(2);});
$('#slide13').mouseenter(function(){slide1Over(3);});
$('#slide14').mouseenter(function(){slide1Over(4);});*/

$('#slide11').click(function(){slide1Click(1);});
$('#slide12').click(function(){slide1Click(2);});
$('#slide13').click(function(){slide1Click(3);});
$('#slide14').click(function(){slide1Click(4);});

$('#slide_a1').click(function(){slide1Click(1);});
$('#slide_a2').click(function(){slide1Click(2);});
$('#slide_a3').click(function(){slide1Click(3);});
$('#slide_a4').click(function(){slide1Click(4);});


/*
function slide1Over(id){
    //console.log(id);
}
*/

function slide1Click(id){
    apart();
    var a = '<a id="slideComeBack" href="javascript:slideComeBack();"></a>';
    $('#slideConM2').html(a+$('#slideConM2ForHide p:nth-of-type('+id+')').html());
    //console.log($('#slideConM2ForHide').html());
    //hideArrow();
    //console.log($('div[class="fp-controlArrow fp-next"]'));
}

function hideArrow(){
    $('div[class="fp-controlArrow fp-prev"]').css("height","0px");
    $('div[class="fp-controlArrow fp-next"]').css("height","0px");
}

function showArrow(){
    $('div[class="fp-controlArrow fp-prev"]').css("height","80px");
    $('div[class="fp-controlArrow fp-next"]').css("height","80px");
}
function slideComeBack(){
    tergether();
    //showArrow();
}

function apart(){
    $('#slide2Title').animate({'margin-top':"-500px",'margin-left':"1140px"},500);
    $('#sec2con1').animate({'margin-top':"-500px"},500);
    $('#sec2con2').animate({'margin-left':"3000px"},500);
    $('#sec2con3').animate({'margin-left':"-1000px"},500);
    $('#sec2con4').animate({'margin-top':"500px"},500);
    $('#slideMove').animate({'margin-left':"-1140px"},700);
    hideArrow();
}

function tergether(){
    $('#slide2Title').animate({'margin-top':"0px",'margin-left':"0px"},600);
    $('#sec2con1').animate({'margin-top':"0px"},600);
    $('#sec2con2').animate({'margin-left':"0px"},600);
    $('#sec2con3').animate({'margin-left':"0px"},600);
    $('#sec2con4').animate({'margin-top':"0px"},600);
    $('#slideMove').animate({'margin-left':"0px"},600);
    showArrow();
}



/*-----------slide3---------------*/

$('#slideCon1').click(function(){slide2Over(1);});
$('#slideCon2').click(function(){slide2Over(2);});
$('#slideCon3').click(function(){slide2Over(3);});
$('#slideCon4').click(function(){slide2Over(4);});
$('#slideCon5').click(function(){slide2Over(5);});

/*$('#slideCon1').mouseleave(function(){slide2Over(1,2);});
$('#slideCon2').mouseleave(function(){slide2Over(2,2);});
$('#slideCon3').mouseleave(function(){slide2Over(3,2);});
$('#slideCon4').mouseleave(function(){slide2Over(4,2);});
$('#slideCon5').mouseleave(function(){slide2Over(5,2);});*/

function slide2Over(id){
    //console.log(id);
    
    $('#slideText1').animate({'margin-top':"500px"},700);
    $('#slideText1').animate({'margin-top':"-500px"},0,function(){$('#slideText1').html($('#slide3TextHidden p:nth-of-type('+id+')').html());});
    /*$('#slideText1').html($('#slide3TextHidden p:nth-of-type('+id+')').html());*/
    $('#slideText1').animate({'margin-top':"0px"},700);
    /*if(select==1)
    $('#slide3TextHidden').css("background","url(testIMG/secShow"+id+".png)");
    else 
    $('#slideShowImg').css("background","url(testIMG/37.png) repeat");  */  
}


/*-----------slide4---------------*/

function slide4Over(id,select,image_url){
    if(select==1){ 
    $('#s4div'+id).css("background","transparent");
    }
    if(select==3){
    $('#s4div'+id).css("background","url("+image_url+")");
    }

}

/*-----------slide5---------------*/

$('#slide5Img1').mouseover(function(){slide5ImgOver(1);});
$('#slide5Img2').mouseenter(function(){slide5ImgOver(2);});
$('#slide5Img3').mouseenter(function(){slide5ImgOver(3);});
$('#slide5Img4').mouseenter(function(){slide5ImgOver(4);});

$('#slide5Img1').mouseleave(function(){slide5ImgLeave(1);});
$('#slide5Img2').mouseleave(function(){slide5ImgLeave(2);});
$('#slide5Img3').mouseleave(function(){slide5ImgLeave(3);});
$('#slide5Img4').mouseleave(function(){slide5ImgLeave(4);});

function slide5ImgOver(id){
    $('#slide5Text'+id).css("display","block");
    //console.log(1);
}

function slide5ImgLeave(id){
    $('#slide5Text'+id).css("display","none");
    //console.log(2);
}

    /*----------------你问我答------------------*/ 
      function comeBack(){  /* 回到你问我答*/
             swapText('section7Scroll','section7Text');
             //console.log("comeBack");
         }
      function comeBack1(){  /* 回到你问我答*/
          swapText('section7Scroll','askquesttion');
          //console.log("comeBack");
      }

     function swapText(id1,id2){     /*交换内容*/
             var div1 = document.getElementById(id1);
             var div2 = document.getElementById(id2);
             var temp = div1.innerHTML;
             div1.innerHTML = div2.innerHTML;
             div2.innerHTML = temp;
         }

    function pClick(id){
    	$.ajax({
    		async:false,
    		url:"front/resume/getanswer?id="+id+"",
    		type:'post',
    		dataType:'json',
    		success:function(data){
    		if(data){
    			if(data.status==true){
    			$("#answer").html(data.content);
    			}
    		}else{
    		}
    		},error:function(){
    		}
    		});
    	
             //var text = this.innerHTML;
             //console.log(this);
             swapText('section7Scroll','section7Text');
         } 
    function setquestion(){
	$("#myform").ajaxSubmit(
			{
				type : "post",
				async : false,
				url : "front/resume/addquestion",
				dataType : "json",
				success : function(data) {
					 $("#tip").html("");
					if (data.content == 'login') {
					   $("#tip").html("请先登录！！！");
					}
					if (data.content == 'content_no_null'){
						  $("#tip").html("问题不能为空！！！");
					}
					if (data.content == 'imageCode_no_null'){
						  $("#tip").html("验证码不能为空！！！");
					}
					if (data.content == 'checkCode_no_ringht'){
						  $("#tip").html("验证码不正确！！！");
					}
					if (data.content == 'ok'){
						 swapText('askquesttion','section7Scroll');
					}
				}
			});
    }
	$(function(){
         
         
         /*这是一段很好的代码
         var ps = document.getElementById('section7Scroll').getElementsByTagName('p');
         var plength = ps.length;
         for(var i=0;i<plength;i++)
             ps[i].addEventListener("click",pClick,false);    这时在pClick里可以用this*/
         
             
     });
 
    $('#talkImg').mouseenter(showTalk);
    $('#talkImg').mouseleave(hideTalk);
    $('#talkImg').click(function(){
    	   swapText('section7Scroll','askquesttion');
    });
    function showTalk(){
        $("#sec5Talk span").removeClass("hideTalk");
        $("#sec5Talk span").addClass("showTalk");
    }
    function hideTalk(){
        $("#sec5Talk span").removeClass("showTalk");
        $("#sec5Talk span").addClass("hideTalk");
    }
    
    //阻止滚动事件冒泡
    $("#section7Scroll,#section3Text,#contain2,#slideConM2").bind("mousewheel",function(e){
                  // 如果提供了事件对象，则这是一个非IE浏览器
              if ( e && e.stopPropagation ) {
                  // 因此它支持W3C的stopPropagation()方法 
                  e.stopPropagation();
              } else { 
                  // 否则，我们需要使用IE的方式来取消事件冒泡
                 window.event.cancelBubble = true;
               }
          });
/*--------------------二维码轮播-------------------------*/
/*var turnCodeNumber = 0;
     function turnCode(){
         if(turnCodeNumber==0)
         $("#scodeContainer").animate({"margin-top":"-177px"},450);
         if(turnCodeNumber==1)
         $("#scodeContainer").animate({"margin-top":"0px"},450);
         turnCodeNumber++;
         turnCodeNumber=(turnCodeNumber>3)?0:turnCodeNumber;
     }
 $(function(){
     var timeCount = setInterval("turnCode()",2000);
 });*/
     
/*--------------------投递-------------------------*/
$('#send').mouseenter(function(){$('#send').css("background","url(static/lango/testIMG/sendHover.png)");});
$('#send').mouseleave(function(){$('#send').css("background","url(static/lango/testIMG/send.png)");});