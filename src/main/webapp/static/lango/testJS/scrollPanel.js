
;$(function(){
	var obj=new ScrollPage();
	obj.operat();
	addEvent(scrollFunc);
	updateBgSize();
	
	window.onresize=function(){
		updateBgSize();
	};
});
function scrollFunc(event){
	var timer=null,e=event||window.event;
	var obj=new ScrollPage();
	obj.Scrolled(e);
	removeEvent(scrollFunc);
	timer=setTimeout(function(){
		addEvent(scrollFunc);
		clearTimeout(timer);
	},obj.opts.speed);
}
/*注册事件*/
function addEvent(fn){
	if(document.addEventListener){
		document.addEventListener('DOMMouseScroll',fn,false);
	}//W3C
	
	window.onmousewheel=document.body.onmousewheel=fn;//IE/Opera/Chrome
}
function removeEvent(fn){
	if(document.addEventListener){
		document.removeEventListener('DOMMouseScroll',fn,false);
	}//W3C
	
	window.onmousewheel=document.body.onmousewheel=null;//IE/Opera/Chrome
}
function ScrollPage(){
	
}
ScrollPage.prototype={
	opts:{
		widthArrays:{},
		speed:400,
		section:".section",
		curPage:"onPage",
		navs:".mark",
		cked:"scrolled",
		navLink:".mark>li"
	},
	Scrolled:function(e){
		var $curPage=$("."+this.opts.curPage);
		if(isAnimated($curPage)){
			return false;
		}
		var wheelData=this.wheelData(e);

		if(wheelData===-1){
			//向后滚，页面往下翻
			/*
			 *1.有没有下一个？
			 *2.下一个是不是最后一个？
			 *3.最后一个的高度够不够window高度的100%；
			 */
			this.downScroll($curPage);
		}else if(wheelData===1){
			/*
			 *1.有没有上一个？
			 */
			this.upScroll($curPage);
		}
	},
	//down data==-1//向后滚，页面往下翻
	downScroll:function($cur,_next){
		var next;
		if(_next==undefined){
			next=this.hasNext($cur);
			if(next.length<=0){
				return false;
			}
		}else{
			next=_next;
		}
		var last=this.isLast(next),ht="0px";
		if(last){
			var lastDis=this.isLastFull(next);
			if(lastDis<0){
				//说明$next的高度低于window的高
				ht=-lastDis+"px";
			}
		}
		var curPage=this.opts.curPage;
		next.css("display","block");
		$cur.animate({"height":ht},this.opts.speed,function(){
			$cur.removeClass(curPage);
			next.addClass(curPage);
			$cur.css("display","none");
		});
		
		this.navCked(next.index(),$cur.index());
	},
	//up data==1//向前滚，页面往上翻
	upScroll:function($cur,_prev){
		var prev;
		if(_prev==undefined){
			prev=this.hasPrev($cur);
			if(prev.length<=0){
				return false;
			}
		}else{
			prev=_prev;
		}
		//var height=this.opts.widthArrays[prev.index().toString()]+"px";
		var height=$("#marked").height()+"px";
		var curPage=this.opts.curPage;
		prev.css("display","block");
		prev.animate({"height":height},this.opts.speed,function(){
			$cur.removeClass(curPage);
			prev.addClass(curPage);
		});
		
		this.navCked(prev.index(),$cur.index());
	},
	navCked:function(i,ci){
		var cked=this.opts.cked;
		$("."+cked).removeClass(cked);
		$(this.opts.navs).each(function() {
			//$(this).children().eq(ci).removeClass(cked);
			$(this).children().eq(i).addClass(cked);
		});
	},
	operat:function(){
		var self=this;
		$("body").on("click",this.opts.navLink,function(){
			var $cur=$("."+self.opts.curPage);
			var i=$(this).index(),ci=$cur.index(),p=ci-i,d="";
			if(p>0){
				//往前点击
				if(p>1){
					d="up";
					var prev=self.killHt(i,ci,d);
					self.upScroll($cur,prev);
					return false;
				}else {
					//等于1相当于展示上一页
					self.upScroll($cur);
					return false;
				}
			}else if(p<0){
				if(p<1){
					d="down";
					var next=self.killHt(i,ci,d);
					self.downScroll($cur,next);
					return false;
				}else{
					self.downScroll($cur);
					return false;
				}
			}
		});
	},
	//取消中间的page的高度
	killHt:function(i,ci,direct){
		var p=ci-i,$section=$(this.opts.section);
		if(direct==="up"){
			//往前面翻页，i<ci
			//恢复中间的page的高度
			for(var b=ci-1;b>i;b--){
				$section.eq(b).css({"height":$(window).height()+"px","display":"none"});			
			}
		}
		else
		{
			for(var b=i-1;b>ci;b--){
				$section.eq(b).css({"height":"0px","display":"none"});			
			}
		}
		return $section.eq(i);
	},
	//恢复中间的page的高度
	/*
	killHt:function(i,ci,direct){
		var p=ci-i,$section=$(this.opts.section);
		if(direct==="up"){
			//往前面翻页，i<ci
			for(var b=ci-1;b>i;b--){
				
				$section.eq(b).css({"height":"0px","display":"none"});			
			}
		}
		else
		{
			for(var b=i-1;b>ci;b--){
				$section.eq(b).css({"height":"0px","display":"none"});			
			}
		}
		return $section.eq(i);
	},*/
	//判断滚轮方向
	wheelData:function(e){
		var data;
		if(e.wheelDelta){
			data=e.wheelDelta<0?-1:1;
		}else if(e.detail){
			data=e.detail>0?-1:1;
		}else{
			return false;
		}
		return data;
	},
	//获取所有section的高度
	getSectionWidth:function(){
		var section=this.opts.section,arr={};
		$(section).each(function() {
			var height=$(this).outerHeight();
			var index=$(this).index().toString();
			arr[index]=height;
		});
		this.opts.widthArrays=arr;
		arr=null;
	},
	isLastFull:function($next){
		//为负数表示最后一页的高度不足window的100%；
		var flag=0;
		var nw=$next.outerHeight(),w=$(window).height();
		if(nw<w){
			flag=nw-w;
		}
		return flag;
	},
	isLast:function($obj){
		return $obj.index()===($(this.opts.section).length-1);
	},
	hasNext:function($cur){
		var f="";
		var next=$cur.next();
		if(next.length>0){
			f=next;
		}
		return f;
	},
	hasPrev:function($cur){
		var f="";
		var prev=$cur.prev();
		if(prev.length>0){
			f=prev;
		}
		return f;
	}
	//end
}

function isAnimated($obj){
	var flag=false;
	if($obj.is(":animated")){
		flag=true;
	}
	return flag;
}
/*
function valpx(str){
	return parseFloat(str.substring(0,str.length-2));
}
*/
//图片根据屏幕大小自适应	
function updateBgSize(){
	var imgWidth = 1440;
	var imgHeight = 850;
	var imgRatio = imgWidth/imgHeight;
	var _img = $( ".bg" );
	var _window = $(window);
	var tpw=$(".tpos").width(),tph=tpw*674/897,tposTop=$(".tpos").css("top"),bl=897/674,menu=57;
	//tph=$(".tpos").height()
	var _windownWidth = _window.width(), 
		_windowHeight = _window.height();
	if( _windownWidth/_windowHeight > imgRatio ){
		_img.width( _windownWidth );
		_img.height( (_windownWidth * imgHeight)/imgWidth );
	}else{
		_img.width( (_windowHeight * imgWidth)/imgHeight );
		_img.height( _windowHeight );
	}
	_img.css({"margin-left":-_img.width()/2,"left":"50%"});
	//
	if(_windowHeight<(tph+parseInt(tposTop.substring(0,tposTop.length-2)))){
		if(_windowHeight-tph>=menu){
			var res=((_windowHeight-tph-menu)/2+menu)+"px";
			$(".tpos").css("top",res);
		}else{
			//当前宽度（61%）下的高度比windowheight还高，必须缩放
			tph=_windowHeight-menu;
			tpw=tph*bl;
			
			$(".tpos").css({"height":tph,"width":tpw,"top":"57px","margin-left":-tpw/2+"px","left":"50%"});
		}
	}
	//
	if(_windownWidth<1250){
		$(".P3Menu").addClass("sMenu");
		$("#page4 .desArea").addClass("smallArea");
	}else{
		$(".P3Menu").removeClass("sMenu");
		$("#page4 .desArea").removeClass("smallArea");
	}
}
function setBg(){
	 var iw=1440,ih=850,w=$(window).width(),h=$(window).height(),$bg=$(".bg");
	 var ib=ih/iw,b=h/w;
	 if(b>ib){
		var bw=(iw/ih)*h+80;
		$bg.css({"width":"auto","height":"100%","left":"50%","margin-left":bw/-2+"px"});
	 }else{
		$bg.css({"width":"100%","height":"auto","left":"50%","margin-left":-w/2+"px"});
	 }
}